package bean;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import modelo.Curso;
import modelo.Nota;
import repositorio.CursoDAO;
import repositorio.NotaDAO;
import servico.CalcularMedia;
import servico.DoubleArray;

@Named
@ViewScoped
public class ChartBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private CursoDAO cursoDAO;
	
	@Inject
	private NotaDAO notaDAO;
	
	private Curso cursoSelecionado;
	
	private List<Curso> todosCursos;
	
	private Double media = 0.0;
	
	public void procuraTodosCursos(){
		todosCursos = cursoDAO.procurarTodos();
	}
	
	public Curso pegarPorPosicao(int posicao) {
		return todosCursos.get(posicao);
	}
	
	public void consumirServico() throws MalformedURLException {
		URL url = new URL("http://localhost:8085/servico?wsdl");
		
		QName qName = new QName("http://servico/", "CalcularMediaImplService");
		
		Service service = Service.create(url, qName);
		
		List<Nota> notasPorCurso = notaDAO.procurarPorCurso(this.cursoSelecionado.getId());
		
		if(notasPorCurso.isEmpty()) {
			media = 0.0;
		}
		else {
			DoubleArray notas = new DoubleArray();
			
			for(Nota n : notasPorCurso) {
				notas.getItem().add(n.getNota());
			}
			
			CalcularMedia calculadorDeMedia = service.getPort(CalcularMedia.class);
			
			media = calculadorDeMedia.calculaMedia(notas);
		}
		
	}
	
	public CursoConverter getCursoConverter() {
		return new CursoConverter(cursoDAO.procurarTodos());
	}

	public List<Curso> getTodosCursos() {
		return todosCursos;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}

	public void setCursoSelecionado(Curso cursoSelcionado) {
		this.cursoSelecionado = cursoSelcionado;
	}
}
