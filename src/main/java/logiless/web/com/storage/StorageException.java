package logiless.web.com.storage;

public class StorageException extends RuntimeException {
	
	private static final long serialVersionUID = 4670153308470619662L;
	
	public StorageException(String message) {
		super(message);
	}
	
	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
