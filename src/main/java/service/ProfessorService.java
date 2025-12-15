package service;

import java.io.Serializable;

import javax.inject.Inject;

import modelo.Professor;
import repositorio.ProfessorDAO;
import util.Transacional;

public class ProfessorService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private ProfessorDAO dao;
	
	@Transacional
	public void salvarProfessor(Professor professor) {
		dao.salvar(professor);
	}
	
	@Transacional
	public void excluirProfessor(Professor professor) {
		dao.excluir(professor);
	}
	
	@Transacional
	public void excluirRelacaoCurso(Integer cursoId) {
		dao.excluirRelacaoCurso(cursoId);
	}
}
