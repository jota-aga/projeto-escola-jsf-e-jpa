package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class AddMessageUtil {
	
	public static void adicionarMensagem(String tituloMensagem, String keyMensagem, FacesMessage.Severity severity, String idComponente) {
		String mensagem = MensagemBundle.mensagem(keyMensagem);
		FacesContext.getCurrentInstance().addMessage(idComponente, new FacesMessage(severity, tituloMensagem, mensagem));
	}
	
	public static void adicionarMensagemEAtualizar(String tituloMensagem, String keyMensagem, FacesMessage.Severity severity, String idComponente, 
			String idComponenteASerAtualizado) {
		String mensagem = MensagemBundle.mensagem(keyMensagem);
		FacesContext.getCurrentInstance().addMessage(idComponente, new FacesMessage(severity, tituloMensagem, mensagem));
		
		RequestContext.getCurrentInstance().update(idComponenteASerAtualizado);
	}
	
	public static void adicionarMensagemEFalha(String tituloMensagem, String keyMensagem, FacesMessage.Severity severity, String idComponente) {
		String mensagem = MensagemBundle.mensagem(keyMensagem);
		FacesContext.getCurrentInstance().addMessage(idComponente, new FacesMessage(severity, tituloMensagem, mensagem));
		
		FacesContext.getCurrentInstance().validationFailed();
	}
}
