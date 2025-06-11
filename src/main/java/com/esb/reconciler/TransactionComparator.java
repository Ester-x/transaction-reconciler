package com.esb.reconciler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esb.reconciler.model.Transaction;

public class TransactionComparator {

    public static Map<String, List<Transaction>> compare(List<Transaction> dbList, List<Transaction> csvList) {
        Map<String, List<Transaction>> result = new HashMap<>();
        result.put("onlyInDb", new ArrayList<>());
        result.put("onlyInCsv", new ArrayList<>());
        result.put("mismatched", new ArrayList<>());

        Map<String, Transaction> dbMap = new HashMap<>();
        for (Transaction t : dbList) {
            dbMap.put(t.getId(), t);
        }

        Map<String, Transaction> csvMap = new HashMap<>();
        for (Transaction t : csvList) {
            csvMap.put(t.getId(), t);
        }

        for (String id : csvMap.keySet()) {
            if (!dbMap.containsKey(id)) {
                result.get("onlyInCsv").add(csvMap.get(id));
            } else if (!transactionsMatch(dbMap.get(id), csvMap.get(id))) {
                result.get("mismatched").add(csvMap.get(id));
            }
        }

        for (String id : dbMap.keySet()) {
            if (!csvMap.containsKey(id)) {
                result.get("onlyInDb").add(dbMap.get(id));
            }
        }

        return result;
    }

    private static boolean transactionsMatch(Transaction t1, Transaction t2) {
        // compare id 
        if (!t1.getId().equals(t2.getId())) {
            return false;
        }

        try {
            // parse and compare dates
            DateTimeFormatter dbDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter csvDateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate date1 = LocalDate.parse(t1.getDate(), dbDateFormat);
            LocalDate date2 = LocalDate.parse(t2.getDate(), csvDateFormat);
            if (!date1.equals(date2)) {
                return false;
            }
        } catch (Exception e) {
            // If date parsing fails dont match
            return false;
        }

        try {

            // compare amounts as BigDecimal for numeric equality
            BigDecimal amount1 = new BigDecimal(t1.getAmount());
            BigDecimal amount2 = new BigDecimal(t2.getAmount());
            if (amount1.compareTo(amount2) != 0) {
                return false;
            }
        } catch (Exception e) {
            // If amount parsing fails dont matching
            return false;
        }

        return true;
    }
}
