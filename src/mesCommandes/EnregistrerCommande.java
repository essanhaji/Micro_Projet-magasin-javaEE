package mesCommandes;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnregistrerCommande extends HttpServlet {

	Connection connexion=null;   
	Statement stmt=null;    
	PreparedStatement pstmt=null;
	
	 protected void OuvreBase() {  
		 try {        
			 Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
			 connexion = DriverManager.getConnection("jdbc:mysql://localhost/magasin","root","");   
			 connexion.setAutoCommit(true);     
			 stmt = connexion.createStatement();    
		 }           
		 catch (Exception E) {   
			 System.out.println(" -------- probleme ouvrir la base " + E.getClass().getName() );  
			 E.printStackTrace();           
		}      
	 }
	 
	 protected void fermeBase() {   
		 try {               
			 stmt.close();    
			 connexion.close();    
		 }
		 catch (Exception E) {  
			 System.out.println(" -------- probeme  fermeture la base" + E.getClass().getName() );  
			 E.printStackTrace();         
		  }
	 } 
	 protected void AjouteNomBase(String nom) {  
		 try {
			 ResultSet rset = null;        
			 pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
			 pstmt.setString(1,nom);          
			 rset=pstmt.executeQuery();     
			 if (!rset.next())          
				 stmt.executeUpdate("INSERT INTO personnel (nom) VALUES  ('" +  nom + "' )" );    
			 }      
		 catch (Exception E) {
			 System.out.println(" - ajouter nom base  " + E.getClass().getName());   
			 E.printStackTrace();         
		 }
	 } 
	 
	 protected int getId(String nom) {
		 int id_user=0;
		 try {
				pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
				 pstmt.setString(1,nom);          
				 ResultSet rset=pstmt.executeQuery();
				 if(rset.next()) {
					 id_user=rset.getInt("numero");
				 }
		 }catch (SQLException e) {
					e.printStackTrace();}
		 return id_user;
	 }
	 protected void AjouteCommandeBase(String nom, HttpSession session ){ 
		     
		 
		
		 Enumeration names = session.getAttributeNames();
	        while (names.hasMoreElements()) {           
	        	String name = (String) names.nextElement();   
	        	String value = session.getAttribute(name).toString();  
	        	try {
					 stmt.executeUpdate("INSERT INTO commande (article,qui) VALUES  ('" +  value + "', '" +  getId(nom) + "')" );    
				}catch (SQLException e) {
					e.printStackTrace();
				}	        	
	        }
     }   
	 protected void MontreCommandeBase(String nom, PrintWriter out) {     
		 try {
			 int id_user=getId(nom);
			 ResultSet rset = null;        
			 pstmt= connexion.prepareStatement("select article from commande where qui=?");
			 pstmt.setInt(1,id_user); 
			 rset=pstmt.executeQuery();
			 out.println("<p>From base de données</p>"); 
			 out.println("<center><br><table border=1>"); 
			 while(rset.next()) {
				 
				 out.println("<tr><td>"+rset.getString("article")+"</td></tr>"); 

			 }
    	 	 out.println("</table></center>");    

		} catch (SQLException e) {
			e.printStackTrace();
		}
     }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom = null;      
		int nbreProduit = 0;   
		Cookie[] cookies = request.getCookies();
		boolean connu = false;       
		nom = Identification.chercheNom(cookies);   
		OuvreBase();        
		AjouteNomBase(nom);
        response.setContentType("text/html");   
        PrintWriter out = response.getWriter();   
        out.println("<html>");       
        out.println("<body>");    
        out.println("<head>");        
        out.println("<title>  votre commande </title>");   
        out.println("</head>");          
        out.println("<body bgcolor=\"white\">");     
        out.println("<center><h3>" + "Bonjour " + nom  + " voici ta nouvelle commande" + "</h3>"); 
        HttpSession session = request.getSession();         
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements()) {           
        	nbreProduit++;               
        	String name = (String) names.nextElement();   
        	String value = session.getAttribute(name).toString();  
        	out.println(name + " = " + value + "<br>");         
        }
        AjouteCommandeBase(nom,session);         
        out.println("<h3>" + "et voici " + nom  + "  ta commande  complete" + "</h3>");  
        MontreCommandeBase(nom, out);

        out.println("<A HREF=vider> Vous pouvez commandez un autre disque </A><br></center> "); 
        out.println("</body>");       
        out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
