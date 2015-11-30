package unipe.br.contas;

public class ContaCorrente extends Conta{
		
	public ContaCorrente() {
		super();
	}
		
	public ContaCorrente(int id, String numero, String nome, double saldo) {
		super(id, numero, nome, saldo);
	}
		
	public ContaCorrente(String numero, String nome, double saldo) {
		super(numero, nome, saldo);
	}
		
	public ContaCorrente(String numero, String nome) {
		super(numero, nome);
	}

	public void creditar(double valor){
		if(valor>0){
			this.saldo += (valor-0.1);
		}
	}
}
