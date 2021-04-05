package mesCommandes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.Enumeration;

public class CommanderUnDisque extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CommanderUnDisque() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String nom = null;
		    String ordre = (String) request.getParameter("ordre");
		    int nbreProduit = 0;
		    Cookie[] cookies = request.getCookies();
		      nom = Identification.chercheNom(cookies);
		      HttpSession session = request.getSession();
		      Enumeration names = session.getAttributeNames();
				
		      response.setContentType("text/html");
		      PrintWriter out = response.getWriter();
		      out.println("<html>");
		      out.println("<body>");
		      out.println("<head>");
		      out.println("<title> Votre commande </title>");
		      
		      out.println("</head>");
		      out.println("<body bgcolor=\"white \" >");
		      if(names.hasMoreElements()) {
		    	  	out.println("<h3" + "Bonjour " + nom + " voici votre commande" + "</h3>");
		    	  	out.println("votre panier:  ");
			      while (names.hasMoreElements()){
			    	  String name = (String) names.nextElement(); 
						String value = session.getAttribute(name).toString();
			   
			    	  out.println("<br>"+name+"    :    " +value +"<br>");
			      }
		      }
		      // Création ou récupération de la session
	
		      
		      if (ordre.equals("ajouter")) {
		    	  String codeDisque = request.getParameter("code");
			      
			      
			      for (String[] stock : Stock.leStock) {
			    	  if(stock[2].equals(codeDisque)) {
			    		  session.setAttribute(codeDisque,stock[0]);
			    		  out.println("<br> Le disque : "+stock[0]+"  a été bien ajouté au panier .<br>");
			    	  }
			      }
			     
			      
			         
		      }
		     
		         
		      
		      out.println("<br> <A HREF=achat>Vous pouvez commandez un autre disque </A><br> <br>");
		      out.println("<A HREF=enregistre> Vous pouvez enregistrer votre commande </A><br> ");
		      out.println("</body>");
		      out.println("</html>");
		      }
		      
		      
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   
		doGet(request, response);
	}

}
