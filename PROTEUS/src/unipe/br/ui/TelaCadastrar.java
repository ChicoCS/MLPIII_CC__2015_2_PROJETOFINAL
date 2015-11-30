package unipe.br.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import unipe.br.contas.ContaBancaria;
import unipe.br.contas.ContaCorrente;
import unipe.br.dados.RepositorioContas;
import unipe.br.dados.exceptions.ContaJaCadastradaException;
import unipe.br.facade.Banco;

public class TelaCadastrar {
	private JDialog fDialog;
	private JPanel jPanelCadastro;
	private JTextField jFieldNumero;
	private JTextField jFieldNome;
	private JTextField jFieldSaldo;
	private JPanel jPanelButton;
	
	public TelaCadastrar(JFrame pai){
		fDialog = new JDialog(pai, "Cadastrar Conta",true);
		fDialog.setResizable(false);
		fDialog.setSize(300, 160);
		fDialog.setLocationRelativeTo(pai);
		
		preparaPanelCadastro();
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.weightx = 1;
		gridBag.weighty = 0.1;
		
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.NONE;
		gridBag.anchor = GridBagConstraints.LINE_END;
		gridBag.insets = new Insets(20, 10, 0, 3);
		preparaLabel("Numero:", gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.anchor = GridBagConstraints.LINE_START;
		preparaFieldNumero(gridBag);
		
		gridBag.weightx = 1;
		gridBag.weighty = 0.1;
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.LINE_END;
		gridBag.insets = new Insets(3, 10, 0, 3);
		preparaLabel("Nome:", gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.LINE_START;
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
		
		
		preparaPanelButton();
		preparaButtonSalvar();
		preparaButtonCancelar();
		
	}
	
	private void limparAtributos(){
		jFieldNumero.setText("");
		jFieldNome.setText("");
		jFieldSaldo.setText("");
	}
	
	public void MostarTela(){	
		fDialog.setVisible(true);
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
				Banco banco = new Banco(new ContaBancaria(new RepositorioContas()));
				String nConta = jFieldNumero.getText().trim();
				String nome = jFieldNome.getText().trim();
				String stringSaldo = jFieldSaldo.getText().trim();
				
				double saldo = 0;
				if(!nConta.equals("") && !nome.equals("")){
					
					try {
						if(!stringSaldo.equals(""))
							saldo = Double.parseDouble(stringSaldo.replace(',', '.'));
						banco.cadastrarConta(new ContaCorrente(nConta, nome, saldo));
						JOptionPane.showMessageDialog(fDialog, "Conta Cadastrada Com Sucesso!!");
						limparAtributos();
					} catch (ContaJaCadastradaException e1) {
						JOptionPane.showMessageDialog(fDialog, e1.getMessage());
					} catch (NumberFormatException	e1){
						JOptionPane.showMessageDialog(fDialog, "Valor do saldo invalido!");
					}
				}else{
					JOptionPane.showMessageDialog(fDialog, "Preencha o formulario!");
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
