package com.vendingmachine.handler;

import com.vendingmachine.model.DispenseRequest;
import com.vendingmachine.model.Snack;

import java.util.Map;

public class SnackValidationHandler extends SnackDispenseHandler {
    private Map<String, Snack> inventory;

    public SnackValidationHandler(Map<String, Snack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean handleRequest(DispenseRequest request) {
        System.out.println("[Chain Step 1] Validating snack selection...");

        Snack snack = inventory.get(request.getSnackName().toLowerCase());

        if (snack == null) {
            request.setValid(false);
            request.setErrorMessage("Snack '" + request.getSnackName() + "' not found in inventory");
            System.out.println("Snack not found!");
            return false;
        }

        request.setSnack(snack);
        System.out.println("Snack found: " + snack.getName());
        return passToNextHandler(request);
    }
}

