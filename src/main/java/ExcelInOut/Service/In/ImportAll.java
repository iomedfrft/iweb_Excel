package ExcelInOut.Service.In;

import ExcelInOut.Service.Entity.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author hss
 * @date 2023/7/16 0:24
 */
public interface ImportAll {
    public List<User> InputExcelXls(Class<User> clz) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;


}
