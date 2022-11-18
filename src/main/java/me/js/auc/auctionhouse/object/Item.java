package me.js.auc.auctionhouse.object;
import com.google.common.primitives.UnsignedInteger;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class Item {
    public Item(ItemStack itemStack, Double price, PlayerData owner) {
        Owner = owner;
        Item = itemStack;
        Price = price;
        UniqId = UUID.randomUUID();
    }
    public Integer ticks = 1000;
    public Date expiredDate;
    public ItemStack Item;
    public Double Price;
    public PlayerData Owner;
    public UUID UniqId;
}
