package unipe.br.ui;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JTable;
import javax.swing.SwingWorker;

import unipe.br.contas.Conta;

public class ContaList extends SwingWorker<List<Conta>, Void> {

	private final JTable tabela;
	
	public ContaList(JTable tabela) {
		this.tabela = tabela;
	}

	@Override
	protected List<Conta> doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void done() {
	    ContaModel model;
		try {
			model = new ContaModel(get());
			tabela.setModel(model);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	  }
}
