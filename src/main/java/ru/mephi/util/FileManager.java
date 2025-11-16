package ru.mephi.util;

import ru.mephi.entity.User;
import ru.mephi.entity.Wallet;
import ru.mephi.entity.Wallets;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;

public class FileManager {
    private static final Path path = Path.of("src/main/resources/txt.txt");
    //При выходе из приложения сохранять данные кошелька пользователя в файл.✅

    public static void saveWalletToFile(Wallet wallet) {
        try {
            Wallets wallets = getWalletsFromFile();
            if(wallets == null){
                wallets = new Wallets();
                wallets.getWallets().add(wallet);
            }else{
                updateFileWallets(wallets, wallet);
            }
            String walletsString = JsonUtil.pojoToJson(wallets);
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile(), false))) {
                bw.write(walletsString);
            }
            System.out.println("Данные кошелька сохранены в файл");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }
    //    При авторизации загружать данные кошелька из файла.✅
    public static Wallets getWalletsFromFile() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            if(br.ready()){
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }
            Wallets wallets = JsonUtil.jsonToPojo(result.toString(), Wallets.class);
            if(wallets != null){
                System.out.println("Данные кошельков загружены из файла");
            }
            return wallets;
        } catch (Exception e) {
            System.out.println("Ошибка чтения кошельков из файла: " + e.getMessage());
            return null;
        }
    }

    private static void updateFileWallets(Wallets wallets, Wallet updatedWallet) {
        User currentUser = updatedWallet.getUser();
        wallets.getWallets().stream()
                .filter(w -> w.getUser().equals(currentUser))
                .findFirst()
                .ifPresent(existing -> {
                    updateFileWallet(existing, updatedWallet);
                });
    }

    private static void updateFileWallet(Wallet fileWallet, Wallet currentWallet) {
        fileWallet.setBalance(currentWallet.getBalance() +  fileWallet.getBalance());
        updateMoney(fileWallet.getIncome(), currentWallet.getIncome());
        updateMoney(fileWallet.getOutcome(), currentWallet.getOutcome());
        updateBudgetPlan(fileWallet.getBudgetPlan(), currentWallet.getBudgetPlan());
    }

    private static void updateMoney(Map<String, Double> fileMoney, Map<String, Double> currentMoney) {
        currentMoney.forEach(
                (key, value) -> fileMoney.merge(key, value, Double::sum)
        );
    }

    private static void updateBudgetPlan(Map<String, Double[]> filePlan, Map<String, Double[]> currentPlan) {
        currentPlan.forEach((key, newArr) -> {
            filePlan.merge(key, newArr, (oldArr, newArr2) -> {
                int len = Math.min(oldArr.length, newArr2.length);
                Double[] result = new Double[Math.max(oldArr.length, newArr2.length)];

                for (int i = 0; i < len; i++) {
                    result[i] = oldArr[i] + newArr2[i];
                }

                if (oldArr.length > len) {
                    System.arraycopy(oldArr, len, result, len, oldArr.length - len);
                } else if (newArr2.length > len) {
                    System.arraycopy(newArr2, len, result, len, newArr2.length - len);
                }

                return result;
            });
        });
    }

    //{
    //  "wallets": [
    //    {
    //      "user": {
    //        "login": "admin"
    //      },
    //      "balance": 82100.0,
    //      "income": {
    //        "Мама дала": 10000.0,
    //        "Зарплата": 80000.0
    //      },
    //      "outcome": {
    //        "Кино": 1400.0,
    //        "Еда": 6500.0
    //      },
    //      "budgetPlan": {
    //        "Кино": [
    //          10000.0,
    //          10000.0
    //        ],
    //        "Еда": [
    //          10000.0,
    //          7000.0
    //        ]
    //      }
    //    }
    //  ]
    //}




}
