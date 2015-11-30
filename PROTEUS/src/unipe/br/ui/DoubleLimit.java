package unipe.br.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class DoubleLimit extends PlainDocument {
	  private String regexCheck = "(\\d+|\\.|\\,)*";

	  public DoubleLimit() {
	  }

	  public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
		  if(!((getText(0, getLength()).contains(".") || getText(0, getLength()).contains(",")) && (str.equals(",") || str.equals(".")))){
			  if (str.matches(regexCheck)){
				  super.insertString(offset, str, a);
			  }
		  }
	  }
}