package me.js.auc.auctionhouse.object;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.UUID;

public class Item implements Serializable {
    public Item(ItemStack itemStack, ItemMeta itemMeta, Double price, PlayerData ownerData) {
        OwnerData = ownerData;
        Item      = itemStack;
        ItemMeta  = itemMeta;
        Price     = price;
        UniqId    = UUID.randomUUID();
    }
    public Integer expiredDelay = 10000;
    public ItemStack Item;
    public ItemMeta ItemMeta;
    public Double Price;
    public PlayerData OwnerData;
    public UUID UniqId;
}
