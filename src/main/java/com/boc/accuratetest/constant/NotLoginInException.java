package com.boc.accuratetest.constant;

public class NotLoginInException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5597119366246728220L;

	 /**
     * Constructs a {@code NotLoginInException} with no detail message.
     */
    public NotLoginInException() {
        super();
    }

    /**
     * Constructs a {@code NotLoginInException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public NotLoginInException(String s) {
        super(s);
    }
}
