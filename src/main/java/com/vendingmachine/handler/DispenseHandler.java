package com.vendingmachine.handler;

import com.vendingmachine.model.DispenseRequest;
import com.vendingmachine.model.Snack;

public class DispenseHandler extends SnackDispenseHandler {

    @Override
    public boolean handleRequest(DispenseRequest request) {
        System.out.println("[Chain Step 4] Dispensing snack...");

        Snack snack = request.getSnack();
        double change = request.getMoneyInserted() - snack.getPrice();

        snack.decrementQuantity();
        System.out.println(snack.getName() + " dispensed!");

        if (change > 0) {
            System.out.println("Returning change: $" + String.format("%.2f", change));
        }

        return passToNextHandler(request);
    }
}
