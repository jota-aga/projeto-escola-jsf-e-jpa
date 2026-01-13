package service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import modelo.Aluno;
import modelo.Curso;
import modelo.Professor;
import modelo.TipoDeUsuario;
import modelo.Usuario;
import repositorio.AlunoDAO;
import repositorio.ProfessorDAO;
import repositorio.UsuarioDAO;
import util.AddMessageUtil;
import util.Transacional;

public class AuthService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ProfessorDAO professorDAO;
	
	@Inject
	private AlunoDAO alunoDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	public TipoDeUsuario autenticarUsuario(String cpf, String senha) {
		Usuario usuario = usuarioDAO.procurarPorCpf(cpf);
		
		if(usuario == null || !usuario.getSenha().equals(senha)) {
			AddMessageUtil.adicionarMensagem(null, "loginIncorreto", FacesMessage.SEVERITY_ERROR, "messages");
		}
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		if(usuario.getTipoDeUsuario() == TipoDeUsuario.ALUNO) {
			Aluno aluno = alunoDAO.procurarPorUsuario(usuario);
			
			session.setAttribute("usuario", aluno);
			return TipoDeUsuario.ALUNO;
		}
		else {
			Professor professor = professorDAO.procurarPorUsuario(usuario);
			
			session.setAttribute("usuario", professor);
			return TipoDeUsuario.PROFESSOR;
		}
	}
	
	@Transacional
	public boolean salvarRegistro(Usuario usuario, Aluno aluno, Professor professor, List<Curso> cursoSelecionados, Curso cursoSelecionado) {
		
		if(isSenhasDiferentes(usuario)) {
			AddMessageUtil.adicionarMensagemEFalha(null, "senhasDiferentes", FacesMessage.SEVERITY_ERROR, "mainForm:confirmarSenha");
			AddMessageUtil.adicionarMensagemEFalha(null, "senhasDiferentes", FacesMessage.SEVERITY_ERROR, "mainForm:senha");
			return false;
		}
		if(isCpfRepetido(usuario)) {
			AddMessageUtil.adicionarMensagemEFalha(null, "cpfJaCadastrado", FacesMessage.SEVERITY_ERROR, "mainForm:cpf");
			return false;
		}
		
		
		if(usuario.getTipoDeUsuario() == TipoDeUsuario.ALUNO) {
			if(isMatriculaRepetida(aluno)) {
				AddMessageUtil.adicionarMensagemEFalha(null, "matriculaJaExiste", FacesMessage.SEVERITY_ERROR, "mainForm:matricula");
				return false;
			}
			
			aluno.setUsuario(usuario);
			aluno.setCursos(new HashSet<>(cursoSelecionados));
			
			alunoDAO.salvar(aluno);
		}
		else {
			professor.setUsuario(usuario);
			professor.setCurso(cursoSelecionado);
			professorDAO.salvar(professor);
		}
		
		return true;
	}
	
	private boolean isMatriculaRepetida(Aluno aluno) {
		Aluno alunoRepetido = alunoDAO.procurarPorMatricula(aluno.getMatricula());
		
		if(alunoRepetido != null) {
			return true;
		}
		
		return false;
	}
	
	private boolean isCpfRepetido(Usuario usuario) {
		Usuario usuarioRepetido = usuarioDAO.procurarPorCpf(usuario.getCpf());
		
		if(usuarioRepetido != null) {
			return true;
		}
		
		return false;
	}
	
	private boolean isSenhasDiferentes(Usuario usuario) {
		if(!usuario.getSenha().equals(usuario.getConfirmarSenha())) {
			return true;
		}
		return false;
	}
}
