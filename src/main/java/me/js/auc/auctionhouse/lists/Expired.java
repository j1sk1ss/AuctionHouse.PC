package me.js.auc.auctionhouse.lists;

import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.data.syncdata.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class Expired {
    public Expired(PlayerData owner) {
        Owner = owner;
        expiredItems = new ArrayList<Item>();
    }
    public PlayerData Owner;
    public List<Item> expiredItems;
}
