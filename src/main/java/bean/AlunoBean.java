package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import modelo.Aluno;
import modelo.Curso;
import modelo.Nota;
import service.AlunoService;

@Named
@ViewScoped
public class AlunoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aluno aluno;
	private List<Nota> notas;
	
	@Inject
	private AlunoService alunoService;
	
	@PostConstruct
	public void prepararPagina() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Object usuario = session.getAttribute("usuario");

		if(usuario instanceof Aluno) {
			aluno = (Aluno) usuario;
		}
	}
	
	public void verNotasDoAluno(Curso curso) {
		notas = alunoService.procurarNotasDoAluno(aluno, curso);
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public String getTipoDeUsuario() {
		if(aluno != null) {
			return aluno.getUsuario().getTipoDeUsuario().name();
		}
		return null;
		
	}
}
