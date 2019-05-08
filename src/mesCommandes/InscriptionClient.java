package mesCommandes;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InscriptionClient extends HttpServlet {
	boolean identique (String recu, String cookie) {            
		return ((recu != null) && (recu.length() >3) && (cookie != null) && (recu.equals(cookie) ));         
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String nomRecu=null, motPasseRecu=null;
		 String nomCookie=null, motPasseCookie=null; 
		 
		 nomRecu=request.getParameter("nom");
		 motPasseRecu=request.getParameter("motdepasse");
		 
		 Cookie[] allCookies=request.getCookies();

		 try {
			 for(int i=0;i<allCookies.length;i++) {
				 if(allCookies[i].getName().equals("nomCookie"))
					 nomCookie=allCookies[i].getValue();
				 if(allCookies[i].getName().equals("motPasseCookie"))
					 motPasseCookie=allCookies[i].getValue();	
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter(); 
		 
		  if (nomCookie==null && nomRecu==null){ 
		      out.println("<html>");            
		      out.println("<body>");           
		      out.println("<head>");       
		      out.println("<title> inscription d'un client  </title>");   
		      out.println("</head>");               
		      out.println("<body bgcolor='white' >"); 
		      out.println( nomRecu +" | "+ motPasseRecu +" | "+ nomCookie +" | "+ motPasseCookie ); 
		      out.println("<h3>" + "Bonjour,  vous devez vous inscrire " + "</h3>");                
			  out.println("<h3>" + "Attention mettre  nom et le mot de passe avec plus de 3 caracteres" + "</h3>");     
			  out.print(" <form action='sinscrire' method='GET' > ");           
			  out.println("nom");
			  out.println("<input type='text' size='20' name='nom'  >");  
			  out.println("<br><br>");            
			  out.println("mot de passe");   
			  out.println("<input type='password' size='20' name='motdepasse'> <br><br>");  
			  out.println("<input type='submit' value='inscription'>");              
			  out.println("</form>");   
			  out.println("</body>");        
			  out.println("</html>");     
		  }
		  else if(nomCookie==null && nomRecu!=null){ 
			  response.addCookie(new Cookie("nomCookie",nomRecu));
			  response.addCookie(new Cookie("motPasseCookie",motPasseRecu));
			  out.println("<html>");            
		      out.println("<body>");           
		      out.println("<head>");       
		      out.println("<title> Demande de connexion </title>");   
		      out.println("</head>");               
		      out.println("<body bgcolor='white' >"); 
		      out.println( nomRecu +" | "+ motPasseRecu +" | "+ nomCookie +" | "+ motPasseCookie ); 
		      out.println("<h3>" + "Bonjour,  Si vous avez un compte, veillez connecter" + "</h3>");                
			  out.print(" <form action='login' method='GET' > ");  
			  out.println("<input type='submit' value='Login'>");              
			  out.println("</form>");   
			  out.println("</body>");        
			  out.println("</html>");  
		  }
		  else if(identique(nomRecu,nomCookie) && identique(motPasseRecu,motPasseCookie))  {
			  out.println("<html>");            
		      out.println("<body>");           
		      out.println("<head>");       
		      out.println("<title> Allez a l'achat </title>");   
		      out.println("</head>");               
		      out.println("<body bgcolor='white' >"); 
		      out.println("<h3>" + "Vous etes bien connecté" + "</h3>");                
			  out.println("<a href='/Micro_Projet/achat'>Continue</a>");        
			  out.println("</body>");        
			  out.println("</html>"); 
		  }
		  else{
			  out.println("<html>");            
		      out.println("<body>");           
		      out.println("<head>");       
		      out.println("<title> Login d'un client  </title>");   
		      out.println("</head>");               
		      out.println("<body bgcolor='white' >"); 
		      out.println( nomRecu +" | "+ motPasseRecu +" | "+ nomCookie +" | "+ motPasseCookie ); 
		      out.println("<h3>" + "Authentification " + "</h3>");                
			  out.print(" <form action='login' method='GET' > ");  
			  out.println("nom");
			  out.println("<input type='text' size='20' name='nom'  >");  
			  out.println("<br><br>");            
			  out.println("mot de passe");   
			  out.println("<input type='password' size='20' name='motdepasse'> <br><br>");  
			  out.println("<input type='submit' value='Login'>");              
			  out.println("</form>");   
			  out.println("</body>");        
			  out.println("</html>"); 
		  }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
