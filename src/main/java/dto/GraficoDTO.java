package dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import modelo.Aluno;
import modelo.Curso;
import modelo.Professor;

public class GraficoDTO {
	private Map<Curso, Integer> quantidadeDeProfessorPorCurso;
	private Map<Curso, Integer> quantidadeDeAlunoPorCurso;
	
	public String getJsonDoGraficoDeProfessorPorCurso() {
		StringBuilder json = new StringBuilder();
		json.append("[");
		json.append("['Curso', 'Qtd Professores', { role: 'style' }],");
		
		for(Entry<Curso, Integer> entry : quantidadeDeProfessorPorCurso.entrySet()) {
			String nome = entry.getKey() == null? "Sem Curso" : entry.getKey().getNome();
			String cor = entry.getKey() == null? "000000" : entry.getKey().getCor();
			json.append(String.format("['%s', %d, '#%s'],", nome, entry.getValue(), cor));
		}
		
		int indexUltimaVirgula = json.lastIndexOf(",");
		json.deleteCharAt(indexUltimaVirgula);
		
		json.append("]");
		System.out.println(json.toString());
		return json.toString();
	}
	
	public String getJsonDoGraficoDeAlunoPorCurso() {
		StringBuilder json = new StringBuilder();
		json.append("[");
		json.append("['Curso', 'Qtd Alunos', { role: 'style' }],");
		
		for(Entry<Curso, Integer> entry : quantidadeDeAlunoPorCurso.entrySet()) {
			json.append(String.format("['%s', %d, '#%s'],", entry.getKey().getNome(), entry.getValue(), entry.getKey().getCor()));
		}
		
		int indexUltimaVirgula = json.lastIndexOf(",");
		json.deleteCharAt(indexUltimaVirgula);
		
		json.append("]");
		System.out.println(json.toString());
		return json.toString();
	}
	
	private void preencherDadosDoGraficoDeProfessorPorCurso(List<Professor> professores) {
		quantidadeDeProfessorPorCurso = new HashMap<>();
		
		professores.forEach(professor ->
							{
								quantidadeDeProfessorPorCurso.merge(professor.getCurso(), 1, Integer::sum);
							});
	}
	
	private void preencherDadosDoGraficoDeAlunoPorCurso(List<Aluno> alunos) {
		quantidadeDeAlunoPorCurso = new HashMap<>();
		
		for(Aluno a : alunos) {
			if(a.getCursos() != null) {
				a.getCursos().forEach(curso -> {
									  quantidadeDeAlunoPorCurso.merge(curso, 1, Integer::sum);
				});
			}
		}
	}

	public void popularGraficos(List<Professor> professores, List<Aluno> alunos) {
		preencherDadosDoGraficoDeAlunoPorCurso(alunos);
		preencherDadosDoGraficoDeProfessorPorCurso(professores);
		
	}
}
