package me.js.auc.auctionhouse.object;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.Random;


public class Item {
    public Item(ItemStack itemStack, Double price, BigInteger id, PlayerData owner) {
        Owner = owner;
        Item = itemStack;
        Price = price;
        Id = id;
        UniqId = new Random().nextInt(999999999);
    }
    public ItemStack Item;
    public Double Price;
    public BigInteger Id;
    public PlayerData Owner;
    public Integer UniqId;
}
