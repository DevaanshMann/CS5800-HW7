package com.vendingmachine.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for DispenseRequest class
 */
public class DispenseRequestTest {

    private DispenseRequest request;
    private Snack snack;

    @BeforeEach
    public void setUp() {
        request = new DispenseRequest("Coke", 1.50);
        snack = new Snack("Coke", 1.50, 5);
    }

    @Test
    public void testRequestCreation() {
        assertEquals("Coke", request.getSnackName());
        assertEquals(1.50, request.getMoneyInserted(), 0.01);
        assertTrue(request.isValid()); // Should be valid by default
        assertEquals("", request.getErrorMessage());
    }

    @Test
    public void testSetSnack() {
        assertNull(request.getSnack());
        request.setSnack(snack);
        assertNotNull(request.getSnack());
        assertEquals("Coke", request.getSnack().getName());
    }

    @Test
    public void testSetValid() {
        assertTrue(request.isValid());
        request.setValid(false);
        assertFalse(request.isValid());
    }

    @Test
    public void testSetErrorMessage() {
        assertEquals("", request.getErrorMessage());
        request.setErrorMessage("Snack not found");
        assertEquals("Snack not found", request.getErrorMessage());
    }

    @Test
    public void testInvalidRequest() {
        request.setValid(false);
        request.setErrorMessage("Insufficient funds");

        assertFalse(request.isValid());
        assertEquals("Insufficient funds", request.getErrorMessage());
    }
}
