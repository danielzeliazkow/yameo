package pl.yameo.recruitmenttask.exception;

public class XMLParsingException extends FileAnalyzingException {

	private static final long serialVersionUID = 1427765112135752919L;

	public XMLParsingException(String message, Exception ex) {
		super(message, ex);
	}
}
