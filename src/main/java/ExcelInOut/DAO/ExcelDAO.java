package ExcelInOut.DAO;

import ExcelInOut.Service.Entity.User;

import java.util.Collection;

/**
 * @author hss
 * @date 2023/7/14 23:00
 */
public interface ExcelDAO {
    /**用于向用户表插入数据
     * @param u 将所插入的id和name封装到一个类中
     */
    void insert(User u);

    /**查询所有用户信息
     * @return 返回集合
     */
    Collection<User> listAll();


}
