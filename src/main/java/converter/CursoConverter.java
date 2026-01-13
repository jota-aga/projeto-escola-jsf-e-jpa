package converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import modelo.Curso;

public class CursoConverter implements Converter{
	
	private List<Curso> cursos;

	public CursoConverter(List<Curso> cursos) {
		super();
		this.cursos = cursos;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		
		Integer cursoId = Integer.valueOf(value);
		
		for(Curso c : cursos) {
			if(c.getId().equals(cursoId)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return null;
		}
		
		Curso curso = (Curso) value;
		
		for(Curso c : cursos) {
			if(c.equals(curso)) {
				return String.valueOf(curso.getId());
			}
		}
		
		return null;
	}

}
