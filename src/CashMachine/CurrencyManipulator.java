package CashMachine;

import CashMachine.exception.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class CurrencyManipulator {
    private final String currencyCode;
    private final Map<Integer, Integer> denominations = new HashMap<>();


    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else denominations.put(denomination, count);
    }

    public int getTotalAmount() {
        return denominations.entrySet().stream()
                .mapToInt(e -> e.getValue() * e.getKey()).sum();
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        map.putAll(denominations);
        Map<Integer, Integer> returnedMap = new HashMap<>();

        int balance = getTotalAmount();
        if (balance < expectedAmount) {
            throw new NotEnoughMoneyException();
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

            int nominal = entry.getKey();
            int count = entry.getValue();
            int total = nominal * count;

            int rest = count - expectedAmount / nominal; // сколько остается
            if (rest <= 0) {
                balance = balance - total;
                expectedAmount = expectedAmount - total;
                if (count - rest != 0) returnedMap.put(nominal, count - rest);
                denominations.remove(nominal); //replace(nominal, 0)
            } else if (total >= expectedAmount) {
                balance = balance - (count - rest) * nominal;
                expectedAmount = expectedAmount - (count - rest) * nominal;
                if (count - rest != 0) returnedMap.put(nominal, count - rest);
                denominations.replace(nominal,rest);
            }
        }
        if (expectedAmount != 0) throw new NotEnoughMoneyException();

        return returnedMap;
    }
}
