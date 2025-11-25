package com.vendingmachine.handler;

import com.vendingmachine.model.DispenseRequest;

public abstract class SnackDispenseHandler {
    protected SnackDispenseHandler nextHandler;

    public void setNextHandler(SnackDispenseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean handleRequest(DispenseRequest request);

    protected boolean passToNext(DispenseRequest request) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(request);
        }
        return true;
    }
}
