package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import modelo.Aluno;
import modelo.Curso;
import modelo.Professor;
import modelo.TipoDeUsuario;
import modelo.Usuario;
import repositorio.AlunoDAO;
import repositorio.CursoDAO;
import repositorio.ProfessorDAO;
import service.AlunoService;
import service.CursoService;
import service.ProfessorService;
import util.AddMessageUtil;

@Named
@ViewScoped
public class AdminBean implements Serializable{
	
	@Inject
	private AlunoDAO alunoDAO;
	
	@Inject
	private ProfessorDAO professorDAO;
	
	@Inject
	private CursoDAO cursoDAO;
	
	@Inject
	private AlunoService alunoService;
	
	@Inject
	private ProfessorService professorService;
	
	@Inject
	private CursoService cursoService;
	
	private Aluno aluno;
	
	private Professor professor;
	
	private Curso curso;
	
	private List<Curso> cursosSelecionados;
	
	private Curso cursoSelecionado;
	
	private List<Aluno> alunos;
	
	private List<Professor> professores;
	
	private List<Curso> cursos;
	
	private boolean editandoProfessor;
	
	private boolean editandoAluno;
	
	private Usuario usuario;
	
	@PostConstruct
	public void prepararPagina() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		Object usuarioLogado = session.getAttribute("usuario");
		
		if(usuarioLogado instanceof Usuario) {
			usuario = (Usuario) usuarioLogado;
		}
		
		if (usuario == null) {
			System.out.println("================== USUARIO VAZIO");
		}
	}
	
	public void procurarTodosAlunos() {
		alunos = alunoDAO.procurarTodos();
	}
	
	public void procurarTodosProfessores() {
		professores = professorDAO.procurarTodos();
	}
	
	public void procurarTodosCursos() {
		cursos = cursoDAO.procurarTodos();
	}
	
	public void prepararEdicaoAluno(Aluno aluno) {
		this.aluno = aluno;
		editandoAluno = true;
		editandoProfessor = false;
		cursosSelecionados = new ArrayList<>(aluno.getCursos());
		cursos = cursoDAO.procurarTodos();
	}
	
	public void prepararNovoCurso() {
		curso = new Curso();
	}
	
	public void prepararEdicaoDeCurso(Curso curso) {
		this.curso = curso;
	}
	
	public void salvarCurso() {
		cursoService.salvarCurso(curso);
		AddMessageUtil.adicionarMensagemEAtualizar("Curso Salvo", "salvarSucessoCurso", FacesMessage.SEVERITY_INFO, null, "messages");
		procurarTodosCursos();
	}
	
	public void excluirCurso(Curso curso) {
		cursoService.excluirCurso(curso);
		AddMessageUtil.adicionarMensagem("Curso Excluido", "excluirSucessoCurso", FacesMessage.SEVERITY_INFO, null);
		procurarTodosCursos();
	}
	
	public void prepararEdicaoProfessor(Professor professor) {
		editandoAluno = false;
		editandoProfessor = true;
		this.professor = professor;
		cursoSelecionado = professor.getCurso();
		cursos = cursoDAO.procurarTodos();
	}
	
	public void salvarAluno() {
		boolean salvocomSucesso = alunoService.salvarAluno(aluno, cursosSelecionados);
		procurarTodosAlunos();
		
		if(salvocomSucesso) {
			AddMessageUtil.adicionarMensagemEAtualizar("Aluno Salvo", "salvarSucessoAluno", FacesMessage.SEVERITY_INFO, null, "messages");
		}
	}
	
	public void excluirAluno(Aluno aluno) {
		alunoService.excluirAluno(aluno);
		AddMessageUtil.adicionarMensagemEAtualizar("Aluno Excluído", "excluirSucessoAluno", FacesMessage.SEVERITY_INFO, null, "mainForm:messages");
		procurarTodosAlunos();
	}
	
	public void salvarProfessor() {
		boolean salvocomSucesso = professorService.salvarProfessor(professor, cursoSelecionado);
		procurarTodosProfessores();
		
		if(salvocomSucesso) {
			AddMessageUtil.adicionarMensagemEAtualizar("Professor Salvo", "salvarSucessoProfessor", FacesMessage.SEVERITY_INFO, null, "messages");
		}
	}
	
	public void excluirProfessor(Professor professor) {
		professorService.excluirProfessor(professor);
		AddMessageUtil.adicionarMensagemEAtualizar("professor Excluído", "excluirSucessoProfessor", FacesMessage.SEVERITY_INFO, null, "mainForm:messages");
		procurarTodosProfessores();
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

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public boolean isTipoProfessor() {
		return editandoProfessor;
	}
	
	public boolean isTipoAluno() {
		return editandoAluno;
	}
	
	public TipoDeUsuario getTipoDeUsuario() {
		if(usuario != null) {
			return usuario.getTipoDeUsuario();
		}
		return null;
	}
}
