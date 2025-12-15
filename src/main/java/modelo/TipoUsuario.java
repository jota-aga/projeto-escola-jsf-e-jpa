package modelo;

public enum TipoUsuario {
	ALUNO("aluno"),
	PROFESSOR("professor");
	
	private String tipo;
	
	TipoUsuario(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
