package repositorio;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import modelo.Usuario;

public class UsuarioDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public UsuarioDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	public UsuarioDAO() {
		
	}
	
	public void salvar(Usuario usuario) {
		manager.merge(usuario);
	}
	
	public void excluir(Usuario usuario) {
		usuario = manager.find(Usuario.class, usuario.getId());
		manager.remove(usuario);
	}
	
	
	public Usuario procurarPorCpf(String cpf) {
		try {
			Query q = manager.createQuery("select u from Usuario u where u.cpf = :cpf");
			
			q.setParameter("cpf", cpf);
			
			return (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Usuario procurarPorId(Integer id) {
		Usuario usuario = manager.find(Usuario.class, id);
		return usuario;
	}
}
