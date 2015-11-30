package unipe.br.contas;

import java.io.File;
import java.util.List;

import unipe.br.dados.IRepositorioContas;
import unipe.br.dados.Relatorio;
import unipe.br.dados.exceptions.ContaJaCadastradaException;
import unipe.br.dados.exceptions.ContaNaoEncontradaException;
import unipe.br.dados.exceptions.RelatorioNaoCriadoException;

public class ContaBancaria {	

	IRepositorioContas persitencia;
		
	public ContaBancaria(IRepositorioContas persitencia) {
			this.persitencia = persitencia;
		}
		
	public void cadastrarConta(Conta conta) throws ContaJaCadastradaException {		
		if(!persitencia.existe(conta)){
			persitencia.inserir(conta);
		}else{
			throw new ContaJaCadastradaException("Conta já existe!");
		}
	}
		
	public Conta procurarConta(Conta conta) throws ContaNaoEncontradaException{	
		if(persitencia.existe(conta)){
			return persitencia.procura(conta);
		}else{
			throw new ContaNaoEncontradaException("Conta não encontrada!");
		}
	}
		
	public void atualizarConta(Conta conta) throws ContaNaoEncontradaException {		
		if(persitencia.existe(conta)){
			persitencia.atualizar(conta);
		}else{
			throw new ContaNaoEncontradaException("Conta não encontrada!");
		}
	}
		
	public void removerConta(Conta conta) throws ContaNaoEncontradaException {
		if(persitencia.existe(conta)){
			persitencia.remover(conta);
		}else{
			throw new ContaNaoEncontradaException("Conta não encontrada!");
		}
	}

	public List<Conta> procurarContas(String nome) {
		return persitencia.listar(nome);
	}

	public File gerar(Conta conta, File file) throws RelatorioNaoCriadoException {
		Relatorio relatorio = new Relatorio(conta, file);
		relatorio.enviar();
		return relatorio.getFile();
	}
}
