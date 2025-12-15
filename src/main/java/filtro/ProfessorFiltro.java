package filtro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.TipoUsuario;

@WebFilter(urlPatterns = {"/curso.xhtml", "/professor.xhtml", "/index.xhtml"})
public class ProfessorFiltro implements javax.servlet.Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest requestHttp = (HttpServletRequest) request;
		HttpServletResponse responseHttp = (HttpServletResponse) response;
		
		HttpSession session = requestHttp.getSession(false);
		
		TipoUsuario usuario = session == null? null : (TipoUsuario) session.getAttribute("usuarioLogado");
		
		if(usuario == null || usuario != TipoUsuario.PROFESSOR) {
	
			String redirect = requestHttp.getContextPath()+"/login.xhtml";
			responseHttp.sendRedirect(redirect);
			
			return;
		}
		
		chain.doFilter(request, response);		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
