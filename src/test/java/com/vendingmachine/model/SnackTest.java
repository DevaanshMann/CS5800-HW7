package com.vendingmachine.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Snack class
 */
public class SnackTest {

    private Snack snack;

    @BeforeEach
    public void setUp() {
        snack = new Snack("Coke", 1.50, 5);
    }

    @Test
    public void testSnackCreation() {
        assertEquals("Coke", snack.getName());
        assertEquals(1.50, snack.getPrice(), 0.01);
        assertEquals(5, snack.getQuantity());
    }

    @Test
    public void testIsAvailable_WhenInStock() {
        assertTrue(snack.isAvailable());
    }

    @Test
    public void testIsAvailable_WhenOutOfStock() {
        Snack emptySnack = new Snack("Pepsi", 1.50, 0);
        assertFalse(emptySnack.isAvailable());
    }

    @Test
    public void testDecrementQuantity() {
        assertEquals(5, snack.getQuantity());
        snack.decrementQuantity();
        assertEquals(4, snack.getQuantity());
    }

    @Test
    public void testDecrementQuantity_WhenZero() {
        Snack emptySnack = new Snack("Pepsi", 1.50, 0);
        emptySnack.decrementQuantity();
        assertEquals(0, emptySnack.getQuantity()); // Should remain 0
    }

    @Test
    public void testSetQuantity() {
        snack.setQuantity(10);
        assertEquals(10, snack.getQuantity());
    }

    @Test
    public void testToString() {
        String expected = "Coke ($1.50) - Quantity: 5";
        assertEquals(expected, snack.toString());
    }
}
