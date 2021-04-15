package CashMachine;

import CashMachine.exception.NotAvailableCurrency;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {
    private final static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulator createManipulatorByCurrencyCode(String currencyCode) {
        currencyCode = currencyCode.toUpperCase();
        map.put(currencyCode, new CurrencyManipulator(currencyCode));
        return map.get(currencyCode);
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) throws NotAvailableCurrency {
        currencyCode = currencyCode.toUpperCase();
        if (!map.containsKey(currencyCode)) {
            throw new NotAvailableCurrency();
        }
        return map.get(currencyCode);
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return map.values();
    }
}
