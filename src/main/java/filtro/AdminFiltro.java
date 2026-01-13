package filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Usuario;

@WebFilter(urlPatterns = {"/gerenciar-alunos.xhtml", "/gerenciar-professores.xhtml"})
public class AdminFiltro implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest requestHttp = (HttpServletRequest) request;
		HttpServletResponse responseHttp = (HttpServletResponse) response;
		
		HttpSession session	= requestHttp.getSession();
		
		Object usuario = session == null ? null : session.getAttribute("usuario");
		
		if(usuario == null || !(usuario instanceof Usuario)) {
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
