package unipe.br.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.plaf.metal.MetalFileChooserUI;

import unipe.br.contas.Conta;
import unipe.br.contas.ContaBancaria;
import unipe.br.dados.RepositorioContas;
import unipe.br.dados.exceptions.ContaNaoEncontradaException;
import unipe.br.dados.exceptions.RelatorioNaoCriadoException;
import unipe.br.facade.Banco;

public class Main {
	private JFrame jfrm;
	private JPanel jpanelBusca;
	private JTextField jTNumero;
	private JTable jtableConta;
	private ContaModel model;
	private JMenuBar jMenuBar;
	private JMenu jMenuFerramenta;
	private JPopupMenu popupMenu;
	private JMenu jMenuAjuda;
	public Banco banco;
	
	public static void main(String[] args) {
		Main principal = new Main();
		principal.montaTela();
		principal.carregarBanco();
	}

	private void montaTela() {
		preparaJanela();
		preparaMenuBar();
		preparaMenuFerramentas();
		preparaMenuItemInserir();
		preparaMenuItemModificar();
		preparaMenuAjuda();
		preparaMenuItemEquipe();
		preparaMenuItemManual();
		preparaPainelBusca();
		preparaLabel("Nome: ");
		preparaTextFieldBusca();
		preparaBotaoBusca();
		preparaBotaoRemover();
		preparaTable();
		preparaPopMenuTable();
		preparaPopMenuItemEditar();
		preparaPopMenuItemRelatorio();
		mostraJanela();		
	}
	
	private void mostraJanela() {	
		jfrm.setMinimumSize(new Dimension(465, 300));			
		jfrm.pack();
		jfrm.setSize(800, 600);
		jfrm.setLocationRelativeTo(null);
		jfrm.setVisible(true);		
	}
	
