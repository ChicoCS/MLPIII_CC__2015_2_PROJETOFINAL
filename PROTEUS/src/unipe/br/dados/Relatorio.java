package unipe.br.dados;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import unipe.br.contas.Conta;
import unipe.br.dados.exceptions.RelatorioNaoCriadoException;

public class Relatorio implements IRelatorio{
	Conta conta;
	File file;
	
	public Relatorio(Conta conta, File file){
		this.conta = conta;
		this.file = file;
	}
	
	public void enviar() throws RelatorioNaoCriadoException{
		BufferedWriter bw = null;
		Date data = new Date();
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String caminho = String.format("%s\\C%s_%s.txt", file.getAbsolutePath(), conta.getConta(), dt1.format(data));
		
		File arquivo = new File(caminho);
		file = arquivo;
		try( FileWriter fw = new FileWriter(arquivo) ){
			bw = new BufferedWriter(fw); 
			bw.write("Data: "+dt1.format(data));
			bw.newLine();
			bw.newLine();
			bw.write(conta.toString());
			bw.flush();
		}catch(IOException ex){
			throw new RelatorioNaoCriadoException("Ouve um erro na criação do arquivo!");
		}finally{
			if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public File getFile(){
		return file;
	}
}