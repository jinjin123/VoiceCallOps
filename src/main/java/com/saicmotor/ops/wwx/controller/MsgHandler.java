package com.saicmotor.ops.wwx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.saicmotor.ops.wwx.biz.BizExecutor;
import com.saicmotor.ops.wwx.dialog.ConversationMnger;
import com.saicmotor.ops.wwx.service.BaiduYuYinService;
import com.saicmotor.ops.wwx.service.DutyPlanService;
import com.saicmotor.ops.wwx.service.TuLingService;
import com.saicmotor.ops.wwx.service.WWXService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.util.Map;
import java.util.Random;

/**
 * Created by kevinsun0716 on 2017/10/23.
 */
@RestController
public class MsgHandler {
    private static Logger log = LoggerFactory.getLogger(MsgHandler.class);

    @Value("${wwx.Token}")
    private String sToken ;
    @Value("${wwx.CorpID}")
    private String sCorpID ;
    @Value("${wwx.EncodingAESKey}")
    private String sEncodingAESKey ;
    @Value("${wwx.VoiceUrl}")
    private String sVoiceUrl ;
    @Value("${wwx.CorpSecret}")
    private String sCorpSecret ;
    
    @Value("${baidu.api.token}")
    private String sBaiduToken ;
    @Value("${baidu.api.url}")
    private String sBaiduUrl ;
    
    @Value("${yunwei.domain.url}")
    private String yDomainUrl ;

    @Autowired
    private XmlMapper xmlMapper;
    @Autowired
    @SuppressWarnings({"SpringJavaAutowiringInspection"})
    private ObjectMapper jsonMapper;
    @Autowired
    private TuLingService tuLingService;
    @Autowired
    private BaiduYuYinService baiduYuYinService;
    @Autowired
    private DutyPlanService dutyPlanService;

    private WXBizMsgCrypt wxcpt;

    @Autowired
    private WWXService wwxService;
    @Autowired
    private BizExecutor bizExecutor;

    @Autowired
    private ConversationMnger cm;

    @PostConstruct
    public void init() throws Exception{
        this.wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
    }

    @RequestMapping("/receiver")
    public ResponseEntity<byte[]> handle(@RequestParam("msg_signature") String msg_signature,
                                         @RequestParam("timestamp") String timestamp,
                                         @RequestParam("nonce") String nonce,
                                         @RequestParam(value="echostr", required=false) String echostr,
                                         @RequestBody(required=false) String body){
        try{
            //url verify
            if( echostr!=null && echostr.trim().length()>0 ){
                log.debug("---> receive url verify : {} ", echostr);
                echostr = wxcpt.VerifyURL(msg_signature,timestamp,nonce,echostr);
                return new ResponseEntity<byte[]>(echostr.getBytes("UTF-8"), HttpStatus.OK);
            }

            log.debug("===> receive origin msg : {}", body);
            if( body==null || body.trim().length()==0 ){
                log.debug("---> receive null msg!");
                return new ResponseEntity<byte[]>(HttpStatus.OK);
            }

            //decrypt msg.
            String tmp = wxcpt.DecryptMsg(msg_signature,timestamp,nonce,body);
            Map<String,Object> msg = xmlMapper.readValue(tmp, Map.class);
            log.debug("---> decrypt msg : {}", msg);

            //process normal msg.
            ResponseEntity<byte[]> result = null;
            if( "text".equals(msg.get("MsgType")) ){
                result = processTextMsg(msg);
            } else if ("voice".equals(msg.get("MsgType"))) {
            	String mediaId = msg.get("MediaId").toString();
            	byte[] voiceData = wwxService.downloadMediaObject(mediaId);
            	String txt = baiduYuYinService.voice2txt("amr","8000", voiceData);
            	if ( txt != null ){
                    msg.put("Content", txt);
            	    result = processTextMsg(msg);
                }
            }

            if (result!=null){
            	return result;
            }else{
                return defaultReply(msg);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<byte[]> processTextMsg(Map<String,Object> msg) throws Exception{
        String content = (String)msg.get("Content");
        Map<String,Object> answer = tuLingService.getTalkAnswer((String)msg.get("FromUserName"), content, null);
        log.info("Q: {} \n A:{}", content, answer.get("text"));

        try{
            String tmp = (String)answer.get("text");
            if ( tmp!=null && !tmp.startsWith("ACT.") ){
                tmp = cm.talk((String)msg.get("FromUserName"), tmp);
                answer.put("text", tmp);
            }
        }catch (Throwable t){
            log.error(t.getMessage(), t);
        }
        
        try{
            if( bizExecutor.accept((String)answer.get("text")) ){
                log.info("found action cmd : {}", answer.get("text"));
                Map<String,Object> result = bizExecutor.exec((String)answer.get("text"));
                answer.put("text", result.get("msgResult"));
            }
        } catch (Throwable t){
            answer.put("text","哦哟，出错了 [流泪]");
            log.error(t.getMessage(), t);
        }

        if( content!=null && content.equals(answer.get("text")) ){
            if ( "voice".equals(msg.get("MsgType")) ){
                answer.put("text", "臣没有听清楚，请陛下再说一遍");
            }else{
                answer.put("text", "臣无法做到，请明示。");
            }
        }

        Random random = new Random(System.currentTimeMillis());
        String tpl =
                "<xml>\n" +
                "   <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "   <FromUserName><![CDATA[%s]]></FromUserName> \n" +
                "   <CreateTime>%s</CreateTime>\n" +
                "   <MsgType><![CDATA[text]]></MsgType>\n" +
                "   <Content><![CDATA[%s]]></Content>\n" +
                "</xml>";
        tpl = String.format(tpl , msg.get("FromUserName"), msg.get("ToUserName"), System.currentTimeMillis()/1000, answer.get("text"));
        String data = wxcpt.EncryptMsg(tpl, String.valueOf(System.currentTimeMillis()/1000), String.valueOf(random.nextInt(99999999)));
        return new ResponseEntity<byte[]>(data.getBytes(), HttpStatus.OK);
    }

    private ResponseEntity<byte[]> defaultReply(Map<String,Object> msg) throws Exception{
        Random random = new Random(System.currentTimeMillis());
        String tpl =
                "<xml>\n" +
                        "   <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                        "   <FromUserName><![CDATA[%s]]></FromUserName> \n" +
                        "   <CreateTime>%s</CreateTime>\n" +
                        "   <MsgType><![CDATA[text]]></MsgType>\n" +
                        "   <Content><![CDATA[%s]]></Content>\n" +
                        "</xml>";
        tpl = String.format(tpl , msg.get("FromUserName"), msg.get("ToUserName"), System.currentTimeMillis()/1000, "测试阶段，目前只支持文本消息");
        String data = wxcpt.EncryptMsg(tpl, String.valueOf(System.currentTimeMillis()/1000), String.valueOf(random.nextInt(99999999)));
        return new ResponseEntity<byte[]>(data.getBytes(), HttpStatus.OK);
    }
}
