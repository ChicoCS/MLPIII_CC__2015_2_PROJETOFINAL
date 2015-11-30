package unipe.br.contas;


public abstract class Conta {
	protected int id;
	protected String conta;
	protected String nome;
	protected double saldo;
	
	public Conta(){
	}
	
	public Conta(int id, String numero, String nome, double saldo){
		this.conta = numero;
		this.nome = nome;
		this.id = id;
		this.saldo = saldo;
	}
	
	public Conta(String numero, String nome, double saldo){
		this(0, numero, nome, saldo);		
	}
	
	public Conta(String numero, String nome){
		this(0, numero, nome, 0);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getConta() {
		return conta;
	}
	
	public void setConta(String nConta) {
		this.conta = nConta;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double valor) {
		this.saldo = valor;		
	}
	
	public boolean debitar(float valor) {
		if(valor < saldo){
			this.saldo -= valor;
			return true;
		}
		return false;
	}
	
	public void creditar(double valor) {
		this.saldo += valor;
	}
	
	@Override
	public boolean equals(Object obj) {
		Conta other = (Conta) obj;
		if (id != other.id)
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return "Conta [id=" + id + ", conta=" + conta + ", nome=" + nome
				+ ", saldo=" + saldo + "]";
	}
}