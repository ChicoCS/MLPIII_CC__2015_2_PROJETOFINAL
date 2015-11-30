package unipe.br.dados;

import java.util.List;

import unipe.br.contas.Conta;

public interface IRepositorioContas {	
	void inserir(Conta conta);
	Conta procura(Conta conta);
	void atualizar(Conta conta);
	void remover(Conta conta);
	List<Conta> listar(String nome);
    boolean existe(Conta conta);
}
