package ExcelInOut.View;

import ExcelInOut.Conroller.ExportExcel;
import ExcelInOut.Conroller.ImportExcel;

import java.util.Scanner;

/**
 * @author hss
 * @date 2023/7/15 10:16
 */
public class MainView {
    public static void OutView(){
        ExportExcel exportExcel=new ExportExcel();
        System.out.println("请输入您想输出的格式：xls or xlsx");
        Scanner sc=new Scanner(System.in);
        String answer=sc.nextLine();
        while (true) {
            if (answer.equals("xls")) {
                exportExcel.contextLoadsXls();
                break;
            } else if (answer.equals("xlsx")) {
                exportExcel.contextLoadsXlsx();
                break;
            } else {
                System.out.println("输入错误，请重新输入");
            }
        }
    }
    public static void InputView(){
        ImportExcel ie=new ImportExcel();
        System.out.println("Excel导入数据库开始。。。");
        ie.JDBCLoad();
    }
}
