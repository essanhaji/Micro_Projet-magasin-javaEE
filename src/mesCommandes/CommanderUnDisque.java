package mesCommandes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommanderUnDisque extends HttpServlet {

	static int nbreProduit = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String nom = null;
		 nbreProduit++;// a chaque fois on fait l'appelle a cette servlet on fais incrémentation, pour
		 // donner ce nombre comme id a  nos sessions.

		 String name_disque=null;//Si on trouve le nom de disque dans la liste des disques de la class stock on va le stocké ici
		 String prix_disque=request.getParameter("prix");
		 Cookie[] cookies = request.getCookies();
		 nom = Identification.chercheNom(cookies); 
		 
		 HttpSession session=request.getSession(true);
		 String codeDisque_Get=request.getParameter("code");
		 //ici on doit faire une parcour dans class stock, pour touver le nom de disque qui a comme code : le code passé par l'url (GET)
		 for (int i=0;i<Stock.leStock.length;i++) {
			 if(Stock.leStock[i][2].equals(codeDisque_Get))
				name_disque=Stock.leStock[i][0];
		 }
		
		 //l'ajoute de session, on ajoute le nom de disque et son prix
		 session.setAttribute("S_disques"+nbreProduit, name_disque+" "+prix_disque+" Euros");

		 response.setContentType("text/html"); 
		 PrintWriter out = response.getWriter(); 
		 out.println("<html>"); 
		 out.println("<body>");  
		 out.println("<head>"); 
		 out.println("<title>  votre commande </title>"); 
		 out.println("</head>");  
		 out.println("<body bgcolor=\"white\">"); 
		 out.println("<center><h3>" + "Bonjour  "+ nom + "  voici  votre commande" + "</h3>"); 
		 out.println("<h4>" + "(Voila votre Panier) "+"</h4>"); 
		 out.println("<table border=1>"); 
	 	 
		 //parcour les sessions on a
		 Enumeration names = session.getAttributeNames(); 
	 	 while (names.hasMoreElements()) { 
	 		 String name = (String) names.nextElement();     
	 		 String value = session.getAttribute(name).toString();     
	 	 	 out.println( "<tr> <td>" + value + "   " + " Euros  </td>");      
	 	 } 

	 	 out.println("</table> </form>");  
		 out.println("<A HREF=achat> Vous pouvez commandez un autre disque </A><br> "); 
		 out.println("<A HREF=enregistre> Vous pouvez enregistrer votre commande </A><br> "); 
		 out.println("</center></body>"); 
		 out.println("</html>"); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
