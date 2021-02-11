//package validator;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.sql.SQLException;
import java.util.Properties;

public class LoginVerifier {
    
    public static boolean validate(String uname, String pass) throws IOException, SQLException {
        
        boolean status = false;
        try {
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    
    LoginVerifier login = new LoginVerifier();
    Class class1= login.getClass();
    URL property_file = class1.getResource("resources/config.properties");
    
    File file = new File(property_file.getPath());
    FileInputStream fileInput = new FileInputStream(file);
    Properties properties = new Properties(); 
    properties.load(fileInput);
    String ServerName_db = "jdbc:mysql://"+ properties.getProperty("ServerName")+":3306/"+ properties.getProperty("database");
    Connection con= DriverManager.getConnection(ServerName_db,properties.getProperty("userName"),properties.getProperty("password"));
        
     PreparedStatement ps = con.prepareStatement("SELECT * from employee WHERE employeeid=?");
     
     ps.setString(1, uname);
       //ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            
       //     if("parth".equals(uname) && "parth".equals(pass)) 
         if(rs.next())
         { 
             String pass1=uname+"_"+rs.getString("dob");
             if(pass.equals(pass1))
            status = true;
          
            //rs.close();
            //ps.close();
            
           // con.close();
            
         }
        }
        catch(Exception e) {
            System.out.println(e);
        }   
        return status;
    }
}
