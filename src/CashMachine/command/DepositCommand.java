package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.*;
import java.util.ResourceBundle;


class DepositCommand implements Command {
    private final ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.createManipulatorByCurrencyCode(currencyCode);

        while (true) {
            String[] split = ConsoleHelper.getValidTwoDigits(currencyCode);
            try {
                int denomination = Integer.parseInt(split[0]);
                int count = Integer.parseInt(split[1]);
                manipulator.addAmount(denomination, count);
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), (denomination * count), currencyCode));
                break;
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
    }
}