	private void preparaJanela() {
		jfrm = new JFrame("PROTEUS");
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	private void preparaMenuBar() {
		jMenuBar = new JMenuBar();
		jfrm.setJMenuBar(jMenuBar);		
	}
	
	//--------------------GUIA Menu--------------------
	
	private void preparaMenuFerramentas() {
		jMenuFerramenta = new JMenu("Menu");
		jMenuBar.add(jMenuFerramenta);
	}
	
	private void preparaMenuItemInserir() {
		JMenuItem jmenuItemInserir = new JMenuItem("Cadastrar");
		jMenuFerramenta.add(jmenuItemInserir);
		jmenuItemInserir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TelaCadastrar(jfrm).MostarTela();				
			}
		});
	}
	
	private void preparaMenuItemModificar() {
		JMenuItem jmenuItemModificar = new JMenuItem("Modificar");
		jMenuFerramenta.add(jmenuItemModificar);
		jmenuItemModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new TelaModificar(jfrm).mostarTela();				
			}
		});
	}
	
	//--------------------GUIA Ajuda--------------------
	
	private void preparaMenuAjuda() {
		jMenuAjuda = new JMenu("Ajuda");
		jMenuBar.add(jMenuAjuda);
	}
	
	private void preparaMenuItemEquipe() {
		JMenuItem jmenuItemSobre = new JMenuItem("Equipe");
		jMenuAjuda.add(jmenuItemSobre);
		jmenuItemSobre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "PROTEUS\nSistema bancário.\nVersão: 2.1\n\nDesenvolvido por:\nFrancisco de Assis Alcântara Neto\nLucas Roque Pinto\nRicardo Maximino de Sá\nWanderly da Silva";
				JOptionPane.showMessageDialog(jfrm, message, "EQUIPE", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
	}
		
	private void preparaMenuItemManual(){
		JMenuItem jmenuItemManual = new JMenuItem("Manual");
		jMenuAjuda.add(jmenuItemManual);
		jmenuItemManual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().open( new File( "src/unipe/br/src/Manual PROTEUS.pdf" ));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		});
	}
	
	//--------------------Painel Busca--------------------
	
	private void preparaPainelBusca() {
		jpanelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
		jfrm.add(jpanelBusca, BorderLayout.NORTH);	
	}
	
	private void preparaLabel(String texto){
		JLabel jLabel = new JLabel(texto);
		jpanelBusca.add(jLabel);
	}
	
	private void preparaTextFieldBusca(){
		jTNumero = new JTextField(20);
		jpanelBusca.add(jTNumero);
	}
	
	private void preparaBotaoBusca(){
		JButton jBBusca = new JButton("Buscar");
		jBBusca.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = jTNumero.getText().trim();
				buscarDadosThead(nome);				
			}
		});
		jpanelBusca.add(jBBusca);
	}
	
	private void preparaBotaoRemover(){
		JButton jBRemover = new JButton("Remover");
		jBRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowSelecionada = jtableConta.getSelectedRow();
				if(rowSelecionada >= 0){
					int opcao = JOptionPane.showConfirmDialog(jfrm, "Tem certeza que deseja remover essa conta?", "Atenção!", 
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(opcao == 0){
						Conta conta = model.getContaAt(rowSelecionada);
						try {
							banco.removerConta(conta);
							model.removeRow(rowSelecionada);
						} catch (ContaNaoEncontradaException e1) {
							JOptionPane.showMessageDialog(jfrm, "Conta não encontrada");
						}
					}
				}else{
					JOptionPane.showMessageDialog(jfrm, "Nenhuma conta selecionada!");
				}
				
			}
		});
		jpanelBusca.add(jBRemover);
	}
	
	//--------------------Tabela--------------------
	
	private void preparaTable() {
		jtableConta = new JTable();
		JScrollPane jspane = new JScrollPane(jtableConta);
		
		jtableConta.addMouseListener(new TableMouseListener(jtableConta));
		
		jfrm.add(jspane, BorderLayout.CENTER);	
	}
	
	
	private void preparaPopMenuTable(){
		popupMenu = new JPopupMenu();
		jtableConta.setComponentPopupMenu(popupMenu);
	}
	
	private void preparaPopMenuItemEditar(){
		JMenuItem menuItemEdit = new JMenuItem("Modificar");
		popupMenu.add(menuItemEdit);
		menuItemEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Conta conta = model.getContaAt(jtableConta.getSelectedRow());
				new TelaModificar(jfrm, conta).mostarTela();				
			}
		});
	}
	private JFileChooser chamarFileChooser(){
		//Preparando o FileChooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fileChooser.setAcceptAllFileFilterUsed(false);
		
		try {
			MetalFileChooserUI ui = (MetalFileChooserUI)fileChooser.getUI();
			Field field = MetalFileChooserUI.class.getDeclaredField("fileNameTextField");
			field.setAccessible(true);
			JTextField tf = (JTextField) field.get(ui);
			tf.setEditable(false);
			tf.setEnabled(false);
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return fileChooser;				
	}
	
	private void preparaPopMenuItemRelatorio(){
		JMenuItem menuItemPopRelatorio = new JMenuItem("Gerar Relatorio");
		popupMenu.add(menuItemPopRelatorio);
		menuItemPopRelatorio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = chamarFileChooser();
				int retorno = fileChooser.showSaveDialog(null);
				if(retorno == JFileChooser.APPROVE_OPTION){					
					try {
						//Salva relatorio arquivo
						final File file = banco.GerarRelatorio(model.getContaAt(jtableConta.getSelectedRow()), fileChooser.getSelectedFile());					
					} catch (RelatorioNaoCriadoException e1) {
						JOptionPane.showMessageDialog(jfrm, e1.getMessage());
					}				
				}			
			}
		});
	}
	
	public void carregarBanco(){
		banco = new Banco(new ContaBancaria(new RepositorioContas()));
		buscarDadosThead("");
	}
	
	
	//--------------------Listener--------------------
	
	public class TableMouseListener extends MouseAdapter {
	     
	    private JTable table;
	     
	    public TableMouseListener(JTable table) {
	        this.table = table;
	    }
	     
	    @Override
	    public void mousePressed(MouseEvent event) {
	        Point point = event.getPoint();
	        int currentRow = table.rowAtPoint(point);
	        table.setRowSelectionInterval(currentRow, currentRow);
	    }
	}
	
	//Utilizando thread
	private void buscarDadosThead(String conta){
		final String BuscaConta = conta;
		SwingWorker<List<Conta>, Void> worker = new SwingWorker<List<Conta>, Void>() {
				 @Override
				 protected List<Conta> doInBackground() throws Exception {

					 return banco.getContas(BuscaConta);		
				 }

				 protected void done() {
					 try {
						 model = new ContaModel(get());
						 jtableConta.setModel(model);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				 }			
		};
		worker.execute();
	}
}