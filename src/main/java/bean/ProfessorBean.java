package bean;

import java.io.Serializable;
import java.util.Date;
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
import modelo.Nota;
import modelo.Professor;
import modelo.TipoDeUsuario;
import repositorio.AlunoDAO;
import repositorio.NotaDAO;
import service.NotaService;
import util.AddMessageUtil;

@Named
@ViewScoped
public class ProfessorBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoDAO alunoDAO;
	
	@Inject
	private NotaService notaService;
	
	@Inject
	private NotaDAO notaDAO;
	
	private Professor professor;
	
	private List<Aluno> alunos;
	
	private List<Nota> notas;
		
	private Aluno aluno;
	
	private Nota nota;
		
	private Curso curso;
	
	
	@PostConstruct
	public void prepararPagina() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		Object usuarioLogado = session.getAttribute("usuario");
		
		if(usuarioLogado instanceof Professor) {
			professor = (Professor) usuarioLogado;
		}
		
		procurarAlunosPorCurso();
	}
	
	public void procurarAlunosPorCurso() {
		alunos = alunoDAO.procurarAlunoPorCurso(professor.getCurso());
	}
	
	public void procurarNotasPorAlunoECurso(Aluno aluno) {
		this.aluno = aluno;
		notas = notaDAO.procurarPorCursoEAluno(professor.getCurso(), aluno);
	}
	
	public void prepararAdicaoDeNota(Aluno aluno) {
		nota = new Nota();
		nota.setAluno(aluno);
		nota.setCurso(professor.getCurso());
		nota.setData(new Date());
	}
	
	public void prepararEdicaoDeNota(Nota nota) {
		this.nota = nota;
	}
	
	public void salvarNota() {
		notaService.salvarNota(nota);
		AddMessageUtil.adicionarMensagemEAtualizar("Nota Editada", "salvarSucessoNota", FacesMessage.SEVERITY_INFO, null, "mainForm:messages");
		adicionarNotaNaLista(nota);
	}
	
	public void excluirNota(Nota nota) {
		notaService.excluirNota(nota);
		
		AddMessageUtil.adicionarMensagemEAtualizar("Nota Excluida", "excluirSucessoNota", FacesMessage.SEVERITY_INFO, null, "mainForm:messages");
		notas.remove(nota);
	}
	
	private void adicionarNotaNaLista(Nota nota) {
		if(notas.contains(nota)) {
			int index = notas.indexOf(nota);
			notas.set(index, nota);
		}
		else {
			notas.add(0, nota);
		}
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}
	
	public List<Nota> getNotas() {
		return notas;
	}

	public TipoDeUsuario getTipoDeUsuario() {
		if(professor != null) {
			return professor.getTipoDeUsuario();
		}
		
		return null;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}
