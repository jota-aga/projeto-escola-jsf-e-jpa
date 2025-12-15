package service;

import java.io.Serializable;

import javax.inject.Inject;

import modelo.Aluno;
import repositorio.AlunoDAO;
import util.Transacional;


public class AlunoService implements Serializable{
	private static final long serialVersionUID = 1L;
	@Inject
	private AlunoDAO dao;
	
	@Transacional
	public void salvarAluno(Aluno aluno) {
		dao.salvar(aluno);
	}
	
	@Transacional
	public void excluirAluno(Aluno aluno) {
		dao.excluir(aluno);
	}
}
