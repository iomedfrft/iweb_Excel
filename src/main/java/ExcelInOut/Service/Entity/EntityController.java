package ExcelInOut.Service.Entity;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author hss
 * @date 2023/7/16 13:04
 */
public class EntityController {
    public static void EntityUtil() throws Exception {
        //        UserService service=new UserServiceImpl();
//        获取配置文件所对应的文件对象
        File entityProperties=new File("E:\\javaspace\\ClassNotes\\TestExcel\\src\\main\\java\\ExcelInOut\\Service\\Entity\\EntityUtil.properties");
        //        创建配置文件读取类Properties
        Properties config=new Properties();
//        创建字节输入流，读取文件对象，并通过Properties
        config.load(new FileInputStream(entityProperties));
        //        Properties会把所有读到的配置文件属性封装成一个类似Map结构
//        将配置文件中所编写的信息读取
        String className=(String)config.get("class");
        String methodName=(String)config.get("method");
        //        利用反射实现方法调用
//        获取类对象
        Class uClass=Class.forName(className);
        //        获取构造器对象
        Constructor<User> c=uClass.getConstructor();
        //        获取实例化UserService的子类对象
        User service=c.newInstance();
        //        获取目标方法的反射对象
        Method m=uClass.getMethod(methodName);

//        调用方法
        m.invoke(service);
    }

}
