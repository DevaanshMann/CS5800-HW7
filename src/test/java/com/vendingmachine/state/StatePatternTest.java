package com.vendingmachine.state;

import com.vendingmachine.config.VendingMachine;
import com.vendingmachine.model.Snack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class StatePatternTest {

    private VendingMachine machine;

    @BeforeEach
    public void setUp() {
        machine = new VendingMachine();

        machine.addSnack(new Snack("Coke", 1.50, 5));
        machine.addSnack(new Snack("Pepsi", 1.50, 0));
        machine.addSnack(new Snack("Cheetos", 2.00, 3));

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
        machine.insertMoney(1.50);

        assertTrue(machine.getState() instanceof IdleState);
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testWaitingForMoneyState_InsertMoney() {
        machine.selectSnack("Coke");
        assertTrue(machine.getState() instanceof WaitingForMoneyState);

        machine.insertMoney(1.50);

        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testWaitingForMoneyState_SelectSnackAgain() {
        machine.selectSnack("Coke");
        String firstSnack = machine.getSelectedSnack();

        machine.selectSnack("Pepsi");

        assertEquals(firstSnack, machine.getSelectedSnack());
        assertTrue(machine.getState() instanceof WaitingForMoneyState);
    }

    @Test
    public void testSuccessfulTransaction_StateTransitions() {
        machine.selectSnack("Coke");
        assertTrue(machine.getState() instanceof WaitingForMoneyState);

        machine.insertMoney(1.50);
        assertTrue(machine.getState() instanceof IdleState);
    }

    @Test
    public void testFailedTransaction_InsufficientMoney() {
        machine.selectSnack("Cheetos");
        machine.insertMoney(1.00);

        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testFailedTransaction_OutOfStock() {
        machine.selectSnack("Pepsi");
        machine.insertMoney(1.50);

        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testReturnMoney_FromWaitingState() {
        machine.selectSnack("Coke");
        machine.setInsertedMoney(1.50);

        assertTrue(machine.getState() instanceof WaitingForMoneyState);

        machine.returnMoney();

        assertTrue(machine.getState() instanceof IdleState);
        assertNull(machine.getSelectedSnack());
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }
}
