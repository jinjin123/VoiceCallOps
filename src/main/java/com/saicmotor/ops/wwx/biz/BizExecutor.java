package com.saicmotor.ops.wwx.biz;

import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BizExecutor {
    private static Logger log = LoggerFactory.getLogger(BizExecutor.class);
//    ACT.asset.getAssetManageTenant(6LWb56eR5Yip5rG96L2m5qih5YW3,0)
    private static String cmdPattern = "^ACT\\.(\\w+)\\.(\\w+)\\(([\\w+\\s*,]*)\\)$";

    @Autowired
    private FreeMarkerConfigurer freemarker;

    private Map<String,BizHandler> handlers = new HashMap<String, BizHandler>();

    public Map<String, BizHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, BizHandler> handlers) {
        this.handlers = handlers;
    }


    public boolean accept(String cmd ){
        if( cmd==null || cmd.trim().length()==0 ) return false;
        Pattern pattern = Pattern.compile(cmdPattern);
        return pattern.matcher(cmd).matches();
    }


    public Map<String,Object> exec(String cmd) throws Exception{
        long st = System.currentTimeMillis();
        Map<String,Object> result = null;

        try{
            log.info("===> {}", cmd);
            Pattern pattern = Pattern.compile(cmdPattern);
            Matcher matcher = pattern.matcher(cmd.trim());
            matcher.matches();

            BizHandler biz = handlers.get(matcher.group(1));
            Method mth = biz.getClass().getMethod(matcher.group(2), String[].class);

            String[] params = null;
            String tmp = matcher.group(3).trim();
            if( tmp==null || tmp.length()==0 ){
                params = new String[0];
            }else{
                params = tmp.split(",");
                for(int i=0;i<params.length;i++){
                    params[i] = params[i].trim().length()==0?null:params[i].trim();
                }
            }
            log.debug("call {}.{}({})", biz.getClass(),mth.getName(), params);

            result = (Map)mth.invoke(biz, new Object[]{params});
            if(result.containsKey("msgFtl")){
                Template tpl = freemarker.getConfiguration().getTemplate((String)result.get("msgFtl"));
                StringWriter out = new StringWriter();
                tpl.process(result, out);
                result.put("msgResult", out.toString());
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw e;
        }finally {
            log.info("<=== used:{}ms result:{}", (System.currentTimeMillis()-st), result);
        }
        return result;
    }
}
