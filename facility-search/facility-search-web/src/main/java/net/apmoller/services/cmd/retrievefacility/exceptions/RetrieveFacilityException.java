package net.apmoller.services.cmd.retrievefacility.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateFacilityException.
 */
public class RetrieveFacilityException extends RuntimeException {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	/** The message. */
	private final String		message;

	/**
	 * Instantiates a new validation exception.
	 */
	public RetrieveFacilityException() {
		super();
		message = "Exception occured";
	}

	/**
	 * Instantiates a new validation exception.
	 *
	 * @param message
	 *            the message
	 */
	public RetrieveFacilityException(String message) {

		super(message);

		this.message = message;
	}

	/**
	 * Instantiates a new validation exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public RetrieveFacilityException(Throwable cause) {
		super(cause);
		message = cause.getLocalizedMessage();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

}
