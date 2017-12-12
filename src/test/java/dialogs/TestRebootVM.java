package dialogs;

import com.saicmotor.ops.wwx.dialog.ConversationMnger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/AppCtx.xml")
public class TestRebootVM {
    Logger log = LoggerFactory.getLogger(TestRebootVM.class);

    @Autowired
    ConversationMnger cm ;
    String openId = "123";

    @Test
    public void test1() throws Exception{
        String[] qs = new String[]{
                "hello",
                "重启服务器",
                "abc.adf,xx",
                "123.12.12.12",
                "pwssd123",
                "testtest"
        };

        String tmp = null;
        for(String q : qs) {
            log.info("Q: {}", q);
            log.info("A: {} ", cm.talk(openId, q));
        }
    }


}
