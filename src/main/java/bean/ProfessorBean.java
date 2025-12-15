package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import modelo.Curso;
import modelo.Professor;
import modelo.TipoUsuario;
import modelo.Usuario;
import repositorio.CursoDAO;
import repositorio.NotaDAO;
import repositorio.ProfessorDAO;
import service.ProfessorService;

@Named
@ViewScoped
public class ProfessorBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Professor professor;
	
	private List<Professor> todosProfessores;
	
	private String termoPesquisa;
	
	private String opcaoPesquisa = "nome";
	
	@Inject
	private ProfessorDAO dao;
	
	@Inject NotaDAO notaDAO;
	
	@Inject
	private ProfessorService service;
	
	@Inject
	private CursoDAO cursoDAO;
		
	public void prepararProfessorNovo() {
		this.professor = new Professor();
	}
	
	public void prepararProfessorEdicao(Professor professor) {
		this.professor = dao.procurarPorId(professor.getId());
	}
	
	public void salvarProfessor() {
		boolean cpfRepetido = this.validarCpfRepetido(professor);
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("keywords.keywords", locale);
		
		if(cpfRepetido) {
			String mensagemErro = bundle.getString("cpfJaCadastrado");
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage("mainForm:cpf", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensagemErro));
		}
		else {
			Usuario usuario = new Usuario();
			String senha = UUID.randomUUID().toString();
			usuario.setSenha(senha);
			usuario.setTipoUsuario(TipoUsuario.PROFESSOR);
			professor.setUsuario(usuario);
			service.salvarProfessor(professor);
			
			
			String mensagemSucesso = bundle.getString("salvarSucessoProfessor");
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagemSucesso));
			RequestContext.getCurrentInstance().update("mainForm:globalMessages");
			
			this.procuraTodosProfessores();
		}	
	}
	
	public void excluirProfessor(Professor professor) {
		service.excluirProfessor(professor);
		
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("keywords.keywords", locale);
		String mensagemSucesso = bundle.getString("excluirSucessoProfessor");
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagemSucesso));
		
		this.procuraTodosProfessores();
	}
	
	public void procuraTodosProfessores() {
		this.todosProfessores = dao.procurarTodos();
	}
	
	public void pesquisarProfessores() {
		if(this.opcaoPesquisa.equals("cpf")){
			this.todosProfessores = dao.procurarPorCpf(termoPesquisa);
		}
		else {
			this.todosProfessores = dao.procurarPorNome(termoPesquisa);
		}
	}
	
	private boolean validarCpfRepetido(Professor professor) {
		List<Professor> repetido = dao.procurarPorCpf(professor.getCpf());
		
		if(repetido.isEmpty()) {
			return false;
		}
		else {
			if(professor.getId() == null) {
				return true;
			}
			else {
				Professor professorRepetido = repetido.get(0);
				
				if(professorRepetido.getId() != professor.getId()) {
					return true;
				}
				
				return false;
			}
		}
	}
	
	public List<Curso> getTodosCursos(){
		return cursoDAO.procurarTodos();
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Professor> getTodosProfessores() {
		return todosProfessores;
	}

	public void setTodosProfessores(List<Professor> todosProfessores) {
		this.todosProfessores = todosProfessores;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public String getOpcaoPesquisa() {
		return opcaoPesquisa;
	}

	public void setOpcaoPesquisa(String opcaoPesquisa) {
		this.opcaoPesquisa = opcaoPesquisa;
	}

	public CursoConverter getCursoConverter() {
		return new CursoConverter(cursoDAO.procurarTodos());
	}
}
