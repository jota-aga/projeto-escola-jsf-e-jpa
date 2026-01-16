package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dto.GraficoBarraCurso;
import dto.GraficoPizzaCurso;
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
	
	private GraficoPizzaCurso graficoPizzaCursoDTO;
	private GraficoBarraCurso graficoBarraCursoDTO;
	
	@PostConstruct
	public void init() {
		List<Professor> professores = professorDAO.procurarTodos();
		
		List<Aluno> alunos = alunoDAO.procurarTodos();
		
		graficoPizzaCursoDTO = new GraficoPizzaCurso();
		graficoBarraCursoDTO = new GraficoBarraCurso();
		
		preencherGraficos(professores, alunos);
	}
	
	public void preencherGraficos(List<Professor> professores, List<Aluno> alunos) {
		graficoBarraCursoDTO.preencherDadosDoGraficoDeProfessorPorCurso(professores);
		graficoPizzaCursoDTO.preencherDadosDoGraficoDeAlunoPorCurso(alunos);
	}

	public GraficoPizzaCurso getGraficoPizzaCursoDTO() {
		return graficoPizzaCursoDTO;
	}

	public GraficoBarraCurso getGraficoBarraCursoDTO() {
		return graficoBarraCursoDTO;
	}
}
