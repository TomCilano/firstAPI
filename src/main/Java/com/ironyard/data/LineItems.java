package com.ironyard.data;

/**
 * Created by Tom on 9/29/16.
 */
public class LineItems {
    private String description;
    private String category;
    private double budgetedAmount;
    private double totalAmount;
    private int id;

    public LineItems() {
    }

    public String getDescription() {return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBudgetedAmount() {
        return budgetedAmount;
    }

    public void setBudgetedAmount(double budgetedAmount) {
        this.budgetedAmount = budgetedAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

