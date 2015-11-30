package unipe.br.dados;

import unipe.br.dados.exceptions.RelatorioNaoCriadoException;

public interface  IRelatorio {	
	void enviar() throws RelatorioNaoCriadoException;
}
