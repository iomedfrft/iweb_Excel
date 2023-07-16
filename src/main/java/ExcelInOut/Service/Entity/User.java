package ExcelInOut.Service.Entity;

import ExcelInOut.Service.Entity.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hss
 * @date 2023/7/14 19:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {


//将Excel这张标签贴到 username这个属性上
    @Excel(value = "username")
    private String username;

    @Excel(value = "password")
    private String password;

    @Excel(value = "gender")
    private String gender;



}
