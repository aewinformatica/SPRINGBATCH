package com.example.demo.itemprocessors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.demo.model.Pessoa;

//PRIMEIRO PARAMETRO O QUE ENTRA O SEGUNDO O QUE SAI

public class PessoaItemProcessor implements ItemProcessor<Pessoa, Pessoa> {

	private static final Logger log = LoggerFactory.getLogger(PessoaItemProcessor.class);
	
	


	@Override
	public Pessoa process(Pessoa pessoa) throws Exception {
		
		
		final String  nome = pessoa.getNome().toUpperCase();
		final String  sobreNome = pessoa.getSobreNome().toUpperCase();
		
		
		final Pessoa pessoaTransformada = new Pessoa(nome,sobreNome);
		
		 log.info("Convertendo (" + pessoa + ") para (" + pessoaTransformada + ")");
		 
		return pessoaTransformada;
	}}
