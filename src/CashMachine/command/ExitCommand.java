package CashMachine.command;

import CashMachine.*;
import CashMachine.exception.*;

import java.util.ResourceBundle;


class ExitCommand implements Command {
    private final ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en");



    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
        String result = ConsoleHelper.readString();
        if (result.equalsIgnoreCase("y")) {
            ConsoleHelper.writeMessage(res.getString("thank.message"));
        } else {
            ConsoleHelper.printExitMessage();
        }
    }
}
