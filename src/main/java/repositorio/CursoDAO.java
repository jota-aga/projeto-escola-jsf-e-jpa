package repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Aluno;
import modelo.Curso;

public class CursoDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public CursoDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	public CursoDAO() {
		
	}
	
	public void salvar(Curso aluno) {
		manager.merge(aluno);
	}
	
	public void excluir(Curso curso) {
		curso = manager.find(Curso.class, curso.getId());
		manager.remove(curso);
	}
	
	public List<Curso> procurarPorNome(String nome) {
		Query q = manager.createQuery("select a from Curso a where a.nome like :nome");
		
		q.setParameter("nome", '%'+nome+'%');
		
		return q.getResultList();
	}
	
	public List<Curso> procurarTodos() {
		Query q = manager.createQuery("from Curso");
		
		return q.getResultList();
	}
	
	public Curso procurarPorId(Integer id) {
		Curso curso = manager.find(Curso.class, id);
		
		return curso;
	}
}
