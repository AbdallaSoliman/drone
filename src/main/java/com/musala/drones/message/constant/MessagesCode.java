package com.musala.drones.message.constant;

public final class MessagesCode {
    public static final String SUCCESS_OPERATION = "SuccessOperation";
    public static final String SUCCESS_OPERATION_CUSTOM = "SuccessOperationCustom";
    public static final String FAIL_OPERATION = "FailOperation";
    public static final String NOT_IMPLEMENTED = "NotImplemented";

    private MessagesCode() {
        throw new IllegalStateException("ExceptionCode class");
    }
}
