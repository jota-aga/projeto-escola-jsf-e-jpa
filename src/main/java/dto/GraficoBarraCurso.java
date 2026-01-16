package dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import modelo.Curso;
import modelo.Professor;

public class GraficoBarraCurso {
	private Map<Curso, Integer> quantidadeDeProfessorPorCurso;
	
	public String getJsonDoGraficoDeProfessorPorCurso() {
		StringBuilder json = new StringBuilder();
		
		json.append("[");
		json.append("['Curso', 'Qtd Professores', { role: 'style' }]");
		
		for(Entry<Curso, Integer> entry : quantidadeDeProfessorPorCurso.entrySet()) {
			String nome = entry.getKey() == null? "Sem Curso" : entry.getKey().getNome();
			String cor = entry.getKey() == null? "000000" : entry.getKey().getCor();
			json.append(String.format(",['%s', %d, '#%s']", nome, entry.getValue(), cor));
		}
		
		
		
		json.append("]");
		System.out.println(json.toString());
		return json.toString();
	}
	
	public void preencherDadosDoGraficoDeProfessorPorCurso(List<Professor> professores) {
		quantidadeDeProfessorPorCurso = new HashMap<>();
		
		professores.forEach(professor ->
							{
								quantidadeDeProfessorPorCurso.merge(professor.getCurso(), 1, Integer::sum);
							});
	}
}
