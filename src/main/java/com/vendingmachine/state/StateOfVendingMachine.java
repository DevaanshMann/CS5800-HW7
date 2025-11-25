package com.vendingmachine.state;

import com.vendingmachine.config.VendingMachine;

public interface StateOfVendingMachine {
    void selectSnack(VendingMachine machine, String snackName);
    void insertMoney(VendingMachine machine, double amount);
    void dispenseSnack(VendingMachine machine);
    void returnMoney(VendingMachine machine);
}
