
//package validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.HttpSession;


public class ValidateServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String uname = request.getParameter("uname");
        String pass = request.getParameter("pass");
        String usertype = request.getParameter("usertype");//its actually db name
         if(usertype.equals("0"))
             response.sendRedirect("index.jsp?id=Please select User type");
               
       try {Class.forName("com.mysql.jdbc.Driver").newInstance();
            
     
    InputStream  fileInput = getClass().getClassLoader().getResourceAsStream("resources/config.properties");

    Properties properties = new Properties(); 
    properties.load(fileInput);
    String ServerName_db = "jdbc:mysql://"+ properties.getProperty("ServerName")+":3306/"+ properties.getProperty("database");
    Connection con= DriverManager.getConnection(ServerName_db,properties.getProperty("userName"),properties.getProperty("password"));

     //Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll","root","root");
    
          PreparedStatement ps = con.prepareStatement("SELECT * from "+usertype+" WHERE id=?");
          ps.setString(1, uname);
           //ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            
       //     if("parth".equals(uname) && "parth".equals(pass)) 
         if(rs.next())
         { 
             String pass1=uname+"_"+rs.getString("dob");
             out.println(pass1);
             if(true)
             {
             
            HttpSession session=request.getSession();
            String username1=rs.getString("salutation")+" "+rs.getString("firstname")+" "+rs.getString("middlename")+" "+rs.getString("lastname");
            session.setAttribute("username",username1);
            session.setAttribute("id",uname);
            session.setAttribute("usertype",usertype);
            if(usertype.equals("employee")) {   
                RequestDispatcher rd = request.getRequestDispatcher("welcomeemployee.jsp");
                rd.forward(request, response);
            }
            if(usertype.equals("accountant")) {   
                RequestDispatcher rd = request.getRequestDispatcher("welcomeaccountant1.jsp");
                rd.forward(request, response);
            }
            if(usertype.equals("hr")) {   
                RequestDispatcher rd = request.getRequestDispatcher("welcomehr.jsp");
                rd.forward(request, response);
            }
            if(usertype.equals("manager")) {   
                RequestDispatcher rd = request.getRequestDispatcher("welcomemanager.jsp");
                rd.forward(request, response);
            }
            
         }
         rs.close();
         ps.close();
         con.close();
         fileInput.close();
         }
       
         
        else {
                 rs.close();
                 ps.close();
                 con.close();

                //out.println("<h4 style=\"color:red;\">Invalid username & password</h4>");
                response.sendRedirect("index.jsp?id=Invalid username or password");
                //RequestDispatcher rd = request.getRequestDispatcher("index.html");
                //rd.include(request, response);
                
        
        }
        }
       catch(Exception e){System.out.println(e);}
       
    }
}

   
