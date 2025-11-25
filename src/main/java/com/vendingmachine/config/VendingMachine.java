package com.vendingmachine.config;

import com.vendingmachine.handler.*;
import com.vendingmachine.model.Snack;
import com.vendingmachine.state.IdleState;
import com.vendingmachine.state.StateOfVendingMachine;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private StateOfVendingMachine currentState;
    private Map<String, Snack> inventory;
    private SnackDispenseHandler dispenseHandler;
    private String selectedSnack;
    private double insertedMoney;

    public VendingMachine() {
        this.currentState = new IdleState();
        this.inventory = new HashMap<>();
        this.selectedSnack = null;
        this.insertedMoney = 0.0;
    }

    public void addSnack(Snack snack) {
        inventory.put(snack.getName().toLowerCase(), snack);
    }

    public Map<String, Snack> getInventory() {
        return inventory;
    }

    public void initializeDispenseChain() {
        SnackValidationHandler validationHandler = new SnackValidationHandler(inventory);
        AvailabilityCheckHandler availabilityHandler = new AvailabilityCheckHandler();
        PaymentValidationHandler paymentHandler = new PaymentValidationHandler();
        DispenseHandler dispenseHandler = new DispenseHandler();

        validationHandler.setNextHandler(availabilityHandler);
        availabilityHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(dispenseHandler);

        this.dispenseHandler = validationHandler;
    }

    public SnackDispenseHandler getDispenseHandler() {
        return dispenseHandler;
    }

    public void setState(StateOfVendingMachine state) {
        this.currentState = state;
    }

    public StateOfVendingMachine getState() {
        return currentState;
    }

    public void setSelectedSnack(String snackName) {
        this.selectedSnack = snackName;
    }

    public String getSelectedSnack() {
        return selectedSnack;
    }

    public void setInsertedMoney(double amount) {
        this.insertedMoney = amount;
    }

    public double getInsertedMoney() {
        return insertedMoney;
    }

    public void selectSnack(String snackName) {
        currentState.selectSnack(this, snackName);
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(this, amount);
    }

    public void dispenseSnack() {
        currentState.dispenseSnack(this);
    }

    public void returnMoney() {
        currentState.returnMoney(this);
    }

    public void displayInventory() {
        System.out.println("\n========== VENDING MACHINE INVENTORY ==========");
        for (Snack snack : inventory.values()) {
            System.out.println("  " + snack);
        }
        System.out.println("===============================================\n");
    }
}
