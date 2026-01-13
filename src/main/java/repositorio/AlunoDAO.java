package repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.Aluno;

import modelo.Curso;import modelo.Curso;
import modelo.Usuario;

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
	
	public Aluno procurarPorMatricula(String matricula) {
		try {
			Query q = manager.createQuery("select a from Aluno a where a.matricula = :matricula");
			
			q.setParameter("matricula", matricula);
			
			return (Aluno) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
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
	
	public Aluno procurarPorUsuario(Usuario usuario) {
		try {
			String jpql = "select a from Aluno a where a.usuario = :usuario";
			
			Query q = manager.createQuery(jpql);
			
			q.setParameter("usuario", usuario);
			
			Aluno aluno = (Aluno) q.getSingleResult();
			
			return aluno;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Aluno> procurarAlunoPorCurso(Curso curso){
		String jpql = "select a from Aluno a join a.cursos c where c = :curso";
		Query q = manager.createQuery(jpql);
		q.setParameter("curso", curso);
		
		return q.getResultList();
	}
}
