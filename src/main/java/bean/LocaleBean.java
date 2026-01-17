package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import converter.LocaleConverter;

@Named
@SessionScoped
public class LocaleBean implements Serializable{
	private Locale locale;
	private List<Locale> localesDisponiveis;
	
	@PostConstruct
	public void init() {
		locale = new Locale("PT", "BR");
		
		localesDisponiveis = new ArrayList<>();
		
		localesDisponiveis.add(new Locale("PT", "BR"));
		localesDisponiveis.add(Locale.US);
	}
	
	public void setLocaleDaPagina() {
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
		
		view.setLocale(locale);
		
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List<Locale> getLocalesDisponiveis() {
		return localesDisponiveis;
	}
	
	public LocaleConverter getLocaleConverter() {
		return new LocaleConverter(localesDisponiveis);
	}
}
