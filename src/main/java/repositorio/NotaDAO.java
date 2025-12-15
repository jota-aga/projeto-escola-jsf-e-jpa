package repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Nota;

public class NotaDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	public void salvarNota(Nota nota) {
		manager.merge(nota);
	}
	
	public void excluirNota(Nota nota) {
		nota = manager.find(Nota.class, nota.getId());
		manager.remove(nota);
	}
	
	public List<Nota> procurarPorCurso(Integer id){		
		Query q = manager.createQuery("select n from Nota n where n.curso.id = :cursoId",Nota.class);
		q.setParameter("cursoId", id);
		
		return q.getResultList();
	}
	
	public void excluirPorAluno(Integer alunoId) {
		Query q = manager.createQuery("delete from Nota where aluno.id = :alunoId");
		q.setParameter("alunoId", alunoId);
		
		q.executeUpdate();
	}
	
	public void excluirPorCurso(Integer cursoId) {
		Query q = manager.createQuery("delete from Nota where curso.id = :cursoId");
		q.setParameter("cursoId", cursoId);
		
		q.executeUpdate();
	}
}
