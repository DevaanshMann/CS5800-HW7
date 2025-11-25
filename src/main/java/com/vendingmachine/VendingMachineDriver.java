package com.vendingmachine;

import com.vendingmachine.model.Snack;
import com.vendingmachine.config.VendingMachine;

public class VendingMachineDriver {
    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("                         VENDING MACHINE ");
        System.out.println("================================================================\n");

        VendingMachine vendingMachine = new VendingMachine();

        Snack coke = new Snack("Coke", 1.50, 5);
        Snack pepsi = new Snack("Pepsi", 1.50, 5);
        Snack cheetos = new Snack("Cheetos", 2.00, 5);
        Snack doritos = new Snack("Doritos", 2.00, 5);
        Snack kitkat = new Snack("KitKat", 1.75, 5);
        Snack snickers = new Snack("Snickers", 1.75, 2);

        vendingMachine.addSnack(coke);
        vendingMachine.addSnack(pepsi);
        vendingMachine.addSnack(cheetos);
        vendingMachine.addSnack(doritos);
        vendingMachine.addSnack(kitkat);
        vendingMachine.addSnack(snickers);

        vendingMachine.initializeDispenseChain();

        vendingMachine.displayInventory();

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 1: Successful purchase (Coke) - Exact amount");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("Coke");
        vendingMachine.insertMoney(1.50);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 2: Successful purchase (Pepsi) - With change");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("Pepsi");
        vendingMachine.insertMoney(2.00);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 3: Insufficient money (Cheetos) - Should fail");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("Cheetos");
        vendingMachine.insertMoney(1.00);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 4: Successful purchase (Doritos)");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("Doritos");
        vendingMachine.insertMoney(2.50);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 5: Successful purchase (KitKat)");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("KitKat");
        vendingMachine.insertMoney(1.75);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 6: Buy Snickers #1 (qty: 2 -> 1)");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("Snickers");
        vendingMachine.insertMoney(1.75);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 7: Buy Snickers #2 (qty: 1 -> 0) - LAST ONE");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("Snickers");
        vendingMachine.insertMoney(2.00);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 8: Try Snickers when OUT OF STOCK (qty: 0)");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("Snickers");
        vendingMachine.insertMoney(1.75);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 9: Invalid snack (RedBull) - Not in inventory");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.selectSnack("RedBull");
        vendingMachine.insertMoney(2.00);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println(" TEST 10: Insert money WITHOUT selecting snack");
        System.out.println("-------------------------------------------------------------");
        vendingMachine.insertMoney(2.00);

        vendingMachine.displayInventory();

        System.out.println("\n================================================================");
        System.out.println("                     DEMO COMPLETED");
        System.out.println("");
        System.out.println("  Chain of Responsibility (4 handlers):");
        System.out.println("    1. Snack Validation -> 2. Availability Check ->");
        System.out.println("    3. Payment Validation -> 4. Dispense");
        System.out.println("");
        System.out.println("  State Pattern (3 states):");
        System.out.println("    Idle -> WaitingForMoney -> DispensingSnack -> Idle");
        System.out.println("================================================================");
    }
}