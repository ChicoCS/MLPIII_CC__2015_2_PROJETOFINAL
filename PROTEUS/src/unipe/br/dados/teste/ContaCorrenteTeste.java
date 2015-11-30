package unipe.br.dados.teste;

import unipe.br.contas.Conta;
import unipe.br.contas.ContaCorrente;
import junit.framework.TestCase;

public class ContaCorrenteTeste extends TestCase {
	Conta conta;
	
	protected void setUp(){
		conta = new ContaCorrente();
		conta.setId(1);
	}
	
	protected void tearDown(){
		conta = null;
	}
	
	public void testCreditar(){
		conta.creditar(100.1);
		assertEquals(conta.getSaldo(), 100.1-0.1);
	}
	
	public void testDebitar(){
		conta.creditar(100.1f);
		assertTrue(conta.debitar(20));
	}
	
	public void testConta(){
		Conta conta2 = new ContaCorrente();
		conta2.setId(1);
		assertNotSame(this.conta, conta2);
		assertEquals(conta, conta2);
	}	
}