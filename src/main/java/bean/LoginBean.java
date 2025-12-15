package bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import modelo.Aluno;
import modelo.Professor;
import repositorio.AlunoDAO;
import repositorio.ProfessorDAO;

@Named
@SessionScoped
public class LoginBean implements Serializable{
	
	private String tipoLogin = "aluno";
	
	private String login;
	
	private String senha;
	
	@Inject
	private AlunoDAO alunoDAO;
	
	@Inject
	private ProfessorDAO professorDAO;
	
	public void validarLogin() {
		if(tipoLogin.equals("aluno")) {
			List<Aluno> listaAluno = alunoDAO.procurarPorMatricula(login);
			if(listaAluno.isEmpty()) {
				
			}
			else {
				Aluno aluno = listaAluno.get(0);
				if(!senha.equals(aluno.getSenha())) {
					
				}
				
			}
		}
		else {
			List<Professor> listaProfessor = professorDAO.procurarPorCpf(login);
			if(listaProfessor.isEmpty()) {
				
			}
			else {
				Professor professor = listaProfessor.get(0);
				if(!senha.equals(professor.getSenha())) {
					
				}
			}
		}
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
}
