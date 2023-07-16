package ExcelInOut.Service.Out;

/**
 * @author hss
 * @date 2023/7/14 19:16
 */



import ExcelInOut.DAO.ExcelDAOImpl;
import ExcelInOut.Service.Entity.Excel;
import ExcelInOut.Service.Entity.User;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class FileOutput implements ExportAll{

    /**
     *
     * @param <T> 泛型
     * @param data 需要导出的数据
     * @param clt 数据对应的实体类
     * @return Excel文件
     */
//xls
   @Override
    public <T> HSSFWorkbook OutputExcelXls(Integer pageSize,List<T> data, Class<User> clt) throws NoSuchFieldException, IllegalAccessException {
//        将数据库数据导入
        ExcelDAOImpl edi=new ExcelDAOImpl();
        List<User> cu=edi.listAll();
//        将用户类的字段存储到属性集合中
    Field[] fields=clt.getDeclaredFields();
    List<String> headers=new LinkedList<>();
    List<String> variable=new LinkedList<>();
//    创建工作簿对象
        HSSFWorkbook workbook=new HSSFWorkbook();
//        创建工作表对象,判断需要创建几个表
        int sheetNum=data.size()/pageSize+1;
        List<HSSFSheet> sheets=new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            HSSFSheet sheet=workbook.createSheet();
            workbook.setSheetName(i,"sheet"+i);
            sheets.add(sheet);
        }
//通过循环将表数据放入Excel每个表中
        for (HSSFSheet sheet:sheets) {
            //创建表头
            Row rowHeader = sheet.createRow(0);
//            记录有多少行数据加入表中
            Integer rowNum=1;
//        表头处理
            if (rowNum<=pageSize) {
//        遍历字段，判断是否应用了Excel注解
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.isAnnotationPresent(Excel.class)) {
//                表头 获取类的注解
                        Excel annotation = field.getAnnotation(Excel.class);
                        headers.add(annotation.value());
//                设置Excel属性
                        rowHeader.createCell(i).setCellValue(annotation.value());
//                字段
                        variable.add(field.getName());
                    }
                }
//        获取数据
                for (int i = 0; i < data.size(); i++) {
//            创建表的工作行（表头占1行，这里从第二行开始）
                    HSSFRow row = sheet.createRow(i + 1);
//            获取一行数据
                    T t = data.get(i);
                    Class<?> aClass = t.getClass();
//            填充列数据
                    for (int j = 0; j < variable.size(); j++) {
                        Field declaredField = aClass.getDeclaredField(variable.get(j));
//                提供反射对象绕过Java语言权限控制检查的权限。
                        declaredField.setAccessible(true);

                        String key = declaredField.getName();
                        Object value = declaredField.get(t);
                        row.createCell(j).setCellValue(value.toString());
                        rowNum++;
                    }
                }
            }else {
                continue;
            }
        }

        return workbook;
}


//xlsx
    @Override
    public <T> XSSFWorkbook OutputExcelXlsx(Integer pageSize,List<T> data, Class<User> clt) throws NoSuchFieldException, IllegalAccessException {
        //        将数据库数据导入
        ExcelDAOImpl edi=new ExcelDAOImpl();
        List<User> cu=edi.listAll();
        Field[] fields=clt.getDeclaredFields();

        //    创建工作簿对象
        XSSFWorkbook workbook=new XSSFWorkbook();
        //        创建工作表对象,判断需要创建几个表
        int sheetNum=data.size()/pageSize + 1;
//        将每个表放入一个链表中
        List<XSSFSheet> sheets=new ArrayList<>();
        if (sheetNum<=3){
            for (int i = 0; i < 4; i++) {
//                xlsx格式的表格必须要起码三张表才能打开
                XSSFSheet sheet=workbook.createSheet();
                workbook.setSheetName(i,"sheet"+i);
                sheets.add(sheet);
            }
        }else {
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = workbook.createSheet();
                workbook.setSheetName(i,"sheet"+i);
                sheets.add(sheet);
            }
        }

//通过循环将表数据放入Excel每个表中
        for (int k = 0; k <sheetNum; k++) {
//            记录一个表中有多少行数据加入表中
            Integer rowNum = 0;
//            存放表头属性
            List<String> headers=new LinkedList<>();
            List<String> variable=new LinkedList<>();
//        本张表中加入的数据小于最大行时
            if (rowNum <= pageSize-1) {
                //创建表头
                Row rowHeader = sheets.get(k).createRow(0);
//        表头处理 第一行
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.isAnnotationPresent(Excel.class)) {
//                表头
                        Excel annotation = field.getAnnotation(Excel.class);
                        headers.add(annotation.value());
                        rowHeader.createCell(i).setCellValue(annotation.value());
//                字段
                        variable.add(field.getName());
                    }
                }
//                计算每页最大插入行数

                int endNum=Math.min(pageSize,data.size()-k*pageSize);

                for (int i = 0; i < endNum; i++) {
//                    ------------------------------------------------行
//            创建表的工作行（表头占1行，这里从第二行开始）
                    XSSFRow row = sheets.get(k).createRow(i + 1);
//            获取一行数据

                    T t = data.get(i+k*pageSize);
                    Class<?> aClass = t.getClass();
//            填充列数据
//                    --------------------------------------------------列
                    loop:for (int j = 0; j < variable.size(); j++) {
                        Field declaredField = aClass.getDeclaredField(variable.get(j));
                        declaredField.setAccessible(true);
                        String key = declaredField.getName();
                        Object value = declaredField.get(t);
//                        判断值是否为空,空的话不填继续下一个
                        if (value.equals("")||value==null){
                            continue loop;
                        }
                        row.createCell(j).setCellValue(value.toString());
                    }
                    rowNum++;

                }
            }else {
                continue;
            }

        }
        return workbook;
    }


}
