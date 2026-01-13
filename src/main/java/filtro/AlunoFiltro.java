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

import modelo.Aluno;
@WebFilter(urlPatterns = {"/aluno.xhtml"})
public class AlunoFiltro implements javax.servlet.Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest HttpRequest = (HttpServletRequest) request;
		HttpServletResponse HttpResponse = (HttpServletResponse) response;
		
		HttpSession session = HttpRequest.getSession();
		
		Object usuario = session == null? null : session.getAttribute("usuario");
		
		if(session == null || !(usuario instanceof Aluno)) {
			String redirect = HttpRequest.getContextPath()+"/login.xhtml";
			HttpResponse.sendRedirect(redirect);
		}
		
		chain.doFilter(request, response);
	
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
