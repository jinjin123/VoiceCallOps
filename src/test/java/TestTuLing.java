import com.saicmotor.ops.wwx.service.TuLingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kevinsun0716 on 2017/10/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/AppCtx.xml")
public class TestTuLing {
    private static Logger log = LoggerFactory.getLogger(TestTuLing.class);

    @Autowired
    private TuLingService tuLingService;

    @Test
    public void getAnswer() throws Exception{
        tuLingService.getTalkAnswer("kevinsun", "上海天气如何", null);
    }

}
