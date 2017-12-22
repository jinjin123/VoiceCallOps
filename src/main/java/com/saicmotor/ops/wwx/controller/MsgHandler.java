package com.saicmotor.ops.wwx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.saicmotor.ops.wwx.biz.BizExecutor;
import com.saicmotor.ops.wwx.dialog.ConversationMnger;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import com.saicmotor.ops.wwx.service.BaiduYuYinService;
import com.saicmotor.ops.wwx.service.DutyPlanService;
import com.saicmotor.ops.wwx.service.TuLingService;
import com.saicmotor.ops.wwx.service.WWXService;
import com.saicmotor.ops.wwx.service.HiService;
import com.saicmotor.ops.wwx.service.ServerwithService;
import com.saicmotor.ops.wwx.service.RestartwithService;
import com.saicmotor.ops.wwx.service.RebootService;
import com.saicmotor.ops.wwx.service.ChecknetService;
import com.saicmotor.ops.wwx.service.CommandService;

import freemarker.template.Template;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import java.io.StringWriter;

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
import java.util.*;



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

    @Value("${yunwei.alarmList.url}")
    private String yAlarmListUrl ;
    @Value("${yunwei.getserverservice.url}")
    private String ygetserverserviceUrl ;

    @Value("${yunwei.execserver.url}")
    private String yexecserverUrl ;

    @Value("${yunwei.checknet.url}")
    private String ychecknetUrl;

    @Value("${yunwei.command.url}")
    private String ycommandUrl;
    @Autowired
    private HttpHelper restUtil;

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


    // for render template
    @Autowired
    private FreeMarkerConfigurer freemarker;

    @Autowired
    private HiService hiservice;

    @Autowired
    private ChecknetService checknet;

    @Autowired
    private ServerwithService getServer;

    @Autowired
    private RestartwithService gorestart;
    @Autowired
    private CommandService gocommand;

    @Autowired
    private RebootService execrestart;

    private WXBizMsgCrypt wxcpt;

    @Autowired
    private WWXService wwxService;
    @Autowired
    private BizExecutor bizExecutor;

    @Autowired
    private ConversationMnger cm;

//    private static int counter = 0;

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
            String tmp = (String)answer.get("text").toString();
            if ( tmp!=null && !tmp.startsWith("ACT.") ){
                tmp = cm.talk((String)msg.get("FromUserName"), tmp);
                log.info("what's question?:",tmp);
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

//        if( content!=null && content.equals(answer.get("text")) ){
//            if ( "voice".equals(msg.get("MsgType")) ){
//                answer.put("text", "臣没有听清楚，请陛下再说一遍");
//            }else{
//                answer.put("text", "臣无法做到，请明示。");
//            }
//        }

        if( content!=null && content.equals(answer.get("text")) ){
            if (content.matches("(.*)你好(.*)")) {
                //return  template  data
                Map<String, Object> result = hiservice.getHianswer((String) yAlarmListUrl, (String) msg.get("FromUserName"));
                if (result.containsKey("msgFtl")) {
                    Template tpl = freemarker.getConfiguration().getTemplate((String) result.get("msgFtl"));
                    StringWriter out = new StringWriter();

                    // template render return result to output
                    tpl.process(result, out);
                    result.put("msgResult", out.toString());
                    answer.put("text", result.get("msgResult"));
                }
            }else if(content.matches("(.*)你能做什么(.*)")){
                answer.put("text", "您可以输入以下文字或语音命令：\n" +
                        "1.查询告警；\n" +
                        "2.查询值班；\n" +
                        "3.查询租户资源；\n" +
                        "4.查询数据中心资源; \n" +
                        "5.操作服务器;(例如如下命令:df -h,free -m,ping -c 4 ip,tail -5 /var/log/messages;禁止echo,reboot等不允许的操作,操作前请确认文件是否存在!)\n " +
                        "6.检查网络；(例如如下命令:ping -c 4 ip;) \n" +
                        "7.请勿执行长时间运行无法停止的命令;(例如如下命令:ping ip,top,tail -f ) \n " +
                        "注意!<不可执行超过4秒才返回的命令，结束会话时,或者中断会话时,请文字或者语音回复'取消'>");
            }else if(content.matches("取消(.*)")){
                answer.put("text", "已取消该次会话!");
            }else{
                answer.put("text", "臣妾没有听清楚，请陛下再说一遍");
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
