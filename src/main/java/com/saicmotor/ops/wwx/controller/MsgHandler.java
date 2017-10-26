package com.saicmotor.ops.wwx.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.saicmotor.ops.wwx.service.TuLingService;
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

    @Autowired
    private XmlMapper xmlMapper;
    @Autowired
    private TuLingService tuLingService;
    private WXBizMsgCrypt wxcpt;

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
                result = processTextMsg(msg, timestamp, nonce);
            }

            if (result!=null){
                return result;
            }else{
                return defaultReply(msg, timestamp, nonce);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<byte[]> processTextMsg(Map<String,Object> msg, String timestamp, String nonce) throws Exception{
        String content = (String)msg.get("Content");
        Map<String,Object> answer = tuLingService.getTalkAnswer((String)msg.get("FromUserName"), content, null);

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

    private ResponseEntity<byte[]> defaultReply(Map<String,Object> msg, String timestamp, String nonce) throws Exception{
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
