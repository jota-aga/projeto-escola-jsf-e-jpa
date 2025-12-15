package service;

import java.io.Serializable;
import java.util.Set;

import javax.inject.Inject;

import modelo.Aluno;
import modelo.Curso;
import repositorio.AlunoDAO;
import repositorio.CursoDAO;
import util.Transacional;

public class CursoService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private CursoDAO dao;
	
	@Inject
	private AlunoDAO alunoDAO;
	
	@Transacional
	public void salvarCurso(Curso curso) {
		dao.salvar(curso);
	}
	
	@Transacional
	public void excluirCurso(Curso curso) {
		Set<Aluno> alunos = curso.getAlunos();
		
		for(Aluno a : alunos) {
			a.getCursos().remove(curso);
			alunoDAO.salvar(a);
		}
		dao.excluir(curso);
	}
}
