package com.vendingmachine.handler;

import com.vendingmachine.model.DispenseRequest;
import com.vendingmachine.model.Snack;

public class PaymentValidationHandler extends SnackDispenseHandler {

    @Override
    public boolean handleRequest(DispenseRequest request) {
        System.out.println("[Chain Step 3] Validating payment...");

        Snack snack = request.getSnack();
        double required = snack.getPrice();
        double inserted = request.getMoneyInserted();

        if (inserted < required) {
            request.setValid(false);
            request.setErrorMessage(
                    String.format("Insufficient funds. Required: $%.2f, Inserted: $%.2f",
                            required, inserted)
            );
            System.out.println(String.format("Insufficient funds! Need $%.2f, got $%.2f",
                    required, inserted));
            return false;
        }

        System.out.println(String.format("    Payment sufficient! Required: $%.2f, Inserted: $%.2f",
                required, inserted));
        return passToNext(request);
    }
}
