package mesCommandes;

import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class EnregistrerCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection connexion=null;
    Statement stmt=null;
    PreparedStatement pstmt=null;
    
    public EnregistrerCommande() {
        super();
        
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
			out.println("<title> votre commande </title>");
			out.println("</head>");
			out.println("<body bgcolor=\"white\">");
			out.println("<h3>" + "Bonjour " + nom + " voici ta nouvelle commande" + "</h3>");
		HttpSession session = request.getSession();
		Enumeration names = session.getAttributeNames();
		while (names.hasMoreElements()) {
		nbreProduit++;
		String name = (String) names.nextElement(); 
		String value = session.getAttribute(name).toString();
		    out.println(name + " = " + value + "<br>");
		}
		AjouteCommandeBase(nom,session);
		    out.println("<h3>" + "et voici " + nom + " ta commande complete" + "</h3>");
		MontreCommandeBase(nom, out);
			out.println("<br><A HREF=vider> Vous pouvez commandez un autre disque </A><br> ");
			out.println("</body>");
			out.println("</html>");
		}
		protected void OuvreBase() {
			try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
				connexion = DriverManager.getConnection("jdbc:mysql://localhost/magasin?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","hajarbsf");
				connexion.setAutoCommit(true);
				stmt = connexion.createStatement();
			}
			catch (Exception E) { 
				log(" --------probeme " + E.getClass().getName() );
				E.printStackTrace();
			} 
		}
		protected void fermeBase() {
			try {
				stmt.close(); 
				connexion.close(); 
			}
			catch (Exception E) { 
				log(" --------probeme " + E.getClass().getName() );
				E.printStackTrace();
			} 
		} 
		protected void AjouteNomBase(String nom) {
			try {
				ResultSet rset= null;
				pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
				pstmt.setString(1,nom);
				rset=pstmt.executeQuery();
				if (!rset.next()) 
				
				stmt.executeUpdate("insert into personnel(nom) values ('" + nom + " ')");
			}
			catch (Exception E) {
				log(" -probeme " + E.getClass().getName() );
				E.printStackTrace();
			}
			}
			
			protected void AjouteCommandeBase(String nom, HttpSession session ) {
				// ajoute le contenu du panier dans la base
				Enumeration names = session.getAttributeNames();
				while (names.hasMoreElements()) {
				String name = (String) names.nextElement(); 
				String value = session.getAttribute(name).toString();
				
				try{
					stmt.executeUpdate("INSERT INTO commande(article,qui) value('"+value+"','"+nom+"')");
				}
				catch(SQLException e) {
					log(" -probeme " + e.getClass().getName() );
					e.printStackTrace();
				}
				}
			}
			
			
			
			protected void MontreCommandeBase(String nom, PrintWriter out) {
				// affiche les produits présents dans la base
				try {
					ResultSet r=stmt.executeQuery("SELECT article FROM commande ");
					out.println("Les produits de votre panier sont :");
					while(r.next()){
						
						out.println("<br>"+r.getString(1));
					}
				}
				catch(Exception e){
					log(" -probeme " + e.getClass().getName() );
					e.printStackTrace();
				}
				
				}
			
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
