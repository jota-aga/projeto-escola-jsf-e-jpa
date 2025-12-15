package bean;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.Curso;

@FacesConverter(forClass = Curso.class)
public class CursoConverter implements Converter{
	
	private List<Curso> cursos;
	
	public CursoConverter(List<Curso> cursos) {
		this.cursos = cursos;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		
		Integer id = Integer.valueOf(value);
		
		for(Curso c : cursos) {
			if(c.getId() == id) {
				return c;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Curso curso = (Curso) value;
			
			return String.valueOf(curso.getId());
		}
		
		return null;
	}

}
