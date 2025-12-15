package bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import modelo.TipoUsuario;
import service.LoginService;
import util.MensagemBundle;

@Named
@SessionScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String tipoLogin = "aluno";
	
	private String login;
	
	private String senha;
	
	private TipoUsuario usuario;
	
	@Inject
	private LoginService service;
	
	public String validarLogin() {
		System.out.println("Chamou método");
		if(tipoLogin.equals("aluno")) {
			usuario = service.autenticarAluno(login, senha);
		}
		else {
			usuario = service.autenticarProfessor(login, senha);
		}
		
		if(usuario == null) {
			String mensagemErro = MensagemBundle.mensagem("loginIncorreto");
			FacesContext.getCurrentInstance().addMessage("loginForm:loginMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensagemErro));
			FacesContext.getCurrentInstance().validationFailed();
			return null;
		}
		else {
			HttpSession session =  (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("usuarioLogado", usuario);
			if(usuario == TipoUsuario.ALUNO) {
				return "aluno?faces-redirect=true";
			}
			else {
				
				return "professor?faces-redirect=true";
			}
		}
	}
	
	public String logout() {
		usuario = null;
		HttpSession session =  (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("usuarioLogado", usuario);
		FacesContext.getCurrentInstance().addMessage("loginForm:loginMessages", new FacesMessage(null, "Logout com sucesso"));
		RequestContext.getCurrentInstance().update("loginForm:loginMessages");
		return "login?faces-redirect=true";
	}

	public String getTipoLogin() {
		return tipoLogin;
	}

	public void setTipoLogin(String tipoLogin) {
		this.tipoLogin = tipoLogin;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TipoUsuario usuario) {
		this.usuario = usuario;
	}
}
