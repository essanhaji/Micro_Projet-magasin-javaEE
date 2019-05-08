package mesCommandes;
import javax.servlet.http.Cookie;

public class Identification {
	 static String chercheNom (Cookie [] cookies) { 
		 String nomCookie=null;
		 for(int i=0;i<cookies.length;i++) {
			 if(cookies[i].getName().equals("nomCookie"))
				 nomCookie=cookies[i].getValue();
		 }
		 return nomCookie;
	 }
}
