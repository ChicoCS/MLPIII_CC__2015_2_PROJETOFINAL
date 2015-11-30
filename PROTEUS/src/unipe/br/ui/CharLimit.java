package unipe.br.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class CharLimit extends PlainDocument {
	  private int limite;

	  public CharLimit(int limite) {
	    this.limite = limite;
	  }

	  public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
		  if (getLength() + str.length() <= limite && Character.isDigit(str.charAt(0))){
			  super.insertString(offset, str, a);
		  }
	  }
}