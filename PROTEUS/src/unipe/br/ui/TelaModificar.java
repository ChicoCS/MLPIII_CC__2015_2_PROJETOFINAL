package unipe.br.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import unipe.br.contas.Conta;
import unipe.br.contas.ContaBancaria;
import unipe.br.contas.ContaCorrente;
import unipe.br.dados.RepositorioContas;
import unipe.br.dados.exceptions.ContaNaoEncontradaException;
import unipe.br.facade.Banco;

public class TelaModificar {
	private JDialog fDialog;
	private JPanel jPanelCadastro;
	private JTextField jFieldBuscaNumero;
	private JTextField jFieldNumero;
	private JTextField jFieldNome;
	private JTextField jFieldSaldo;
	private JPanel jPanelButton;
	private JPanel jPanelBusca;
	private Banco banco;
	private int id;
	
	private void setAtributos(Conta conta){
		this.id = conta.getId();
		jFieldBuscaNumero.setText(conta.getConta());
		jFieldNumero.setText(conta.getConta());
		jFieldNome.setText(conta.getNome());
		jFieldSaldo.setText(Double.toString(conta.getSaldo()));
	}
	
	private void limparAtributos(){
		this.id = -1;
		jFieldBuscaNumero.setText("");
		jFieldNumero.setText("");
		jFieldNome.setText("");
		jFieldSaldo.setText("");
	}
	
	public TelaModificar(JFrame pai){
		preparaTela(pai);
		preparaPainelBusca();
		preparaLabelNormal("Conta:");
		preparaTextFieldBusca();
		preparaBotaoBusca();		
		preparaPainelCadastro();		
		preparaPanelButton();
		preparaButtonSalvar();
		preparaButtonCancelar();
		
		banco = new Banco(new ContaBancaria(new RepositorioContas()));
	}
	
	public TelaModificar(JFrame pai, Conta conta){
		this(pai);
		setAtributos(conta);
	}
	
	public void mostarTela(){	
		fDialog.pack();
		fDialog.setVisible(true);
	}
	
	private void preparaTela(JFrame pai){
		fDialog = new JDialog(pai, "Modificar Conta",true);
		fDialog.setResizable(false);
		fDialog.setSize(310, 210);
		fDialog.setLocationRelativeTo(pai);
	}
	
	private void preparaPainelBusca(){
		jPanelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanelBusca.setBorder(BorderFactory.createTitledBorder("Buscar Conta"));
		fDialog.add(jPanelBusca, BorderLayout.NORTH);
	}
	
	private void preparaPainelCadastro(){
		preparaPanelCadastro();
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.weightx = 1;
		gridBag.weighty = 0.1;
		
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.NONE;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_END;
		gridBag.insets = new Insets(20, 10, 0, 3);
		preparaLabel("Numero:", gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_START;
		preparaFieldNumero(gridBag);
		
		gridBag.weightx = 1;
		gridBag.weighty = 0.1;
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_END;
		gridBag.insets = new Insets(3, 10, 0, 3);
		preparaLabel("Nome:", gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_START;
		preparaFieldNome(gridBag);
		
		gridBag.weightx = 1;
		gridBag.weighty = 2;
		gridBag.gridx = 0;
		gridBag.gridy = 2;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_END;
		gridBag.insets = new Insets(3, 10, 0, 3);
		preparaLabel("Saldo:", gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 2;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_START;
		preparaFieldSaldo(gridBag);
	}
	
	
	
	private void preparaTextFieldBusca(){
		jFieldBuscaNumero = new JTextField(10);
		jPanelBusca.add(jFieldBuscaNumero);
	}
	
	private void preparaBotaoBusca(){
		JButton jBBusca = new JButton("Buscar");
		jBBusca.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Conta conta = new ContaCorrente();
				conta.setConta(jFieldBuscaNumero.getText().trim());
				try {
					setAtributos(banco.procurarConta(conta));
				} catch (ContaNaoEncontradaException e1) {
					JOptionPane.showMessageDialog(fDialog, e1.getMessage());
					limparAtributos();
				}
			}
		});
		jPanelBusca.add(jBBusca);
	}
	private void preparaPanelCadastro(){
		jPanelCadastro = new JPanel();
		jPanelCadastro.setLayout(new GridBagLayout());
		fDialog.add(jPanelCadastro, BorderLayout.WEST);
	}
	private void preparaFieldNumero(GridBagConstraints gc){
		jFieldNumero = new JTextField(6);	
		jFieldNumero.setDocument(new CharLimit(10));
		jPanelCadastro.add(jFieldNumero, gc);
	}
	
	private void preparaFieldNome(GridBagConstraints gc){
		jFieldNome = new JTextField(15);
		jPanelCadastro.add(jFieldNome, gc);
	}
	
	private void preparaFieldSaldo(GridBagConstraints gc){
		jFieldSaldo = new JTextField(6);
		jFieldSaldo.setDocument(new DoubleLimit());
		jPanelCadastro.add(jFieldSaldo, gc);
	}
	
	private void preparaLabel(String texto, GridBagConstraints gc){
		JLabel label = new JLabel(texto);
		jPanelCadastro.add(label, gc);
	}
	public void preparaLabelNormal(String texto){
		JLabel label = new JLabel(texto);
		jPanelBusca.add(label);
	}
	private void preparaPanelButton(){
		jPanelButton = new JPanel();
		jPanelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		fDialog.add(jPanelButton, BorderLayout.SOUTH);
	}
	
	private void preparaButtonSalvar(){
		JButton jBSalvar = new JButton("Salvar");
		jPanelButton.add(jBSalvar);
		jBSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int opcao = JOptionPane.showConfirmDialog(fDialog, "Tem certeza que deseja editar essa conta?", "Atenção!", 
														JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(opcao == 0){
					String nConta = jFieldNumero.getText().trim();
					String nome = jFieldNome.getText().trim();
					String stringSaldo = jFieldSaldo.getText().trim();
					
					double saldo = 0;
					if(!nConta.equals("") && !nome.equals("") && !jFieldNumero.getText().trim().equals("")){					
						try {
							if(!stringSaldo.equals(""))
								saldo = Double.parseDouble(stringSaldo.replace(',', '.'));
							banco.atualizarConta(new ContaCorrente(id, nConta, nome, saldo));
							JOptionPane.showMessageDialog(fDialog, "Conta atualizada com sucesso!");
							
						} catch (ContaNaoEncontradaException e1) {
							JOptionPane.showMessageDialog(fDialog, e1.getMessage());
							limparAtributos();
						}  catch (NumberFormatException	e1){
							JOptionPane.showMessageDialog(fDialog, "Valor do saldo invalido!");
						}	
					}else{
						JOptionPane.showMessageDialog(fDialog, "Proibido valores vazios!");
					}					
				}				
			}
		});
	}
	
	private void preparaButtonCancelar(){
		JButton jBCancelar = new JButton("Cancelar");
		jPanelButton.add(jBCancelar);
		jBCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fDialog.dispose();				
			}
		});
	}
}