import com.saicmotor.ops.wwx.biz.BizExecutor;
import com.saicmotor.ops.wwx.biz.BizHandler;
import com.saicmotor.ops.wwx.service.DutyPlanService;
import com.saicmotor.ops.wwx.utils.HttpHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/AppCtx.xml")
public class TestBizHandler {
    private Logger log = LoggerFactory.getLogger(TestBizHandler.class);

    @Autowired
    private BizExecutor executor;
    @Autowired
    private HttpHelper restUtil;
    @Autowired
    private DutyPlanService dutyPlanService;

    @Test
    public void T1() throws Exception{
        Map<String,BizHandler> handlers = executor.getHandlers();
        log.info("-->{}", handlers.keySet());
        BizHandler biz = handlers.get("duty");
        log.info("-->{}", biz.getClass());

        Method mth = biz.getClass().getMethod("getWeekDutyPlan", Map.class);
        log.info("--mth : {}", mth.getName());
        mth.invoke(biz, new HashMap());
    }

    @Test
    public void T2() throws Exception{
        Pattern pattern = Pattern.compile("^ACT\\.(\\w+)\\.(\\w+)\\(([\\w+\\s*,]*)\\)$");
        String cmd = "ACT.duty.getDutyPlan(7)";
        //String cmd = "ACT.duty.getDutyPlan(1)";
        Matcher matcher = pattern.matcher(cmd);
        if( matcher.matches() ){
            for(int i=0;i<=matcher.groupCount();i++){
                System.out.println("---->"+matcher.group(i));
            }
        }
        String params = matcher.group(3);
        String[] p = params.split(",");
        for(String s : p){
            System.out.println("--==="+s+"===");
        }

        executor.exec(cmd);
    }

    @Test
    public void T3() throws Exception {
        Map result = restUtil.getJson("http://cmdb.saicstack.com/duty/calender/data?date=2017-11-12%2012:12:12");
        log.info("{}",result);
    }

    @Test
    public void T4() throws Exception {
        Map dt = dutyPlanService.getTodayPlan();
        log.info("{}", dt);
    }
}
