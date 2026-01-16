package dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import modelo.Aluno;
import modelo.Curso;
import modelo.Professor;

public class GraficoPizzaCurso {
	
	private Map<Curso, Integer> quantidadeDeAlunoPorCurso;
	private String jsonParaCoresDosCursos;
	
	
	
	public String getJsonDoGraficoDeAlunoPorCurso() {
		StringBuilder json = new StringBuilder();
		StringBuilder stringBuilderParaCoresDosCursos = new StringBuilder();
		
		json.append("[");
		json.append("['Curso', 'Qtd Alunos']");
		
		stringBuilderParaCoresDosCursos.append('[');
		
		for(Entry<Curso, Integer> entry : quantidadeDeAlunoPorCurso.entrySet()) {
			json.append(String.format(",['%s', %d]", entry.getKey().getNome(), entry.getValue()));
			
			stringBuilderParaCoresDosCursos.append("'#"+entry.getKey().getCor()+"',");
		}
		
		int indexDaUltimaVirgula = stringBuilderParaCoresDosCursos.lastIndexOf(",");
		stringBuilderParaCoresDosCursos.deleteCharAt(indexDaUltimaVirgula);
		stringBuilderParaCoresDosCursos.append(']');
		this.jsonParaCoresDosCursos = stringBuilderParaCoresDosCursos.toString();
		
		
		json.append("]");
		System.out.println(json.toString());
		return json.toString();
	}
	
	public void preencherDadosDoGraficoDeAlunoPorCurso(List<Aluno> alunos) {
		quantidadeDeAlunoPorCurso = new HashMap<>();
		
		for(Aluno a : alunos) {
			if(a.getCursos() != null) {
				a.getCursos().forEach(curso -> {
									  quantidadeDeAlunoPorCurso.merge(curso, 1, Integer::sum);
				});
			}
		}
	}

	public String getJsonParaCoresDosCursos() {
		return jsonParaCoresDosCursos;
	}
	
	
}
