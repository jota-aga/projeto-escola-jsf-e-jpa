package repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Aluno;

public class AlunoDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public AlunoDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	public AlunoDAO() {
		
	}
	
	public void salvar(Aluno aluno) {
		manager.merge(aluno);
	}
	
	public void excluir(Aluno aluno) {
		aluno = manager.find(Aluno.class, aluno.getId());
		manager.remove(aluno);
	}
	
	public List<Aluno> procurarPorMatricula(String matricula) {
		Query q = manager.createQuery("select a from Aluno a where a.matricula like :matricula");
		
		q.setParameter("matricula", '%'+matricula+'%');
		
		return q.getResultList();
	}
	
	public List<Aluno> procurarPorNome(String nome) {
		Query q = manager.createQuery("select a from Aluno a where a.nome like :nome");
		
		q.setParameter("nome", '%'+nome+'%');
		
		return q.getResultList();
	}
	
	public List<Aluno> procurarTodos() {
		Query q = manager.createQuery("from Aluno");
		
		return q.getResultList();
	}
	
	public Aluno procurarPorId(Integer id) {
		Aluno aluno = manager.find(Aluno.class, id);
		return aluno;
	}
}
