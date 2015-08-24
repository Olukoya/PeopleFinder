import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet("/PersonDetails")
public class PersonDetails extends HttpServlet  
{
	static String title, fname,lname,address,email,position,person, fullname, coyname, zip,id,cityID,stateID="";

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");        
          
        String singleP = req.getParameter("individual");
        
        
        if (singleP != null)
{
        try
        {
             Class.forName("oracle.jdbc.driver.OracleDriver");
             Connection con=DriverManager.getConnection("jdbc:oracle:thin:testuser/password@localhost","testdb","password");
             Statement st=con.createStatement();
             System.out.println("connection established successfully...!!");     
 
             ResultSet rs=st.executeQuery("select * from CUSTOMERS where FULLNAME ='"+singleP+"'"); 
             person+= "<table border=1>";
             person+= "<tr><th>Fullname</th>"
             		+ "<th>Title</th>"
             		+ "<th>First Name</th>"
             		+ "<th>Last Name</th>"
             		+ "<th>Address</th>"
             		+ "<th>Zip Code</th>"
             		+ "<th>Email</th>"
             		+ "<th>Position</th>"
             		+ "<th>ID</th>"
             		+ "<th>City ID</th>"
             		+ "<th>State ID</th>"
             		+ "</tr>";
             
                 while(rs.next())
                 {
                	 fullname = rs.getString("FULLNAME");
                	 title = rs.getString("TITLE");
                	 fname = rs.getString("FIRSTNAME");
                	 lname = rs.getString("LASTNAME");
                	 address = rs.getString("STREETADDRESS");
                	 zip = rs.getString("ZIPCODE");
                	 email = rs.getString("EMAILADDRESS");
                	 position = rs.getString("POSITION");
                	 id = rs.getString("ID");
                	 cityID = rs.getString("CITYID");
                	 stateID = rs.getString("STATEID");
                	 
                	person+="<tr><td>"+fullname
                    		+"</td><td>"+title
                    		+"</td><td>"+fname
                    		+"</td><td>"+lname
                    		+"</td><td>"+address
                    		+"</td><td>"+zip
                    		+"</td><td>"+email
                    		+"</td><td>"+position
                    		+"</td><td>"+id
                    		+"</td><td>"+cityID
                    		+"</td><td>"+stateID
                    		+"</td></tr>";
                 }
                 con.close();
      
        }
        catch (Exception e){
            e.printStackTrace();
        }
 
        req.setAttribute("message",person);
		getServletContext().getRequestDispatcher("/SearchOutput.jsp").forward(req, res);
		
	}
        }
	
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
    }
}