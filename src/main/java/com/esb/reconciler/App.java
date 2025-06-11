package com.esb.reconciler;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.esb.reconciler.model.Transaction;

public class App {

    public static void main(String[] args) {
        try {
            Properties config = new Properties();
            try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    System.out.println("Sorry, unable to find application.properties");
                    return;
                }
                config.load(input);
            }

            // Download CSV
            new SftpService(config).downloadCsv();

            // Read from DB
            List<Transaction> dbTransactions = new DatabaseService(config).fetchTransactions();

            // Read from CSV
            List<Transaction> csvTransactions = CsvReader.readTransactions(config.getProperty("sftp.local.path"));

            // Compare
            Map<String, List<Transaction>> deltas = TransactionComparator.compare(dbTransactions, csvTransactions);

            // Generate html report
            ReportGenerator.generate(config.getProperty("report.output.path"), dbTransactions, csvTransactions, deltas);

            // new SftpService(config).uploadCsv(config.getProperty("report.output.path"), "/upload/report.html");
            System.out.println("Reconciliation complete. Report generated.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
