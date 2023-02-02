package me.js.auc.auctionhouse.lists;

import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.data.syncdata.PlayerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Expired implements Serializable { // Обьект просрочки
    public Expired(PlayerData owner) {
        Owner = owner;
        expiredItems = new ArrayList<>();
    }
    public PlayerData Owner; // Владелец обьекта просрочки
    public List<Item> expiredItems; // Лист предметов просрочки
}
