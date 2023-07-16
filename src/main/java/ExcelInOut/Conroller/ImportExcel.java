package ExcelInOut.Conroller;

import ExcelInOut.DAO.ExcelDAOImpl;
import ExcelInOut.Service.Entity.User;
import ExcelInOut.Service.In.FileInput;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author hss
 * @date 2023/7/16 8:51
 */
public class ImportExcel {
    public void JDBCLoad(){
        FileInput fi=new FileInput();
        ExcelDAOImpl edi=new ExcelDAOImpl();

        try {
            List<User>  list=fi.InputExcelXls(User.class);
            for (User u:list) {
                edi.insert(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("Excel表格导入成功！！！");
    }
}
