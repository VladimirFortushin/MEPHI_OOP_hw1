package ru.mephi.util;

import lombok.Getter;
import ru.mephi.entity.User;
import ru.mephi.entity.Wallet;
import ru.mephi.entity.Wallets;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataManager {
    //    Все данные должны храниться в памяти приложения. ✅
    @Getter
    private static final Set<User> userSet = new HashSet<>();
    @Getter
    private static Map<User, Wallet> userWalletMap = new HashMap<>();

    static {
        userSet.add(new User("Joshua","123"));
        userSet.add(new User("XÆA-12","456"));
        userSet.add(new User("Jeremiah","789"));
        userSet.add(new User("admin","admin"));
    }


}
