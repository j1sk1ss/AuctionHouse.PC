package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;


public class ShopWindow implements Listener, IWindow {


    public ShopWindow(Integer size, String name, MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI, Shop shopList) {
        this.xConomyAPI = xConomyAPI;
        this.moneyTransfer = moneyTransfer;
        this.name = name;
        shopWindow = Bukkit.createInventory(null, size, name);
        this.shopList = shopList;
    }


    private final String name;
    private final Inventory shopWindow;
    private Shop shopList;
    private final MoneyTransfer moneyTransfer;
    private final XConomyAPI xConomyAPI;


    public void ShowWindow(Integer window, Player player) {
        shopWindow.clear();
        FillWindow(window * shopWindow.getSize());
        player.openInventory(shopWindow);
    }

    private void FillWindow(Integer startIndex) {
        int count = Math.min(shopWindow.getSize(), shopList.shopList.size() - startIndex);

        for (int i = startIndex; i < count; i++) {
            shopWindow.setItem(i, shopList.shopList.get(i).Item);
        }
    }
}
