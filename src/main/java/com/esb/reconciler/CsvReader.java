package com.esb.reconciler;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.esb.reconciler.model.Transaction;
import com.opencsv.CSVReader;

public class CsvReader {

    public static List<Transaction> readTransactions(String path) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            reader.readNext();  // to skip the header line
            while ((nextLine = reader.readNext()) != null) {
                transactions.add(new Transaction(nextLine[0], nextLine[1], nextLine[2]));
            }
        }
        return transactions;
    }
}
