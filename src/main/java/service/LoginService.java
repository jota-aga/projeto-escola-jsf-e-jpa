package service;

import java.io.Serializable;

import java.util.List;

import javax.inject.Inject;

import modelo.Aluno;
import modelo.Professor;
import modelo.TipoUsuario;
import repositorio.AlunoDAO;
import repositorio.ProfessorDAO;

public class LoginService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ProfessorDAO professorDAO;
	
	@Inject
	private AlunoDAO alunoDAO;
	
	public TipoUsuario autenticarAluno(String login, String senha) {
		List<Aluno> listaAluno = alunoDAO.procurarPorMatricula(login);
		if(listaAluno.isEmpty()) {
			return null;
		}
		else {
			Aluno aluno = listaAluno.get(0);
			if(!senha.equals(aluno.getUsuario().getSenha())) {
				return null;
			}
			else {
				return aluno.getUsuario().getTipoUsuario();
			}
		}
	}
	
	public TipoUsuario autenticarProfessor(String login, String senha) {
		List<Professor> listaProfessor = professorDAO.procurarPorCpf(login);
		
		if(listaProfessor.isEmpty()) {
			return null;
		}
		else {
			Professor professor = listaProfessor.get(0);
			if(!senha.equals(professor.getUsuario().getSenha())) {
				return null;
			}
			else {
				return professor.getUsuario().getTipoUsuario();
			}
		}
	}
}
