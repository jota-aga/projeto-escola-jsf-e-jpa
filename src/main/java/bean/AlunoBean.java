package bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import modelo.Aluno;
import modelo.Curso;
import modelo.Nota;
import modelo.TipoUsuario;
import modelo.Usuario;
import repositorio.AlunoDAO;
import repositorio.CursoDAO;
import repositorio.NotaDAO;
import service.AlunoService;
import service.NotaService;

@Named
@ViewScoped
public class AlunoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlunoDAO dao;
	
	@Inject
	private CursoDAO cursoDAO;
	
	private String termoPesquisa;
	
	private String opcaoPesquisa = "nome";
	
	@Inject
	private AlunoService service;
	
	@Inject
	private NotaService notaService;
	
	private Set<Curso> cursosSelecionados;
	
	private Curso cursoSelecionado;
	
	private List<Aluno> todosAlunos;
	
	private CursoConverter cursoConverter;
	
	private Aluno aluno;
	
	private Nota nota;
	
	public void procuraTodosAlunos() {
		this.todosAlunos = dao.procurarTodos();
	}
	
	public void prepararAlunoNovo() {
		this.aluno = new Aluno();
		this.cursosSelecionados = new HashSet<>();
	}
	
	public void prepararAlunoEdicao(Aluno aluno) {
		this.aluno = dao.procurarPorId(aluno.getId());
		cursosSelecionados = new HashSet<>(this.aluno.getCursos());
	}
	
	public void prepararAdicaoNota(Aluno aluno) {
		cursosSelecionados = new HashSet<>(aluno.getCursos());
		nota = new Nota();
		nota.setAluno(aluno);
	}
	
	public List<Aluno> getTodosAlunos(){
		return this.todosAlunos;
	}
	
	public void salvarAluno() {
		boolean matriculaRepetida =  this.validarMatriculaRepetida(aluno);
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("keywords.keywords", locale);
		
		if(!matriculaRepetida) {
			aluno.setCursos(new HashSet<>(cursosSelecionados));
			String senha = UUID.randomUUID().toString();
			Usuario usuario = new Usuario();
			usuario.setSenha(senha);
			usuario.setTipoUsuario(TipoUsuario.ALUNO);
			aluno.setUsuario(usuario);
			service.salvarAluno(aluno);
			
			
			String mensagemSuceso = bundle.getString("salvarSucessoAluno");
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagemSuceso));
			RequestContext.getCurrentInstance().update("mainForm:globalMessages");
			
			this.procuraTodosAlunos();
		}
		else {
			String mensagemErro = bundle.getString("matriculaJaExiste");
			FacesContext.getCurrentInstance().addMessage("mainForm:matricula", new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensagemErro));
			FacesContext.getCurrentInstance().validationFailed();
		}
		
	}
	
	public void excluirAluno(Aluno aluno) {
		notaService.excluirPorAluno(aluno.getId());
		service.excluirAluno(aluno);
		
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("keywords.keywords", locale);
		String mensagemSucesso = bundle.getString("excluirSucessoAluno");
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagemSucesso));
		this.procuraTodosAlunos();
	}
	
	public void salvarNota() {
		notaService.salvarNota(nota);
		cursoSelecionado = null;
	}
	
	public void pesquisarAlunos() {
		if(opcaoPesquisa.equals("matricula")) {
			this.todosAlunos = dao.procurarPorMatricula(termoPesquisa);
		}
		else {
			this.todosAlunos = dao.procurarPorNome(termoPesquisa);
		}
	}
	
	private boolean validarMatriculaRepetida(Aluno aluno) {
		List<Aluno> repetidos = dao.procurarPorMatricula(aluno.getMatricula());
		
		if(repetidos.isEmpty()) {
			return false;
		}
		else {
			if(aluno.getId() == null) {
				return true;
			}
			else {
				Aluno alunoRepetido = repetidos.get(0);
				
				if(alunoRepetido.getId() != aluno.getId()) {
					return true;
				}
				else {
					return false;
				}
			}
		}
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
	
	public boolean getAlunoExiste() {
		if(aluno == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public List<Curso> getCursos() {
		return cursoDAO.procurarTodos();
	}

	public CursoConverter getCursoConverter() {
		return this.cursoConverter = new CursoConverter(this.getCursos());
	}

	public Set<Curso> getCursosSelecionados() {
		return cursosSelecionados;
	}

	public void setCursosSelecionados(Set<Curso> cursosSelecionados) {
		this.cursosSelecionados = cursosSelecionados;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}
	
}
