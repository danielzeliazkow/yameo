package pl.yameo.recruitmenttask.exception;

public class FileAnalyzingException extends RuntimeException {
	
	private static final long serialVersionUID = 8955129060039731473L;

	public FileAnalyzingException(String message, Exception ex) {
		super(message, ex);
	}
}
