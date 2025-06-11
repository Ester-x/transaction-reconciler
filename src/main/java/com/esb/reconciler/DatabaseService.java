package com.esb.reconciler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.esb.reconciler.model.Transaction;

public class DatabaseService {

    private final String url, user, password;

    public DatabaseService(Properties config) {
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");
    }

    public List<Transaction> fetchTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id, date, amount FROM Transactions")) {

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getString("id"),
                        rs.getString("date"),
                        rs.getString("amount")
                ));
            }
        }
        return transactions;
    }
}
