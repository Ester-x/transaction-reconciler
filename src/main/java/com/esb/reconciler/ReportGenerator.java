package com.esb.reconciler;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import com.esb.reconciler.model.Transaction;

public class ReportGenerator {

    public static void generate(String path, List<Transaction> dbList, List<Transaction> csvList, Map<String, List<Transaction>> deltas) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Transaction Report</title></head><body>");
        html.append("<h1>Transaction Reconciliation Report</h1>");
        html.append("<p>Total DB Transactions: ").append(dbList.size()).append("</p>");
        html.append("<p>Total CSV Transactions: ").append(csvList.size()).append("</p>");

        html.append("<h2>Only in Database</h2><ul>");
        for (Transaction t : deltas.get("onlyInDb")) {
            html.append("<li>").append(t.getId()).append(" | ").append(t.getDate()).append(" | ").append(t.getAmount()).append("</li>");
        }
        html.append("</ul><h2>Only in CSV</h2><ul>");
        for (Transaction t : deltas.get("onlyInCsv")) {
            html.append("<li>").append(t.getId()).append(" | ").append(t.getDate()).append(" | ").append(t.getAmount()).append("</li>");
        }
        html.append("</ul><h2>Mismatched Records</h2><ul>");
        for (Transaction t : deltas.get("mismatched")) {
            html.append("<li>").append(t.getId()).append(" | ").append(t.getDate()).append(" | ").append(t.getAmount()).append("</li>");
        }
        html.append("</ul></body></html>");

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(html.toString());
        }
    }
}
