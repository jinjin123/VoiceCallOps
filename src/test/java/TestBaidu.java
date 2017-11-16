import com.saicmotor.ops.wwx.service.BaiduYuYinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/AppCtx.xml")
public class TestBaidu {

    @Autowired
    private BaiduYuYinService baiduYuYinService;

    @Test
    public void t1() throws Exception{
        URL url = this.getClass().getResource("/voices");
        File dir = new File(url.getPath());
        for(File fi : dir.listFiles()){
            System.out.println("----> "+fi.getPath());
            baiduYuYinService.getVoiceRecognition(fi);
        }
    }

    @Test
    public void t2() throws Exception{
        URL url = this.getClass().getResource("/voices");
        File fi = new File(url.getPath()+File.separator+"8k.pcm");
        FileInputStream fin = new FileInputStream(fi);
        byte[] data = new byte[(int)fi.length()];
        fin.read(data);
        fin.close();

        String result = baiduYuYinService.voice2txt("pcm","8000",data);
        System.out.println("result--->"+result);
    }
}
