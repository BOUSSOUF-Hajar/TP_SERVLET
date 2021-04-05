package mesCommandes;

import javax.servlet.http.Cookie;

public class Identification {
	static String chercheNom(Cookie[] cookies) {
		String value = null;
		if (cookies != null) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("nomCookie")) {
					 value = cookie.getValue();
				}
			}
		}
		
		return value;
	}

}
