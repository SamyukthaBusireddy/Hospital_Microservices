package com.eda.streams.model;

public class Transaction {
    private String name;
    private Integer amount;
    private String time;

    public Transaction() {}

    public Transaction(String name, Integer amount, String time) {
        this.name = name;
        this.amount = amount;
        this.time = time;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}

