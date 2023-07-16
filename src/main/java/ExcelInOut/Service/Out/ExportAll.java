package ExcelInOut.Service.Out;

import ExcelInOut.Service.Entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * @author hss
 * @date 2023/7/15 21:03
 */
public interface ExportAll{
    public <T> HSSFWorkbook OutputExcelXls(Integer pageSize, List<T> data, Class<User> clt) throws NoSuchFieldException, IllegalAccessException;

    public <T> XSSFWorkbook OutputExcelXlsx(Integer pageSize, List<T> data, Class<User> clt) throws NoSuchFieldException, IllegalAccessException;
}
