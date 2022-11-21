package me.js.auc.auctionhouse.object;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Item {
    public Item(ItemStack itemStack, Double price, PlayerData ownerData) {
        OwnerData = ownerData;
        Item = itemStack;
        Price = price;
        UniqId = UUID.randomUUID();
    }
    public Integer expiredDelay = 10000;
    public ItemStack Item;
    public Double Price;
    public PlayerData OwnerData;
    public UUID UniqId;
}
