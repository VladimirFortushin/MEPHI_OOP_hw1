package ru.mephi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class Wallets {
    @JsonProperty(value = "wallets")
    private List<Wallet> wallets;

    public Wallets (){
        this.wallets = new ArrayList<>();
    }
}
