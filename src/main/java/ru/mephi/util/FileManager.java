package ru.mephi.util;

import ru.mephi.entity.Wallet;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileManager {
    public static void saveWalletToFile(Wallet wallet) {
        Path path = Path.of("src/main/resources/txt.txt");
        try (FileWriter writer = new FileWriter(path.toFile(), false)) {
            writer.write(wallet.toString());
            System.out.println("Данные кошелька сохранены в " + path);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }
}
