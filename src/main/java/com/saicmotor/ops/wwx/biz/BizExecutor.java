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

public class BizExecutor {
    private static Logger log = LoggerFactory.getLogger(BizExecutor.class);
    @Autowired
    private FreeMarkerConfigurer freemarker;

    private Map<String,BizHandler> handlers = new HashMap<String, BizHandler>();

    public Map<String, BizHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, BizHandler> handlers) {
        this.handlers = handlers;
    }

    public Map<String,Object> exec(Map<String,Object> cmd) throws Exception{
        long st = System.currentTimeMillis();
        Map<String,Object> result = null;

        try{
            log.info("===> {}", cmd);
            BizHandler biz = handlers.get(cmd.get("catalog"));
            Method mth = biz.getClass().getMethod((String)cmd.get("action"), Map.class);
            Map<String,Object> params = (Map<String,Object>)cmd.get("params");
            log.debug("call {}.{}({})", biz.getClass(),mth.getName(),params);

            result = (Map)mth.invoke(biz, params);
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
