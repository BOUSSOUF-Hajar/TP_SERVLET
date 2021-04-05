package mesCommandes;

import java.awt.desktop.SystemEventListener;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class MonFiltre
 */
@WebFilter("/MonFiltre")
public class MonFiltre implements Filter {
	private FilterConfig filterConfig = null;
	public void init(FilterConfig filterConfig) throws ServletException {
	this.filterConfig = filterConfig;
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	ServletException {
	String nom = null;
	int cst =0;
	HttpServletRequest hrequest = (HttpServletRequest) request;
	HttpServletResponse hresponse = (HttpServletResponse) response;
	Cookie[] cookies = hrequest.getCookies();
	// test s'il existe un cookie dont l'attribut est "nom"
	if (cookies !=null) {
		for( Cookie c : cookies ) {
			if(c.getName().equals("nomCookie") )  cst=1;
			
		}
	}
	if (cst==0){ // cas ou il n'existe pas appel de la servlet inscrire
		 HttpServletResponse httpResponse = (HttpServletResponse) response; 
		 httpResponse.sendRedirect("/servlet/sinscrire"); 

		
	}
	else {
	chain.doFilter(request, response); }
	}
	public void destroy() {
		
		this.filterConfig = null;
		}
		}