package me.js.auc.auctionhouse.object;

import me.js.auc.auctionhouse.AuctionHouse;
import me.yic.xconomy.data.syncdata.PlayerData;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.UUID;

public class Item implements Serializable {
    public Item(ItemStack itemStack, ItemMeta itemMeta, Double price, PlayerData ownerData) {
        OwnerData  = ownerData;
        Item       = itemStack;
        ItemMeta   = itemMeta;
        Price      = price;
        UniqId     = UUID.randomUUID();
    }
    public Integer expiredDelay = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
            getInt("auction.max_duration");

    public ItemStack Item;
    public ItemMeta ItemMeta;
    public Double Price;
    public PlayerData OwnerData;
    public UUID UniqId;
}
