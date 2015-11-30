package unipe.br.ui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import unipe.br.contas.Conta;

public class ContaModel extends AbstractTableModel{

    private static final int COL_NUMERO = 0;
    private static final int COL_NOME = 1;
    private static final int COL_SALDO = 2;

    
    private List<Conta> valores;
    
	public ContaModel(List<Conta> contas) {
		valores = contas;
	}
	public ContaModel() {

	}

	public String getColumnName(int column) {  
        		   
	    if (column == COL_NUMERO) return "Numero";
	    if (column == COL_NOME) return "Nome";
	    if (column == COL_SALDO) return "Saldo";	   
	    
	    return "";   
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return valores.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Conta mod = valores.get(row);  
        if (column == COL_NUMERO) return mod.getConta();
        if (column == COL_SALDO) return String.format("%.2f", mod.getSaldo());
        if (column == COL_NOME) return mod.getNome();
        
        return "";  
	}
	public Conta getContaAt(int row){
		return valores.get(row);
	}
	public void removeRow(int row){
		valores.remove(row);
		this.fireTableRowsDeleted(row, row);
	}
}
