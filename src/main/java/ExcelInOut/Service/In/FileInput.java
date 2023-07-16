package ExcelInOut.Service.In;
import java.util.List;
import ExcelInOut.Service.Entity.Excel;
import java.io.*;

import ExcelInOut.Service.Entity.User;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * @author hss
 * @date 2023/7/16 0:25
 */
public class FileInput implements ImportAll {

    private File getFile() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入需要导入的文件路径：");
        String filePath = sc.nextLine();
        File file = new File(filePath);

        if (((!file.exists()) || (!filePath.contains(".xls") ))&&( (!file.exists()) || (!filePath.contains(".xlsx")))) {
            System.out.println("文件错误,请重新输入；");
            return getFile();
        }
        return file;
    }


    @Override
    public List<User> InputExcelXls(Class<User> clz) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        为返回的数据准备一个链表
        List<User> list=new ArrayList<>();

//        准备文件流，创建工作区
        FileInputStream fileInputStream=new FileInputStream(getFile());
            Workbook workbook= WorkbookFactory.create(fileInputStream);
//            获取sheet表，sheet代表获取下标为0的excel表，也就是第一张表  如果多张表。。。。。。。。。。。。。。。

            Sheet sheet=workbook.getSheetAt(0);
//            获取最大行数
            int rowNum=sheet.getPhysicalNumberOfRows();
//            获取反射字段
            Field[] fields=clz.getDeclaredFields();
//            获取第一行表头
            Row row=sheet.getRow(0);
//            获取最大列数
        int column=row.getPhysicalNumberOfCells();
//        表头校验
        for (int i = 0; i < fields.length; i++) {
            Field field=fields[i];
            if (field.isAnnotationPresent(Excel.class)){
                Excel annotation =field.getAnnotation(Excel.class);
                Cell cell=row.getCell(i);
                if (cell==null||!getCellValue(cell).equals(annotation.value())){
                    throw new RuntimeException("Excel格式错误");
                }
            }
        }
//        处理行数据
        for (int i = 0; i < rowNum; i++) {
            row=sheet.getRow(i);
//            遇到空行结束
            if (row==null){
                break;
            }
            User rowData=clz.getDeclaredConstructor().newInstance();
//            处理列数据
            for (int j = 0; j < fields.length; j++) {
                Field field=fields[j];
//                设置属性可访问
                field.setAccessible(true);
                if (field.isAnnotationPresent(Excel.class)){
                    Excel annotation=field.getAnnotation(Excel.class);
//                    按照默认顺序
                    int columnIndex=annotation.columnIndex();
                    Cell cell=row.getCell(j);
                    if (cell==null){
                        continue;
                    }
//                    获取列值
                    Object value=getCellValue(cell);
//                    设置列属性
                    setFieldValue(rowData,field,value);
                }
            }
            list.add(rowData);
        }
        return list;
    }

//    设置属性
    private <T> void setFieldValue(T rowData,Field field,Object value) throws IllegalAccessException {
        if (field.getType()==int.class||field.getType()==Integer.class){
            field.set(rowData,value);
        }else if (field.getType()==long.class||field.getType()==Long.class){
            field.set(rowData,value);
        }else if (field.getType()==double.class||field.getType()==Double.class){
            field.set(rowData,value);
        }else if (field.getType()==String.class){
            field.set(rowData,String.valueOf(value));
        }else if (field.getType()==LocalDateTime.class){
            field.set(rowData,LocalDateTime.parse(String.valueOf(value),dateTimeFormatter));
        }
    }



    //    定义日期格式
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    获取excel表数据类型
    private Object getCellValue(Cell cell){
        CellType cellType=cell.getCellType();
        Object cellValue=null;
        if (cellType==CellType._NONE){
        }else if(cellType==CellType.NUMERIC){
//            数值型
            if (DateUtil.isCellDateFormatted(cell)){
//                日期型
                Date d=cell.getDateCellValue();
                cellValue=dateTimeFormatter.format(LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault()));
            }else {
                double numericCellValue=cell.getNumericCellValue();
                BigDecimal bigDecimal=new BigDecimal(numericCellValue);
                if ((bigDecimal+".0").equals(Double.toString(numericCellValue))){
//                    整型
                    cellValue=bigDecimal;
                }else if (String.valueOf(numericCellValue).contains("E10")){
//                    科学计数法
                    cellValue=new BigDecimal(numericCellValue).toPlainString();
                }else {
//                    浮点型
                    cellValue=numericCellValue;
                }
            }
        }else if (cellType==CellType.STRING){
            cellValue=cell.getStringCellValue();
        }else if (cellType==CellType.BOOLEAN){
//            布尔型
            cellValue=cell.getBooleanCellValue();
        }else if(cellType==CellType.ERROR){
//            错误
            cellValue=cell.getErrorCellValue();
            }
        return cellValue;
    }


}
