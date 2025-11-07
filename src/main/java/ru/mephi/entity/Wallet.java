package ru.mephi.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Wallet {
    private User user;
    private double balance;
    private Map<String, Double> income;
    private Map<String, Double> outcome;
    private Map<String, Double[]> budgetPlan;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователь: ").append(user).append(",\n");
        sb.append("Общий баланс: ").append(balance).append(",\n");
        sb.append("Доходы:\n");

        for (Map.Entry<String, Double> entry : income.entrySet()) {
            sb.append("  ").append(entry.getKey())
                    .append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("Расходы:\n");

        for (Map.Entry<String, Double> entry : outcome.entrySet()) {
            sb.append("  ").append(entry.getKey())
                    .append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("Бюджетный план:\n");

        for (Map.Entry<String, Double[]> entry : budgetPlan.entrySet()) {
            sb.append("  ").append(entry.getKey())
                    .append(": ").append(entry.getValue()[0])
            .append(" остаток: ").append(entry.getValue()[1]).append("\n");
        }

        return sb.toString();
    }

    public Wallet(){
        this.income = new HashMap<>();
        this.outcome = new HashMap<>();
        this.budgetPlan = new HashMap<>();
    }
}
