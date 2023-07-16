package ExcelInOut.Conroller;

import ExcelInOut.DAO.ExcelDAOImpl;

import ExcelInOut.Service.Out.FileOutput;
import ExcelInOut.Service.Entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author hss
 * @date 2023/7/15 7:11
 */
public class ExportExcel {
    ExcelDAOImpl edi=new ExcelDAOImpl();
    List<User> cu=edi.listAll();
    FileOutput fileOutput=new FileOutput();
//    输入路径
    File getBasePath(String fileExtension){
        String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString()+fileExtension;
        System.out.println("请输入想要导出的路径：");
        Scanner sc=new Scanner(System.in);
        String basePath=sc.nextLine();
        if (!new File(basePath).isDirectory()){
            return getBasePath(fileExtension);
        }
        return new File(basePath+"\\"+fileName);
    }
//导出到xls
    public void contextLoadsXls(){

        try {
            HSSFWorkbook workbook= fileOutput.OutputExcelXls(700000,cu, User.class);
//           设置sheet的Name
            workbook.setSheetName(0,"sheetName");
            workbook.write(getBasePath(".xls"));
            System.out.println("Excel表格导出成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    导出到xlsx
    public void contextLoadsXlsx(){

//        使用字节流将数据读入workbook
        try (
            FileOutputStream fos=new FileOutputStream(getBasePath(".xlsx"));){
            XSSFWorkbook workbook= fileOutput.OutputExcelXlsx(15,cu, User.class);
//            workbook.setSheetName(0,"sheetName");
            for (User u:cu){
                byte[] data=u.toString().getBytes();
                fos.write(data);
                workbook.write(fos);
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Excel表格导出成功！！！");
    }
}
