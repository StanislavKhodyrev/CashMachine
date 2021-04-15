package CashMachine;

import CashMachine.exception.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class ConsoleHelper {
    private static final BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static final ResourceBundle res = ResourceBundle.getBundle
            (CashMachine.class.getPackage().getName() + ".resources.common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String text = null;
        try {
            text = bis.readLine();
            if (text.equalsIgnoreCase("exit")) {
                throw new InterruptOperationException();
            }
        } catch (IOException ignored) {
        }

        return text;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        List<String> operationCode = Arrays.asList("RUB", "USD", "EUR");
        writeMessage(res.getString("choose.currency.code"));
        String code;
        code = readString();

        while (!operationCode.contains(code.toUpperCase())) {
            writeMessage("You entered the currency in the wrong format" +
                    '\n' + "please enter currency again");
            code = readString();
        }
        return code.toUpperCase();

    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        writeMessage(res.getString("operation.WITHDRAW"));
        String line;
        String[] currencyCountAndDenomination;
        line = readString();
        currencyCountAndDenomination = line.split(" ");
        while (!line.matches("\\d+ \\d+") || currencyCountAndDenomination.length > 2) {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            line = readString();
            currencyCountAndDenomination = line.split(" ");
        }

        return currencyCountAndDenomination;
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString("choose.operation"));
        Operation operation = null;
        int number = 0;
        while (number < 1 || number > 4) {
            try {
                number = Integer.parseInt(readString());
                operation = Operation.getAllowableOperationByOrdinal(number);
            } catch (IllegalArgumentException e) {
                writeMessage(res.getString("choose.operation"));
            }
        }
        return operation;
    }

    public static void printExitMessage() {
       ConsoleHelper.writeMessage(res.getString("the.end"));
    }


}
