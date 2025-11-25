package com.vendingmachine.combinedtest;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.vendingmachine.config",
                "com.vendingmachine.handler",
                "com.vendingmachine.model",
                "com.vendingmachine.state"})
public class VendingMachineCombinedTest {
}

