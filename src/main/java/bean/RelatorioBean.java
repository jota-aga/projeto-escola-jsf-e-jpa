package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dto.GraficoDTO;
import modelo.Aluno;
import modelo.Professor;
import repositorio.AlunoDAO;
import repositorio.ProfessorDAO;

@Named
@ViewScoped
public class RelatorioBean implements Serializable{
	@Inject
	AlunoDAO alunoDAO;
	
	@Inject
	ProfessorDAO professorDAO;
	
	private GraficoDTO graficoDTO;
	
	@PostConstruct
	public void init() {
		List<Professor> professores = professorDAO.procurarTodos();
		
		List<Aluno> alunos = alunoDAO.procurarTodos();
		
		graficoDTO = new GraficoDTO();
		graficoDTO.popularGraficos(professores, alunos);
	}

	public GraficoDTO getGraficoDTO() {
		return graficoDTO;
	}
}
