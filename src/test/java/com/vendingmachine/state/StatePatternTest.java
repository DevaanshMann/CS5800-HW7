package com.vendingmachine.state;

import com.vendingmachine.config.VendingMachine;
import com.vendingmachine.model.Snack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for State Pattern implementation
 */
public class StatePatternTest {

    private VendingMachine machine;

    @BeforeEach
    public void setUp() {
        machine = new VendingMachine();

        // Add snacks
        machine.addSnack(new Snack("Coke", 1.50, 5));
        machine.addSnack(new Snack("Pepsi", 1.50, 0)); // Out of stock
        machine.addSnack(new Snack("Cheetos", 2.00, 3));

        // Initialize chain
        machine.initializeDispenseChain();
    }

    @Test
    public void testInitialState_IsIdle() {
        assertTrue(machine.getState() instanceof IdleState);
    }

    @Test
    public void testIdleState_SelectSnack() {
        machine.selectSnack("Coke");

        assertEquals("Coke", machine.getSelectedSnack());
        assertTrue(machine.getState() instanceof WaitingForMoneyState);
    }

    @Test
    public void testIdleState_InsertMoneyWithoutSelection() {
        // Should remain in idle state
        machine.insertMoney(1.50);

        assertTrue(machine.getState() instanceof IdleState);
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testWaitingForMoneyState_InsertMoney() {
        machine.selectSnack("Coke");
        assertTrue(machine.getState() instanceof WaitingForMoneyState);

        machine.insertMoney(1.50);

        // Should transition to Idle after dispensing
        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testWaitingForMoneyState_SelectSnackAgain() {
        machine.selectSnack("Coke");
        String firstSnack = machine.getSelectedSnack();

        machine.selectSnack("Pepsi"); // Try to select another

        // Should remain with first selection
        assertEquals(firstSnack, machine.getSelectedSnack());
        assertTrue(machine.getState() instanceof WaitingForMoneyState);
    }

    @Test
    public void testSuccessfulTransaction_StateTransitions() {
        // Idle -> select snack -> WaitingForMoney
        machine.selectSnack("Coke");
        assertTrue(machine.getState() instanceof WaitingForMoneyState);

        // WaitingForMoney -> insert money -> Dispensing -> Idle
        machine.insertMoney(1.50);
        assertTrue(machine.getState() instanceof IdleState);
    }

    @Test
    public void testFailedTransaction_InsufficientMoney() {
        machine.selectSnack("Cheetos");
        machine.insertMoney(1.00); // Not enough (needs $2.00)

        // Should return to idle
        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testFailedTransaction_OutOfStock() {
        machine.selectSnack("Pepsi");
        machine.insertMoney(1.50);

        // Should return to idle
        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testReturnMoney_FromWaitingState() {
        machine.selectSnack("Coke");
        machine.setInsertedMoney(1.50); // Manually set money

        assertTrue(machine.getState() instanceof WaitingForMoneyState);

        machine.returnMoney();

        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }
}
