package com.saicmotor.ops.wwx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.saicmotor.ops.wwx.service.BaiduYuYinService;
import com.saicmotor.ops.wwx.service.SearchService;
import com.saicmotor.ops.wwx.service.TuLingService;
import com.saicmotor.ops.wwx.service.impl.utils.MsgHandlerUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private SearchService searchService;
    @Autowired
    private MsgHandlerUtil msgHandlerUtil;
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
            } else if ("voice".equals(msg.get("MsgType"))) {
            	String MediaId = msg.get("MediaId").toString();
            	Map<String,Object> accessTokenMap = msgHandlerUtil.getAccesToken(sCorpID, sCorpSecret);
            	String accessToken = accessTokenMap.get("access_token").toString();
            	String wxVoiceUrl = sVoiceUrl + "?access_token=" + accessToken + "&media_id=" + MediaId;
            	// download media
            	File mediaFile = downloadFile(wxVoiceUrl);
            	
            	Map<String,Object> voiceRecognitionMap = baiduYuYinService.getVoiceRecognition(mediaFile);
            	String voiceResult =  voiceRecognitionMap.get("result").toString();
            	if (voiceResult.contains("[")) {
            		voiceResult.replace("[", "");
            	}
            	if (voiceResult.contains("]")) {
            		voiceResult.replace("]", "");
            	}
            	
            	msg.put("Content", voiceResult);
            	result = processTextMsg(msg, timestamp, nonce);
            }

            if (result!=null){
//            	String resultStr = new String(result.getBody(),"UTF-8");
//            	log.info("---> resultStr  : {}", resultStr);
//            	String resultDecode = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, resultStr);
//            	log.info("---> resultDecode  : {}", resultDecode);
//            	
//                Map<String,Object> resultMap = xmlMapper.readValue(resultDecode, Map.class);
//                log.info("---> resultMap  : {}", resultMap);
//                String text = resultMap.get("text").toString();
//                log.info("---> text  : {}", text);
//                try {
//                	Map<String,String> resultTextMap = xmlMapper.readValue(text, Map.class);
//                	log.info("---> resultTextMap  : {}", resultTextMap);
//	            	if (resultTextMap.containsKey("action")) {
//	            		String actionUri = resultTextMap.get("action");
//	            		String message = "{0}?date={1}";
//	            		Date date = new Date();
//	            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//	            		String datastr = sdf.format(date);
//	            		Object[] array = new Object[]{actionUri,datastr};  
//	            		String value = MessageFormat.format(message, array);
//	            		String actionUrl = yDomainUrl + value;
//	            		int days = Integer.parseInt(resultTextMap.get("days"));
//	            	
//		            	List<String> schedueDataList = searchService.getSchedue(actionUrl,days);
//		            	String[] schedueDataArray = (String[])schedueDataList.toArray(new String[0]);
//		            	StringBuilder sBuilder=new StringBuilder();
//		            	for(String str:schedueDataArray){
//		            		sBuilder.append(str);
//		            	}
//		            	byte[] schedueDataByte =sBuilder.toString().getBytes();
//		            	ResponseEntity<byte[]> actionResult = new ResponseEntity<byte[]>(schedueDataByte,HttpStatus.OK);
//		                return actionResult;
//	
//	            	} else {
//	            		return result;
//	            	}
//            	} catch(Exception e){
//            		return result;
//            	}
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
        log.info("-->{}", answer); 
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("AppCtx.xml");  

        try{
//        	Map<String,Object> resultMap = jsonMapper.readValue(answer.get("text").toString(), Map.class);
//        	log.info("---> resultTextMap  : {}", resultMap);
//        	String catalog = (String)resultMap.get("catalog");
//        	String action = (String)resultMap.get("action");
//        	String params =(String)resultMap.get("params");
        	
        	String catalog = (String)answer.get("catalog");
        	String action = (String)answer.get("action");
        	String params =(String)answer.get("params");
        	Map<String,Object> paramsMap = jsonMapper.readValue(params, Map.class);
        	Object Object = ctx.getBean(catalog);
        	log.info("-->Object: {}", Object);
        	Class<?> cls = Object.getClass();
        	Method method = cls.getDeclaredMethod(action, String.class);
        	String resolvedAnswer = (String)method.invoke(Object, paramsMap);
        	answer.put("text", resolvedAnswer);
        	
        } catch (Throwable t){
        	log.debug("json convert error");
        }
        
        
//        
//        try{
//        	log.info("-->{}",answer.get("text").getClass());
//        	Map<String,String> resultMap = jsonMapper.readValue(answer.get("text").toString(), Map.class);
//        	log.info("---> resultTextMap  : {}", resultMap);
//        	if (resultMap.containsKey("action")) {
//        		String actionUri = resultMap.get("action");
//        		String message = "{0}?date={1}";
//        		Date date = new Date();
//        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        		String datastr = sdf.format(date);
//        		String datastrEncode = URLEncoder.encode(datastr, "UTF-8");
//        		
//        		Object[] array = new Object[]{actionUri,datastrEncode};  
//        		String value = MessageFormat.format(message, array);
//        		String actionUrl = yDomainUrl + value;
//        		int days = Integer.parseInt(resultMap.get("days"));
//        	
//            	List<String> schedueDataList = searchService.getSchedue(actionUrl,days);
//            	StringBuilder sBuilder=new StringBuilder();
//            	for(String str:schedueDataList){
//            		sBuilder.append(str);
//            	}
//            	
////            	String[] schedueDataArray = (String[])schedueDataList.toArray(new String[0]);
////            	StringBuilder sBuilder=new StringBuilder();
////            	for(String str:schedueDataArray){
////            		sBuilder.append(str);
////            	}
//            	answer.put("text", sBuilder);
//        	}
//        }catch(Throwable t){
//        	log.debug("json convert error");
//        }
        
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
    
    @RequestMapping("/test")
    public void test() {
    	File file = new File("F:\\media\\media1509501458407");
    	try {
			Map<String,Object> voiceRecognitionMap = baiduYuYinService.getVoiceRecognition(file);
			 String ss = voiceRecognitionMap.get("result").toString();
//        	String[] voiceResultArray = (String[]) voiceRecognitionMap.get("result");
//        	String voiceResult = "";
//        	for (int i=0;i<voiceResultArray.length;i++) {
//        		voiceResult = voiceResult + voiceResultArray[i];
//        	}
        	System.out.println("百度语音结果是: " + ss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private  File downloadFile(String urlPath) {
        File file = null;
        
        String classPath = this.getClass().getClassLoader().getResource("/").getPath();
		for (int i =0;i <5;i++) {
			classPath = classPath.substring(0,classPath.lastIndexOf("/"));
		}
		String downloadDir = classPath + "/temp";
		
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 文件名
//            String filePathUrl = httpURLConnection.getURL().getFile();
//            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
            String fileName = "media" + System.currentTimeMillis();

            System.out.println("file length---->" + fileLength);

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 1000 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }
    }
}
