package ru.mephi;

import ru.mephi.entity.User;
import ru.mephi.entity.Wallet;
import ru.mephi.util.DataStorageUtil;
import ru.mephi.util.FileManager;

import java.util.Scanner;

public class Main {

    private static User tempUser;
    private static Wallet tempWallet;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        tempUser = authUser();
        if(!DataStorageUtil.getUserSet().contains(tempUser)) {
            System.out.println("Неверное имя пользователя или пароль");
            System.exit(-1);
        }
        tempWallet = new Wallet();
        tempWallet.setUser(tempUser);
        setCategories();

    }

    private static void setCategories() {
        System.out.print("Спланируйте бюджет (прим. Развлечения:30000): ");
        getInput("Бюджет");
        System.out.print("Отразите доходы (прим. Зарплата:80000): ");
        getInput("Доход");
        System.out.print("Отразите расходы (прим. Кафе:1500): ");
        getInput("Расход");
        FileManager.saveWalletToFile(tempWallet);
    }

    private static void getInput(String command) {
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                if(command.equals("Расход")) {
                    System.out.println("Завершение.");
                    System.out.println("Кошелек пользователя:\n" + tempWallet);
                }
                return;
            }

            if (input.equals("exit")) {
                System.exit(0);
            }

            if(input.contains(":")){
                String[] split = input.split(":");
                if(split.length == 2){
                    fillWallet(command, split);
                    System.out.println("Добавлен " + command.toLowerCase() + ": " + split[0] + " " + split[1]);
                }
            }
        }
    }

    private static void fillWallet(String command, String[] split){
        String name = split[0];
        double sum = Double.parseDouble(split[1]);
        switch (command) {
            case "Бюджет" -> {
                Double[] plan = new Double[2];
                plan[0] = sum;
                plan[1] = sum;
                tempWallet.getBudgetPlan().put(name, plan);
            }
            case "Доход" -> {
                tempWallet.getIncome().put(name, tempWallet.getIncome().getOrDefault(name, 0.0) + sum);
                tempWallet.setBalance(tempWallet.getBalance() + sum);
            }
            case "Расход" -> {
                tempWallet.getOutcome().put(name, tempWallet.getOutcome().getOrDefault(name, 0.0) + sum);
                tempWallet.setBalance(tempWallet.getBalance() - sum);
                Double[] plan = tempWallet.getBudgetPlan().get(name);
                if(plan != null) {
                    plan[1] -= sum;
                    tempWallet.getBudgetPlan().put(name, plan);
                }
            }
        }
    }

    private static User authUser() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        return new User(username, password);
    }
}