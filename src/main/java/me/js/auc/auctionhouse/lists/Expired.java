package me.js.auc.auctionhouse.lists;

import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Expired {
    public Expired(PlayerData owner) {
        Owner = owner;
        expiredList = new ArrayList<Item>();
    }
    public PlayerData Owner;
    public List<Item> expiredList;
}
