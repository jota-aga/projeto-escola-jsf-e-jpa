package service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import modelo.Aluno;
import modelo.Curso;
import modelo.Professor;
import repositorio.AlunoDAO;
import repositorio.CursoDAO;
import repositorio.ProfessorDAO;
import util.Transacional;

public class CursoService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private CursoDAO dao;
	
	@Inject
	private ProfessorDAO professorDAO;
	
	@Inject
	private AlunoDAO alunoDAO;
	
	@Transacional
	public void salvarCurso(Curso curso) {
		dao.salvar(curso);
	}
	
	@Transacional
	public void excluirCurso(Curso curso) {
		List<Aluno> alunos = alunoDAO.procurarAlunoPorCurso(curso);
		
		List<Professor> professores = professorDAO.procurarPorCurso(curso);
		
		alunos.forEach(aluno -> aluno.getCursos().remove(curso));
		
		professores.forEach(professor -> professor.setCurso(null));
		
		curso.setAlunos(new HashSet<>(alunos));
		curso.setProfessores(new HashSet<>(professores));
		
		dao.salvar(curso);
		dao.excluir(curso);
	}
}
