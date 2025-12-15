package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import modelo.Curso;
import repositorio.CursoDAO;
import service.CursoService;
import service.NotaService;
import service.ProfessorService;

@Named
@ViewScoped
public class CursoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<Curso> todosCursos;
	
	@Inject
	private CursoDAO dao;
	
	private String termoPesquisa = "Vazio";
	
	@Inject
	private CursoService service;
	
	@Inject
	private ProfessorService professorService;
	
	@Inject 
	private NotaService notaService;
	
	private Curso curso;
	
	public void procuraTodosCursos() {
		this.todosCursos = dao.procurarTodos();
	}
	
	public void pesquisarCursosPorNome() {
		this.todosCursos = dao.procurarPorNome(termoPesquisa);
	}
	
	public void prepararCursoNovo() {
		this.curso = new Curso();
	}
	
	public void prepararCursoEdicao(Curso curso) {
		this.curso = dao.procurarPorId(curso.getId());
	}
	
	public void salvarCurso() {
		service.salvarCurso(curso);
		
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("keywords.keywords", locale);
		String mensagemSucesso = bundle.getString("salvarSucessoCurso");
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagemSucesso));
		RequestContext.getCurrentInstance().update("mainForm:globalMessages");
		
		this.procuraTodosCursos();
	}
	
	public void excluirCurso(Curso curso) {
		notaService.excluirPorCurso(curso.getId());
		professorService.excluirRelacaoCurso(curso.getId());
		
			
		service.excluirCurso(curso);
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("keywords.keywords", locale);
		String mensagemSucesso = bundle.getString("excluirSucessoCurso");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagemSucesso));
		this.procuraTodosCursos();
	}

	public List<Curso> getTodosCursos() {
		return todosCursos;
	}

	public void setTodosCursos(List<Curso> todosCursos) {
		this.todosCursos = todosCursos;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}
