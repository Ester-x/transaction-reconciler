package com.esb.reconciler.model;

import java.util.Objects;

public class Transaction {

    private String id;
    private String date;
    private String amount;

    public Transaction(String id, String date, String amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return id.equals(that.id) && date.equals(that.date) && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount);
    }
}
