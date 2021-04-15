package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.*;

import java.util.ResourceBundle;


class LoginCommand implements Command {
    private final ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");
    private final ResourceBundle validCreditCards = ResourceBundle.getBundle
            (CashMachine.class.getPackage().getName() + ".resources.verifiedCards");

    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));

        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.data.number"));
            String creditCardNumber = ConsoleHelper.readString();
            ConsoleHelper.writeMessage(res.getString("specify.data.pin"));
            String pinStr = ConsoleHelper.readString();
            if (creditCardNumber == null || (creditCardNumber = creditCardNumber.trim()).length() != 12 ||
                    pinStr == null || (pinStr = pinStr.trim()).length() != 4) {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            } else {
                try {
                    if (validCreditCards.containsKey(creditCardNumber) && pinStr.equals(validCreditCards.getString(creditCardNumber))) {
                        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), creditCardNumber));
                        break;
                    } else {
                        ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), creditCardNumber));
                        ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                    }
                } catch (NumberFormatException e) {
                    ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                }
            }
        }

    }
}


