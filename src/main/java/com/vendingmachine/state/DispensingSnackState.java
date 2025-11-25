package com.vendingmachine.state;

import com.vendingmachine.config.VendingMachine;
import com.vendingmachine.model.DispenseRequest;

public class DispensingSnackState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        System.out.println("\n>>> Currently dispensing. Please wait.");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("\n>>> Currently dispensing. Please wait.");
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("\n========== PROCESSING DISPENSE REQUEST ==========");

        String selectedSnackName = machine.getSelectedSnack();
        double insertedMoney = machine.getInsertedMoney();

        DispenseRequest request = new DispenseRequest(selectedSnackName, insertedMoney);

        boolean success = machine.getDispenseHandler().handleRequest(request);

        System.out.println("=================================================\n");

        if (!success) {
            System.out.println(">>> X TRANSACTION FAILED: " + request.getErrorMessage());
            System.out.println(">>> Returning money: $" + String.format("%.2f", insertedMoney));
        } else {
            System.out.println(">>> TRANSACTION SUCCESSFUL!");
            System.out.println(">>> Enjoy your " + selectedSnackName + "!");
        }

        machine.setSelectedSnack(null);
        machine.setInsertedMoney(0);
        machine.setState(new IdleState());
    }

    @Override
    public void returnMoney(VendingMachine machine) {
        System.out.println("\n>>> Cannot return money while dispensing.");
    }
}
