package pl.yameo.recruitmenttask.exception;

public class CommonException extends FileAnalyzingException {
	
	private static final long serialVersionUID = -7702869010350266792L;

	public CommonException(Exception e) {
		super("Couldn't analyze file. Please try again later", e);
	}
}
