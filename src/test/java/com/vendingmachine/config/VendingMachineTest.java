package com.vendingmachine.config;

import com.vendingmachine.handler.SnackValidationHandler;
import com.vendingmachine.model.Snack;
import com.vendingmachine.state.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private VendingMachine machine;

    @BeforeEach
    public void setUp() {
        machine = new VendingMachine();
        machine.addSnack(new Snack("Coke", 1.50, 5));
        machine.addSnack(new Snack("Pepsi", 1.50, 3));
        machine.addSnack(new Snack("Cheetos", 2.00, 0));
        machine.initializeDispenseChain();
    }

    @Test
    public void testAddSnack() {
        VendingMachine newMachine = new VendingMachine();
        Snack doritos = new Snack("Doritos", 2.00, 5);

        newMachine.addSnack(doritos);

        assertNotNull(newMachine.getInventory().get("doritos"));
        assertEquals("Doritos", newMachine.getInventory().get("doritos").getName());
    }

    @Test
    public void testGetInventory() {
        assertNotNull(machine.getInventory());
        assertEquals(3, machine.getInventory().size());
        assertTrue(machine.getInventory().containsKey("coke"));
        assertTrue(machine.getInventory().containsKey("pepsi"));
        assertTrue(machine.getInventory().containsKey("cheetos"));
    }

    @Test
    public void testSetAndGetState() {
        assertTrue(machine.getState() instanceof IdleState);

        machine.setState(new WaitingForMoneyState());
        assertTrue(machine.getState() instanceof WaitingForMoneyState);
    }

    @Test
    public void testSetAndGetSelectedSnack() {
        assertNull(machine.getSelectedSnack());

        machine.setSelectedSnack("Coke");
        assertEquals("Coke", machine.getSelectedSnack());

        machine.setSelectedSnack(null);
        assertNull(machine.getSelectedSnack());
    }

    @Test
    public void testSetAndGetInsertedMoney() {
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);

        machine.setInsertedMoney(1.50);
        assertEquals(1.50, machine.getInsertedMoney(), 0.01);

        machine.setInsertedMoney(0.0);
        assertEquals(0.0, machine.getInsertedMoney(), 0.01);
    }

    @Test
    public void testInitializeDispenseChain() {
        VendingMachine newMachine = new VendingMachine();
        newMachine.addSnack(new Snack("Coke", 1.50, 5));

        assertNull(newMachine.getDispenseHandler());

        newMachine.initializeDispenseChain();

        assertNotNull(newMachine.getDispenseHandler());
        assertTrue(newMachine.getDispenseHandler() instanceof SnackValidationHandler);
    }

    @Test
    public void testCompleteTransaction_Success() {
        int initialQuantity = machine.getInventory().get("coke").getQuantity();

        machine.selectSnack("Coke");
        machine.insertMoney(1.50);

        int finalQuantity = machine.getInventory().get("coke").getQuantity();
        assertEquals(initialQuantity - 1, finalQuantity);
        assertTrue(machine.getState() instanceof IdleState);
    }

    @Test
    public void testCompleteTransaction_InsufficientFunds() {
        int initialQuantity = machine.getInventory().get("pepsi").getQuantity();

        machine.selectSnack("Pepsi");
        machine.insertMoney(1.00);

        int finalQuantity = machine.getInventory().get("pepsi").getQuantity();
        assertEquals(initialQuantity, finalQuantity);
        assertTrue(machine.getState() instanceof IdleState);
    }

    @Test
    public void testCompleteTransaction_OutOfStock() {
        assertEquals(0, machine.getInventory().get("cheetos").getQuantity());

        machine.selectSnack("Cheetos");
        machine.insertMoney(2.00);

        assertEquals(0, machine.getInventory().get("cheetos").getQuantity());
        assertTrue(machine.getState() instanceof IdleState);
    }

    @Test
    public void testMultipleTransactions() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.50);
        assertEquals(4, machine.getInventory().get("coke").getQuantity());

        machine.selectSnack("Pepsi");
        machine.insertMoney(2.00);
        assertEquals(2, machine.getInventory().get("pepsi").getQuantity());

        machine.selectSnack("Coke");
        machine.insertMoney(1.50);
        assertEquals(3, machine.getInventory().get("coke").getQuantity());
    }

    @Test
    public void testInventoryDepletion() {
        for (int i = 0; i < 3; i++) {
            machine.selectSnack("Pepsi");
            machine.insertMoney(1.50);
        }

        assertEquals(0, machine.getInventory().get("pepsi").getQuantity());

        machine.selectSnack("Pepsi");
        machine.insertMoney(1.50);

        assertEquals(0, machine.getInventory().get("pepsi").getQuantity());
        assertTrue(machine.getState() instanceof IdleState);
    }
}