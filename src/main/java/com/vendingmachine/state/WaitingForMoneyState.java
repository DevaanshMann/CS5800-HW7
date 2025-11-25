package com.vendingmachine.state;

import com.vendingmachine.config.VendingMachine;

public class WaitingForMoneyState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        System.out.println("\n>>> Error: Already selected a snack. Please insert money or cancel.");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("\n>>> Money inserted: $" + String.format("%.2f", amount));
        machine.setInsertedMoney(amount);

        machine.setState(new DispensingSnackState());
        machine.dispenseSnack();
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("\n>>> Error: Please insert money first.");
    }

    @Override
    public void returnMoney(VendingMachine machine) {
        double amount = machine.getInsertedMoney();
        if (amount > 0) {
            System.out.println("\n>>> Returning money: $" + String.format("%.2f", amount));
            machine.setInsertedMoney(0);
        }
        machine.setSelectedSnack(null);
        machine.setState(new IdleState());
    }
}
