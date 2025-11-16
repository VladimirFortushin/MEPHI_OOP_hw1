package ru.mephi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Wallet {
    @JsonProperty(value = "user")
    private User user;
    @JsonProperty(value = "balance")
    private double balance;
    @JsonProperty(value = "income")
    private Map<String, Double> income;
    @JsonProperty(value = "expense")
    private Map<String, Double> outcome;
    @JsonProperty(value = "budget_plan")
    private Map<String, Double[]> budgetPlan;


    @Override
    public String toString() {
        double in = 0.0;
        double out = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователь: ").append(user).append(",\n");
        sb.append("Баланс: ").append(balance).append(",\n");
        for (Map.Entry<String, Double> entry : income.entrySet()) {
            in += entry.getValue();
        }
        sb.append("Общий доход: ").append(in).append(",\n");


        sb.append("Доходы по категориям:\n");

        for (Map.Entry<String, Double> entry : income.entrySet()) {
            sb.append("  ").append(entry.getKey())
                    .append(": ").append(entry.getValue()).append("\n");
        }
        for (Map.Entry<String, Double> entry : outcome.entrySet()) {
            out += entry.getValue();
        }
        sb.append("Общие расходы: ").append(out).append(",\n");


        sb.append("Расходы:\n");

        for (Map.Entry<String, Double> entry : outcome.entrySet()) {
            sb.append("  ").append(entry.getKey())
                    .append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("Бюджет по категориям:\n");

        for (Map.Entry<String, Double[]> entry : budgetPlan.entrySet()) {
            sb.append("  ").append(entry.getKey())
                    .append(": ").append(entry.getValue()[0])
                    .append(" Оставшийся бюджет: ").append(entry.getValue()[1]).append("\n");
        }

        return sb.toString();
    }

    public Wallet(){
        this.income = new HashMap<>();
        this.outcome = new HashMap<>();
        this.budgetPlan = new HashMap<>();
    }
}
