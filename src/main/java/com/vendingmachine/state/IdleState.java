package com.vendingmachine.state;

import com.vendingmachine.config.VendingMachine;

public class IdleState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        System.out.println("\n>>> Snack selected: " + snackName);
        machine.setSelectedSnack(snackName);
        machine.setState(new WaitingForMoneyState());
        System.out.println(">>> Please insert money.");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("\n>>> Error: Please select a snack first.");
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("\n>>> Error: Please select a snack first.");
    }

    @Override
    public void returnMoney(VendingMachine machine) {
        System.out.println("\n>>> No money to return.");
    }
}