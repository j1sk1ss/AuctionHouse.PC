package me.js.auc.auctionhouse.object;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;


public class Item {
    public Item(ItemStack itemStack, Double price, PlayerData owner) {
        Owner = owner;
        Item = itemStack;
        Price = price;
        UniqId = UUID.randomUUID();
    }
    public Integer expiredDelay = 10000;
    public ItemStack Item;
    public Double Price;
    public PlayerData Owner;
    public UUID UniqId;
}
