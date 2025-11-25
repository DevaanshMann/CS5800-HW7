package com.vendingmachine.handler;

import com.vendingmachine.model.DispenseRequest;
import com.vendingmachine.model.Snack;

public class AvailabilityCheckHandler extends SnackDispenseHandler {

    @Override
    public boolean handleRequest(DispenseRequest request) {
        System.out.println("[Chain Step 2] Checking availability...");

        Snack snack = request.getSnack();

        if (!snack.isAvailable()) {
            request.setValid(false);
            request.setErrorMessage(snack.getName() + " is out of stock");
            System.out.println("Out of stock! Quantity: 0");
            return false;
        }

        System.out.println("    In stock! Quantity: " + snack.getQuantity());
        return passToNextHandler(request);
    }
}
