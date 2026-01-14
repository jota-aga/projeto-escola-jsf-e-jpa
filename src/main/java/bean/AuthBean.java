package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.weld.context.RequestContext;
import org.omg.CORBA.Request;

import converter.CursoConverter;
import modelo.Aluno;
import modelo.Curso;
import modelo.Professor;
import modelo.TipoDeUsuario;
import modelo.Usuario;
import repositorio.CursoDAO;
import service.AuthService;
import util.AddMessageUtil;

@Named
@ViewScoped
public class AuthBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CursoDAO cursoDAO;
	
	@Inject
	private AuthService authService;

	private Usuario usuario;
	
	private Aluno aluno;
	
	private Professor professor;
	
	private String confirmarSenha;
	
	private List<Curso> cursos;
	
	private List<Curso> cursosSelecionados;
	
	private Curso cursoSelecionado;
	
	private String cpf;
	
	private String senha;
	
	@PostConstruct
	public void prepararRegistro() {
		usuario = new Usuario();
		usuario.setTipoDeUsuario(TipoDeUsuario.ALUNO);
		aluno = new Aluno();
		aluno.setCursos(new HashSet<>());
		professor = new Professor();
		procurarTodosOsCursos();
		cursosSelecionados = new ArrayList<>();
	}
	
	public void salvarRegistro() {
		boolean isUsuarioSalvoComSucesso = authService.salvarRegistro(usuario, aluno, professor, cursosSelecionados, cursoSelecionado);
		
		if(isUsuarioSalvoComSucesso) {
			if(usuario.getTipoDeUsuario() == TipoDeUsuario.ALUNO) {
				AddMessageUtil.adicionarMensagemEAtualizar("Aluno Salvo", "salvarSucessoAluno", FacesMessage.SEVERITY_INFO, null, "mainForm:messages");
			}
			else {
				AddMessageUtil.adicionarMensagemEAtualizar("Professor Salvo", "salvarSucessoProfessor", FacesMessage.SEVERITY_INFO, null, "mainForm:messages");
			}
		}
		
	}
	
	public String fazerLogin() {
		TipoDeUsuario tipoDoUsuarioLogado = authService.autenticarUsuario(cpf, senha);
		
		if(tipoDoUsuarioLogado == TipoDeUsuario.ALUNO) {
			return "aluno.xhtml?faces-redirect=true";
		}
		else if(tipoDoUsuarioLogado == TipoDeUsuario.PROFESSOR) {
			return "professor.xhtml?faces-redirect=true";
		}
		else if(tipoDoUsuarioLogado == TipoDeUsuario.ADMIN) {
			return "gerenciar-alunos.xhtml?faces-redirect=true";
		}
		return "login.xhtml";
	}
	
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		session.setAttribute("usuario", null);
		
		return "login.xhtml?faces-redirect=true";
	}
	
	public void procurarTodosOsCursos() {
		cursos = new ArrayList<>();
		cursos = cursoDAO.procurarTodos();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public TipoDeUsuario[] getTiposDeUsuario() {
		return TipoDeUsuario.values();
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
	public CursoConverter getCursoConverter() {
		CursoConverter cursoConverter = new CursoConverter(cursos);
		
		return cursoConverter;
	}

	public List<Curso> getCursos() {
		return cursos;
	}
	
	public boolean isTipoAluno() {
		boolean isAluno = usuario.getTipoDeUsuario() == TipoDeUsuario.ALUNO ? true : false;
		
		return isAluno;
	}
	
	public boolean isTipoProfessor() {
		boolean isProfessor = usuario.getTipoDeUsuario() == TipoDeUsuario.PROFESSOR ? true : false;
		
		return isProfessor;
	}

	public List<Curso> getCursosSelecionados() {
		return cursosSelecionados;
	}

	public void setCursosSelecionados(List<Curso> cursosSelecionados) {
		this.cursosSelecionados = cursosSelecionados;
	}

	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}

	public void setCursoSelecionado(Curso cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
