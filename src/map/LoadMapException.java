package map;

public class LoadMapException extends Exception {

	private String msg;
	
	public LoadMapException(String msg) {
		this.msg = msg;
	}
	
	public LoadMapException(Exception cause) {
		this.msg = cause.getMessage();
	}
	
	@Override
	public String getMessage() {
		return this.msg;
	}
	
}
