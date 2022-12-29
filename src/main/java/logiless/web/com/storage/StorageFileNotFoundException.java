package logiless.web.com.storage;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = -6209504473556272608L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}
	
	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
