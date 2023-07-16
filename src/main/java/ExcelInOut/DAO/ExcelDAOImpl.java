package ExcelInOut.DAO;

import ExcelInOut.Service.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hss
 * @date 2023/7/14 23:19
 */
public class ExcelDAOImpl implements ExcelDAO {
    @Override
    public List<User> listAll() {
        List<User> lu=new ArrayList<>();
        String sql="select * from user";
        try (Connection c=Util.getConnection();
            PreparedStatement ps=c.prepareStatement(sql);){
            ResultSet re=ps.executeQuery();
            while (re.next()){
                String username=re.getString("username");
                String password=re.getString("password");
                String gender=re.getString("gender");
                User u=new User(username,password,gender);
                lu.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lu.isEmpty()?null:lu;
    }

    @Override
    public void insert(User u) {
        String sql="insert into user(username,password,gender) values(?,?,?)";
        try(Connection c=Util.getConnection();
        PreparedStatement ps=c.prepareStatement(sql);){
            if ((u.getUsername()==null||u.getUsername().equals(""))){
                u.setUsername("default");
            }else if(u.getPassword()==null||u.getPassword().equals("")){
                u.setPassword("default");
            }else if (u.getGender()==null||u.getGender().equals("")){
                u.setGender("default");
            }
            ps.setString(1,u.getUsername());
            ps.setString(2,u.getPassword());
            ps.setString(3,u.getGender());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
