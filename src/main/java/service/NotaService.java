package service;

import java.io.Serializable;

import javax.inject.Inject;

import modelo.Nota;
import repositorio.NotaDAO;
import util.Transacional;

public class NotaService implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private NotaDAO dao;
	
	@Transacional
	public void salvarNota(Nota nota) {
		dao.salvarNota(nota);
	}
	
	@Transacional
	public void excluirNota(Nota nota) {
		dao.excluirNota(nota);
	}
	
	@Transacional
	public void excluirPorCurso(Integer cursoId) {
		dao.excluirPorCurso(cursoId);
	}
	
	@Transacional
	public void excluirPorAluno(Integer alunoId) {
		dao.excluirPorAluno(alunoId);
	}
}
