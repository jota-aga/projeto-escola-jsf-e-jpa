package filtro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import modelo.Professor;

@WebFilter(urlPatterns = { "/professor.xhtml", "/curso.xhtml"})
public class LoggingFiltro implements javax.servlet.Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest requestHttp = (HttpServletRequest) request;
		Professor professor = (Professor) requestHttp.getSession().getAttribute("professor");	
		
		chain.doFilter(request, response);
		System.out.println("interceptando");
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
