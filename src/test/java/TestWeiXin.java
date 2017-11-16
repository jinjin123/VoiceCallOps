import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.saicmotor.ops.wwx.service.WWXService;
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
public class TestWeiXin {
    private static Logger log = LoggerFactory.getLogger(TestWeiXin.class);

    @Autowired
    private XmlMapper xmlMapper;
    @Autowired
    private WWXService wwxService;

    @Test
    public void test1() throws Exception{
        String xml = "<xml>\n" +
                "   <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "   <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                "   <CreateTime>1348831860</CreateTime>\n" +
                "   <MsgType><![CDATA[text]]></MsgType>\n" +
                "   <Content><![CDATA[this is a test]]></Content>\n" +
                "   <MsgId>1234567890123456</MsgId>\n" +
                "   <AgentID>1</AgentID>\n" +
                "</xml>";

    }

    @Test
    public void testGetToken() throws Exception{
        wwxService.getAccessToken();
    }

    @Test
    public void downloadMedia() throws Exception{
        wwxService.downloadMediaObject("test");
    }

}
