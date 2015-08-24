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
 
@WebServlet("/SearchDatabase")
public class SearchDatabase extends HttpServlet  
{
	static String person, company, fullname, coyname="";
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/SearchForm.jsp").forward(req, res);
		
		}
		
	
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
    	String search = req.getParameter("input");
        

		try
        {
			
             Class.forName("oracle.jdbc.driver.OracleDriver");
             Connection con=DriverManager.getConnection("jdbc:oracle:thin:testuser/password@localhost","testdb","password");
             Statement st=con.createStatement();
             System.out.println("connection established successfully...!!");
             ResultSet rs=st.executeQuery("select FULLNAME from CUSTOMERS where LASTNAME like '"+search+"%'");
             System.out.println("select FULLNAME from CUSTOMERS where LASTNAME like '"+search+"%'");
             System.out.println(rs);
            
             
             
             person+= "<table border=1>";
             person+= "<tr><th>Full Names</th>"
             		+ "</tr>";
             
             company+= "<table border=1>";
             company+= "<tr><th>Companies</th></tr>";
             
             	System.out.println(rs.next());
                 while(rs.next())
                 {
                	 System.out.println("ERROR CHECK1");
                	 
                	 fullname = rs.getString("FULLNAME"); 

                	 person+="<tr><td>"+"<a href =" + "\"PersonDetails?individual=" +fullname+"\"" + ">" + fullname +"</a>"
                    		+"</td></tr>";
    	 
                 }
                 ResultSet cs=st.executeQuery("select COMPANY from COMPANIES where COMPANY like '"+search+"%'");   
                 while (cs.next())
                 {
                	 System.out.println("ERROR CHECK2");

                	 coyname = cs.getString("COMPANY");
                     //  	 assignmentName = rs.getString("ASSIGNMENT_NAME");
                     //  	 date = rs.getString("ASSIGNMENT_DATE");
                     //  	 type = rs.getString("ASSIGNMENT_TYPE");
                     //  	 grade = rs.getInt("GRADE");
                       	
                           
                           
                       	 company+="<tr><td>"+coyname
                          // 		+"</td><td>"+assignmentName
                          // 		+"</td><td>"+type
                          // 		+"</td><td>"+date
                          // 		+"</td><td>"+grade
                           		+"</td></tr>";
                	 
                 }
                 con.close();
      
        }
        catch (Exception e){
            e.printStackTrace();
        }
        req.setAttribute("message",person);
        req.setAttribute("message2",company);
		getServletContext().getRequestDispatcher("/SearchOutput.jsp").forward(req, res);
    }
}