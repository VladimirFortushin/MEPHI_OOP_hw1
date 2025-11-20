package ru.mephi;

import ru.mephi.entity.User;
import ru.mephi.entity.Wallet;
import ru.mephi.entity.Wallets;
import ru.mephi.util.DataManager;
import ru.mephi.util.FileManager;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static User tempUser;
    private static Wallet tempWallet;
    private static Scanner scanner = new Scanner(System.in);
    private static Map<Integer, String> categories = new HashMap<>();

    /*

    Хранение данных
    Все данные должны храниться в памяти приложения.✅

    Авторизация пользователей
    Реализовать функциональность для авторизации пользователей по логину и паролю. Приложение должно поддерживать несколько пользователей.✅

    Функционал управления финансами
    Разработать логику для добавления доходов и расходов.✅ Пользователь должен иметь возможность создавать категории для планирования бюджета.✅
    Предусмотреть функциональность для установления бюджета на каждую категорию расходов. ✅

    Работа с кошельком пользователя
    Привязать кошелёк к авторизованному пользователю. ✅ Кошелёк должен хранить информацию о текущих финансах и всех операциях (доходах и расходах).✅
    Сохранять установленный бюджет по категориям.✅
асходов, а также данных по каждой категории.✅
    Выводить информацию о текущем состоянии бюджета для каждой категории, а также оставшийся лимит.✅
    Поддерживать вывод информации в терминал или в файл. (терминал ✅)

            Вывод информации
    Реализовать возможность отображения общей суммы доходов и р✅
    Подсчет расходов и доходов✅
    Разработать методы, подсчитывающие общие расходы и доходы, а также по категориям.✅
    Поддержать возможность подсчета по нескольким выбранным категориям. ✅Если категория не найдена, уведомлять пользователя.✅

    Проверка вводимых данных
    Валидация пользовательского ввода и уведомление о некорректных данных.✅

    Оповещения
    Оповещать пользователя, если превышен лимит бюджета по категории или расходы превысили доходы.✅

            Сохранение данных
    При выходе из приложения сохранять данные кошелька пользователя в файл.✅
    При авторизации загружать данные кошелька из файла.✅

    Чтение команд пользователя в цикле
    Реализовать цикл для постоянного чтения команд пользователя. Поддержать возможность выхода из приложения.✅
*/
    public static void main(String[] args) {
        tempUser = authUser();
        setWallet();
        setCategories();
    }
    //При авторизации загружать данные кошелька из файла.✅
    private static void setWallet() {
        Wallets wallets = FileManager.getWalletsFromFile();
        if(wallets != null){
            tempWallet = getUserWallet(wallets, tempUser);
            if(tempWallet == null){
                tempWallet = new Wallet();
                tempWallet.setUser(tempUser);
            }else{
                System.out.println(tempWallet);
            }
        }else{
            tempWallet = new Wallet();
            tempWallet.setUser(tempUser);
        }
    }

    private static void setCategories() {
        //Пользователь должен иметь возможность создавать категории для планирования бюджета.✅
        //    Предусмотреть функциональность для установления бюджета на каждую категорию расходов. ✅
        //Привязать кошелёк к авторизованному пользователю. ✅ Кошелёк должен хранить информацию о текущих финансах и всех операциях (доходах и расходах).✅
        //    Сохранять установленный бюджет по категориям.✅

        System.out.print("Спланируйте бюджет, создайте категории (прим. Развлечения:30000): ");
        getInput("Бюджет", "Развлечения:30000");
        //Разработать логику для добавления доходов и расходов.✅
        System.out.print("Отразите доходы (прим. Зарплата:80000): ");
        getInput("Доход", "Зарплата:80000");
        //Разработать логику для добавления доходов и расходов.✅
        System.out.print("Отразите расходы (прим. Кафе:1500): ");
        getInput("Расход", "прим. Кафе:1500");
        FileManager.saveWalletToFile(tempWallet);
        System.out.print("Вывести инфо по каким-то отдельным категориям? (прим. 1,2,3,4,7):\n" + printCategories());
        getInput("Отдельные категории", "прим. 1,2,3,4,7");
    }


    private static void getInput(String command, String example) {
        //Реализовать цикл для постоянного чтения команд пользователя. Поддержать возможность выхода из приложения.✅
        while (true) {
            String input = scanner.nextLine().trim();
            checkInput(input, command, example);

            if (input.isBlank()) {
                if (command.equals("Расход")) {
                    System.out.println("Кошелек пользователя:\n" + tempWallet);
                }
                return;
            }

            if (input.equals("exit")) {
                System.exit(0);
            }

            if (input.contains(":")) {
                String[] split = input.split(":");
                if (split.length == 2) {
                    fillWallet(command, split);
                    System.out.println("Добавлен " + command.toLowerCase() + ": " + split[0] + " " + split[1]);
                }
            }

            if (command.equals("Отдельные категории")) {
                String[] split = input.split(",");
                printBudgetPlan(split);
            }


        }
    }
    //Валидация пользовательского ввода и уведомление о некорректных данных.✅
    private static void checkInput(String input, String command, String example) {
        if(!input.contains(":") && !input.contains(",") && !input.equals("exit") && !input.isBlank()) {
            if(command.equals("Отдельные категории")){return;}
            System.out.println("Для раздела \"" + command + "\" используйте формат ввода на примере: " + example + "\n");
            getInput(command, example);
        }
    }

    //Поддержать возможность подсчета по нескольким выбранным категориям. Если категория не найдена, уведомлять пользователя.✅
    private static void printBudgetPlan(String[] split) {
        StringBuilder result = new StringBuilder();
        double total = 0.0;
        for (String numberStr : split) {
            int number = Integer.parseInt(numberStr);
            if (categories.get(number) == null){
                result.append(number).append(" категория не найдена").append("\n");
            }else {
                result.append(categories.get(number)).append(": ")
                        .append(tempWallet.getBudgetPlan().get(categories.get(number))[0])
                        .append(" Оставшийся бюджет: ").append(tempWallet.getBudgetPlan().get(categories.get(number))[1]).append("\n");
                total += tempWallet.getBudgetPlan().get(categories.get(number))[1];
            }
        }
        result.insert(0, "Остаток бюджета по категориям: " + total + "\n");
        System.out.println(result);

    }

    private static void fillWallet(String command, String[] split) {
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
                if (plan != null) {
                    plan[1] -= sum;
                    tempWallet.getBudgetPlan().put(name, plan);
                    checkBudgetPlanBalance(name, plan[1]);
                }
                //Оповещать пользователя, если превышен лимит бюджета по категории или расходы превысили доходы.
                checkBalance();
            }
        }

    }



    //Оповещения
    //Оповещать пользователя, если превышен лимит бюджета по категории или расходы превысили доходы.
    private static void checkBalance() {
        //расходы превысили доходы ✅
        if(tempWallet.getBalance() < 0){
            System.out.println("Расходы превысили доходы на " + (tempWallet.getBalance() * -1));
        }
    }
    //Оповещать пользователя, если превышен лимит бюджета по категории✅
    private static void checkBudgetPlanBalance(String name, Double plan) {
        if(plan < 0){
            System.out.println("Превышен лимит бюджета по категории " + name + " на " + (plan * -1));
        }
    }

    //Реализовать функциональность для авторизации пользователей по логину и паролю. Приложение должно поддерживать несколько пользователей.✅
    private static User authUser() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        Optional<User> dbUser = DataStorageUtil.getUserSet().stream().filter(user -> user.getPassword().equals(password) && user.getLogin().equals(username)).findFirst();

        if (DataStorageUtil.getUserSet().contains(tempUser) || dbUser.isEmpty()) {
            System.out.println("Неверное имя пользователя или пароль");
            System.exit(-1);
        }

        return dbUser.get();
    }

    //Поддержать возможность подсчета по нескольким выбранным категориям. Если категория не найдена, уведомлять пользователя.✅
    private static void getCategories() {
        AtomicInteger counter = new AtomicInteger();
        Set<String> set = new HashSet<>();
        tempWallet.getBudgetPlan().forEach((category, y) -> set.add(category));
        set.forEach(category -> categories.put(counter.incrementAndGet(), category));
    }

    //Поддержать возможность подсчета по нескольким выбранным категориям. Если категория не найдена, уведомлять пользователя.✅
    private static String printCategories() {
        StringBuilder result = new StringBuilder();
        getCategories();
        categories.forEach((number, category) -> {
            result.append(number).append(": ").append(category).append("\n");
        });
        return result.toString();
    }

    private static Wallet getUserWallet(Wallets wallets, User user) {
        return wallets.getWallets().stream().filter(wallet -> wallet.getUser().equals(user)).findFirst().orElse(null);
    }
}
