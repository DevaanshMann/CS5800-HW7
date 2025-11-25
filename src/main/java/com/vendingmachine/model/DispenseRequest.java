package com.vendingmachine.model;

public class DispenseRequest {
    private String snackName;
    private double moneyInserted;
    private Snack snack;
    private boolean isValid;
    private String errorMessage;

    public DispenseRequest(String snackName, double moneyInserted) {
        this.snackName = snackName;
        this.moneyInserted = moneyInserted;
        this.isValid = true;
        this.errorMessage = "";
    }

    public String getSnackName() {
        return snackName;
    }

    public double getMoneyInserted() {
        return moneyInserted;
    }

    public Snack getSnack() {
        return snack;
    }

    public void setSnack(Snack snack) {
        this.snack = snack;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
