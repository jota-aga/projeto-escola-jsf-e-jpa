package modelo;

public enum TipoDeUsuario {
	ALUNO("Aluno"),
	PROFESSOR("Professor"),
	ADMIN("Admin");
	
	private String tipo;
	
	TipoDeUsuario(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
