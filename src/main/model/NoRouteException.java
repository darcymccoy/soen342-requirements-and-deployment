package main.model;

public class NoRouteException extends Exception {
    public NoRouteException() {
		super();
	}

	public NoRouteException(String message) {
		super(message);
	}
}
