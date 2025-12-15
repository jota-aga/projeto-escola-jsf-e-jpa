package modelo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestePersistencia {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("escola");
		EntityManager em = emf.createEntityManager();
		
		
		em.getTransaction().begin();
		
		Curso c1 = new Curso();
		c1.setNome("BCC");
		
		Curso c2 = new Curso();
		c2.setNome("Letras");
		
		/*
		List<Curso> cursos = Arrays.asList(c1, c2);
		
		
		Aluno a1 = new Aluno();
		a1.setNome("João");
		a1.setMatricula("123456789");
		a1.setDataNascimento(new Date(9, 10, 2004));
		a1.setCursos(cursos);
		
		Aluno a2 = new Aluno();
		a2.setNome("Maria");
		a2.setMatricula("123456789");
		a2.setDataNascimento(new Date(9, 10, 2004));
		a2.setCursos(Arrays.asList(c1));
		
		em.persist(c1);
		em.persist(c2);
		em.persist(a1);
		em.persist(a2);
		*/
		c1.setId(1);
		c2.setId(2);
		/*
		Professor p1 = new Professor();
		p1.setNome("Cesar");
		p1.setFormacao("Bacharelado em Ciência da Computação");
		p1.setCpf("12345678978");
		p1.setCurso(c1);
		*/
		
		Professor p2 = new Professor();
		p2.setNome("Vanessa");
		p2.setFormacao("Graduada em Letras");
		p2.setCpf("12785678978");
		p2.setCurso(c2);
		
		em.persist(p2);
		
		em.getTransaction().commit();
		
		List<Professor> professores = em.createQuery("from Professor", Professor.class).getResultList();
		
		for(Professor p : professores) {
			System.out.println(p);
		}
		em.close();
		
	
		
		
		
		em.close();
		
	}
}
