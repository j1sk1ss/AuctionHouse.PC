package me.js.auc.auctionhouse.object;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;


public class Item {
    public Item(ItemStack itemStack, Double price, BigInteger id, PlayerData owner) {
        Owner = owner;
        Item = itemStack;
        Price = price;
        Id = id;
        UniqId = UUID.randomUUID();
    }
    public ItemStack Item;
    public Double Price;
    public BigInteger Id;
    public PlayerData Owner;
    public UUID UniqId;
}
