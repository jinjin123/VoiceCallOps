
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;

public class Msym {
    
    public void test(String[] arg){
        for (String string : arg) {
            System.out.println(string);
        }
    }
    @Test
    public void demo1() throws Exception {
        //获取字节码对象
        Class<Msym> clazz = (Class<Msym>) Class.forName("Msym");
        System.out.println(clazz);
        //获取一个对象
        Constructor con =  clazz.getConstructor();
        System.out.println(con);
        Msym m = (Msym) con.newInstance();
        String[] s = new String[]{"aa","bb"};
        //获取Method对象
        Method method = clazz.getMethod("test", String[].class);
        System.out.println(method);
        //调用invoke方法来调用
        method.invoke(m,new Object[] {s});
//        method.invoke(m, (Object)s);
    }
}