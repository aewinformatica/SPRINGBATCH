package com.example.demo.model;

public class Pessoa {

	private String nome;
	private String sobreNome;
	
	
	//Tem que ser Criado o Construtor padrao para evitar erros de FlatFileParseException
	public Pessoa() {
	}
	
	

	public Pessoa(String nome, String sobreNome) {
		this.nome = nome;
		this.sobreNome = sobreNome;
	}



	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSobreNome() {
		return sobreNome;
	}


	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}



	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", sobreNome=" + sobreNome + "]";
	}

	
	
}
