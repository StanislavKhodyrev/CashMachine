package CashMachine.command;

import CashMachine.CashMachine;
import CashMachine.ConsoleHelper;
import CashMachine.CurrencyManipulator;
import CashMachine.CurrencyManipulatorFactory;
import CashMachine.exception.InterruptOperationException;
import CashMachine.exception.NotAvailableCurrency;
import CashMachine.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;


class WithdrawCommand implements Command {
    private final ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));

        String currencyCode = ConsoleHelper.askCurrencyCode();


        while (true) {
            try {
                CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                String s = ConsoleHelper.readString();
                if (s == null) {
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                } else {
                    try {
                        int amount = Integer.parseInt(s);
                        boolean isAmountAvailable = manipulator.isAmountAvailable(amount);
                        if (isAmountAvailable) {
                            Map<Integer, Integer> denominations = manipulator.withdrawAmount(amount);
                            for (Integer item : denominations.keySet()) {
                                ConsoleHelper.writeMessage("\t" + item + " - " + denominations.get(item));
                            }
                            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, currencyCode));
                            break;
                        } else {
                            ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                        }
                    } catch (NumberFormatException e) {
                        ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                    }
                }
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            } catch (NotAvailableCurrency notAvailableCurrency) {
                ConsoleHelper.writeMessage("No money available in this currency");
                ConsoleHelper.writeMessage("Select another currency? [y/n]");
                String answer = ConsoleHelper.readString();
                if (answer.equals("y")) {
                    currencyCode = ConsoleHelper.askCurrencyCode();
                } else break;
            }
        }
    }

}
