package repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.Professor;
import modelo.Usuario;

public class ProfessorDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public ProfessorDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	public ProfessorDAO() {
		
	}
	
	public void salvar(Professor professor) {
		manager.merge(professor);
	}
	
	public void excluir(Professor professor) {
		professor = manager.find(Professor.class, professor.getId());
		manager.remove(professor);
	}
	
	public void excluirRelacaoCurso(Integer cursoId) {
		String jpql = "update Professor set curso = :null where curso.id = :cursoId";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("null", null);
		query.setParameter("cursoId", cursoId);
		
		query.executeUpdate();
		
	}
	
	public List<Professor> procurarPorCpf(String cpf) {
		Query q = manager.createQuery("select p from Professor p where p.cpf like :cpf");
		
		q.setParameter("cpf", '%'+cpf+'%');
		
		return q.getResultList();
	}
	
	public List<Professor> procurarPorNome(String nome) {
		Query q = manager.createQuery("select a from Professor a where a.nome like :nome", Professor.class);
		
		q.setParameter("nome", '%'+nome+'%');
		
		return q.getResultList();
	}
	
	public List<Professor> procurarPorCurso(Integer cursoId){
		Query q = manager.createQuery("select p from Professor p where p.curso.id = :cursoId", Professor.class);
		q.setParameter("cursoId", cursoId);
		
		return q.getResultList();
	}
	
	public List<Professor> procurarTodos() {
		Query q = manager.createQuery("from Professor");
		
		return q.getResultList();
	}
	
	public Professor procurarPorId(Integer id) {
		Professor professor = manager.find(Professor.class, id);
		return professor;
	}
	
	public Professor procurarPorUsuario(Usuario usuario) {
		try {
			String jpql = "select p from Professor p where p.usuario = :usuario";
			
			Query q = manager.createQuery(jpql);
			
			q.setParameter("usuario", usuario);
			
			Professor professor = (Professor) q.getSingleResult();
			
			return professor;
		} catch (NoResultException e) {
			return null;
		}
	}
}
