package unipe.br.facade;

import java.io.File;
import java.util.List;

import unipe.br.contas.Conta;
import unipe.br.contas.ContaBancaria;
import unipe.br.dados.exceptions.ContaJaCadastradaException;
import unipe.br.dados.exceptions.ContaNaoEncontradaException;
import unipe.br.dados.exceptions.RelatorioNaoCriadoException;

public class Banco {
	
	private ContaBancaria contaBancaria;
	
	public Banco(ContaBancaria bancaria) {
		this.contaBancaria = bancaria;
	}
	
	public void cadastrarConta(Conta conta) throws ContaJaCadastradaException {
		contaBancaria.cadastrarConta(conta);
	}
	
	public Conta procurarConta(Conta conta) throws ContaNaoEncontradaException{
		return contaBancaria.procurarConta(conta);
	}
	
	public void atualizarConta(Conta conta) throws ContaNaoEncontradaException{
		contaBancaria.atualizarConta(conta);
	}
	
	public void removerConta(Conta conta) throws ContaNaoEncontradaException{
		contaBancaria.removerConta(conta);
	}

	public List<Conta> getContas(String nome) {
		return contaBancaria.procurarContas(nome);	
	}
	
	public File GerarRelatorio(Conta conta, File file) throws RelatorioNaoCriadoException {
		return contaBancaria.gerar(conta, file);	
	}
}