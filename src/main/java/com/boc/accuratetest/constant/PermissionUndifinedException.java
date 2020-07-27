package com.boc.accuratetest.constant;

public class PermissionUndifinedException extends RuntimeException{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 5902998110160769377L;

	/**
     * Constructs a {@code NotLoginInException} with no detail message.
     */
    public PermissionUndifinedException() {
        super();
    }

    /**
     * Constructs a {@code NotLoginInException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public PermissionUndifinedException(String s) {
        super(s);
    }
}
