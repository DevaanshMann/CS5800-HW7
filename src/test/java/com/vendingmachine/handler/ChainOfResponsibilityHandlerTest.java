package com.vendingmachine.handler;

import com.vendingmachine.model.DispenseRequest;
import com.vendingmachine.model.Snack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

/**
 * JUnit tests for Chain of Responsibility handlers
 */
public class ChainOfResponsibilityHandlerTest {

    private Map<String, Snack> inventory;
    private SnackDispenseHandler chain;

    @BeforeEach
    public void setUp() {
        // Create inventory
        inventory = new HashMap<>();
        inventory.put("coke", new Snack("Coke", 1.50, 5));
        inventory.put("pepsi", new Snack("Pepsi", 1.50, 0)); // Out of stock
        inventory.put("cheetos", new Snack("Cheetos", 2.00, 3));

        // Build the chain
        SnackValidationHandler validationHandler = new SnackValidationHandler(inventory);
        AvailabilityCheckHandler availabilityHandler = new AvailabilityCheckHandler();
        PaymentValidationHandler paymentHandler = new PaymentValidationHandler();
        DispenseHandler dispenseHandler = new DispenseHandler();

        validationHandler.setNextHandler(availabilityHandler);
        availabilityHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(dispenseHandler);

        chain = validationHandler;
    }

    @Test
    public void testChain_SuccessfulDispense() {
        DispenseRequest request = new DispenseRequest("Coke", 1.50);

        boolean result = chain.handleRequest(request);

        assertTrue(result);
        assertTrue(request.isValid());
        assertEquals(4, inventory.get("coke").getQuantity()); // Should be decremented
    }

    @Test
    public void testChain_InvalidSnackName() {
        DispenseRequest request = new DispenseRequest("RedBull", 2.00);

        boolean result = chain.handleRequest(request);

        assertFalse(result);
        assertFalse(request.isValid());
        assertTrue(request.getErrorMessage().contains("not found"));
    }

    @Test
    public void testChain_OutOfStock() {
        DispenseRequest request = new DispenseRequest("Pepsi", 1.50);

        boolean result = chain.handleRequest(request);

        assertFalse(result);
        assertFalse(request.isValid());
        assertTrue(request.getErrorMessage().contains("out of stock"));
    }

    @Test
    public void testChain_InsufficientFunds() {
        DispenseRequest request = new DispenseRequest("Cheetos", 1.00);

        boolean result = chain.handleRequest(request);

        assertFalse(result);
        assertFalse(request.isValid());
        assertTrue(request.getErrorMessage().contains("Insufficient funds"));
        assertEquals(3, inventory.get("cheetos").getQuantity()); // Should NOT be decremented
    }

    @Test
    public void testChain_WithChange() {
        DispenseRequest request = new DispenseRequest("Coke", 2.00);

        boolean result = chain.handleRequest(request);

        assertTrue(result);
        assertTrue(request.isValid());
        assertEquals(4, inventory.get("coke").getQuantity());
    }

    @Test
    public void testValidationHandler_Standalone() {
        SnackValidationHandler handler = new SnackValidationHandler(inventory);
        DispenseRequest request = new DispenseRequest("Coke", 1.50);

        boolean result = handler.handleRequest(request);

        assertTrue(result);
        assertNotNull(request.getSnack());
        assertEquals("Coke", request.getSnack().getName());
    }

    @Test
    public void testAvailabilityHandler_InStock() {
        AvailabilityCheckHandler handler = new AvailabilityCheckHandler();
        DispenseRequest request = new DispenseRequest("Coke", 1.50);
        request.setSnack(inventory.get("coke"));

        boolean result = handler.handleRequest(request);

        assertTrue(result);
        assertTrue(request.isValid());
    }

    @Test
    public void testAvailabilityHandler_OutOfStock() {
        AvailabilityCheckHandler handler = new AvailabilityCheckHandler();
        DispenseRequest request = new DispenseRequest("Pepsi", 1.50);
        request.setSnack(inventory.get("pepsi"));

        boolean result = handler.handleRequest(request);

        assertFalse(result);
        assertFalse(request.isValid());
    }

    @Test
    public void testPaymentHandler_Sufficient() {
        PaymentValidationHandler handler = new PaymentValidationHandler();
        DispenseRequest request = new DispenseRequest("Coke", 2.00);
        request.setSnack(inventory.get("coke"));

        boolean result = handler.handleRequest(request);

        assertTrue(result);
        assertTrue(request.isValid());
    }

    @Test
    public void testPaymentHandler_Insufficient() {
        PaymentValidationHandler handler = new PaymentValidationHandler();
        DispenseRequest request = new DispenseRequest("Cheetos", 1.00);
        request.setSnack(inventory.get("cheetos"));

        boolean result = handler.handleRequest(request);

        assertFalse(result);
        assertFalse(request.isValid());
    }
}
