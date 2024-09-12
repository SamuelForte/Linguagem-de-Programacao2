import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {

    public static void main(String[] args) {

    try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcfotografia","root","root");

        Statement statement = connection.createStatement();

        ResultSet ResultSet = statement.executeQuery("select * from people");

        while (ResultSet.next()) {
            System.out.println(ResultSet.getString("firstname"));

        }

    }catch(Exception e){
        e.printStackTrace();

    }
    }
}
