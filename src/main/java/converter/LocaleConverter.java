package converter;

import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class LocaleConverter implements Converter{
	
	private List<Locale> locales;
	
	public LocaleConverter(List<Locale> locales) {
		this.locales = locales;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		
		for(Locale locale : locales) {
			if(locale.toString().equals(value)) {
				return locale;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return null;
		}
		
		Locale locale = (Locale) value;
		
		return locale.toString();
	}

}
