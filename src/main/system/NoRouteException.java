package main.system;

public class NoRouteException extends Exception {
    public NoRouteException() {
		super();
	}

	public NoRouteException(String message) {
		super(message);
	}
}
