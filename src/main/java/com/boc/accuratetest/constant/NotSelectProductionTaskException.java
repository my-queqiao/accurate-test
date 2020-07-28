package com.boc.accuratetest.constant;

public class NotSelectProductionTaskException extends RuntimeException{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -7428590338207668080L;

	/**
     * Constructs a {@code NotLoginInException} with no detail message.
     */
    public NotSelectProductionTaskException() {
        super();
    }

    /**
     * Constructs a {@code NotLoginInException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public NotSelectProductionTaskException(String s) {
        super(s);
    }
}
