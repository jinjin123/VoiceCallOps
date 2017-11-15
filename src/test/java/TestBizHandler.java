import com.saicmotor.ops.wwx.biz.BizExecutor;
import com.saicmotor.ops.wwx.biz.BizHandler;
import com.saicmotor.ops.wwx.service.DutyPlanService;
import com.saicmotor.ops.wwx.utils.RestUtil;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/AppCtx.xml")
public class TestBizHandler {
    private Logger log = LoggerFactory.getLogger(TestBizHandler.class);

    @Autowired
    private BizExecutor executor;
    @Autowired
    private RestUtil restUtil;
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
        Map<String,Object> cmd = new HashMap<String,Object>();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("days", 7);
        cmd.put("catalog", "duty");
        cmd.put("action", "getDutyPlan");
        cmd.put("params", params);

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
