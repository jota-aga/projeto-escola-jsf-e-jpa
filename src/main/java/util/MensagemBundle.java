package util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class MensagemBundle {
	
	public static String mensagem(String keyword) {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("keywords.keywords", locale);
		
		String mensagem = bundle.getString(keyword);
		
		return mensagem;
	}
}
