package me.js.auc.auctionhouse.scripts;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.*;

public class MoneyTransfer {
    public MoneyTransfer(XConomyAPI xConomyAPI) {
        this.xConomyAPI = xConomyAPI;
    }
    private final XConomyAPI xConomyAPI;
    public void SellItem(Double price, PlayerData player, ItemStack item, Shop shop) {
        var sellItem = new Item(item, price, player);
        shop.shopList.add(sellItem);
    }
    public void BuyItem(Player buyer, PlayerData buyerData, Item item, Shop shop) {
        if (buyerData.getBalance().doubleValue() < item.Price && !item.OwnerData.equals(buyerData)) {
            buyer.sendMessage("Недостаточно средств!");
            return;
        }
        shop.shopList.remove(item);
        buyer.getInventory().addItem(new ItemStack(item.Item.getType(), item.Item.getAmount()));
        Objects.requireNonNull(Bukkit.getPlayer(item.OwnerData.getUniqueId())).sendMessage("Продан предмет: "
                + item.Item.getI18NDisplayName() + ". За: " + item.Price + "₽");

        ChangeBalance(buyerData, item.Price, false);
        ChangeBalance(item.OwnerData, item.Price, true);
    }
    private void ChangeBalance(PlayerData player, Double amount, Boolean isAdd) {
        xConomyAPI.changePlayerBalance(player.getUniqueId(), player.getName(), new BigDecimal(amount), isAdd);
    }
}
