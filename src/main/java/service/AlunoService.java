package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import modelo.Aluno;
import modelo.Curso;
import modelo.Nota;
import repositorio.AlunoDAO;
import repositorio.NotaDAO;
import util.Transacional;


public class AlunoService implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	private AlunoDAO dao;
	
	@Inject 
	NotaDAO notaDAO;
	
	@Transacional
	public boolean salvarAluno(Aluno aluno, List<Curso> cursosSelecionados) {
		aluno.setCursos(new HashSet<>(cursosSelecionados));
		dao.salvar(aluno);
		return true;
	}
	
	@Transacional
	public void excluirAluno(Aluno aluno) {
		dao.excluir(aluno);
	}
	
	public List<Nota> procurarNotasDoAluno(Aluno aluno, Curso curso) {
		List<Nota> notas = new ArrayList<>();
		
		notas = notaDAO.procurarPorCursoEAluno(curso, aluno);
		
		return notas;
	}
}
