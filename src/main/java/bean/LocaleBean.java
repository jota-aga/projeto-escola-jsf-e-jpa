package bean;

import java.io.Serializable;


import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LocaleBean implements Serializable{
	
	private Locale currentLocale = Locale.US;
	
	public void mudarLocaleParaEN() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		
		currentLocale = Locale.US;
		
		viewRoot.setLocale(currentLocale);
	}
	
	public void mudarLocaleParaPT() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		
		currentLocale = new Locale("pt", "BR");
		
		viewRoot.setLocale(currentLocale);
}

	public Locale getCurrentLocale() {
		return currentLocale;
	}
}
