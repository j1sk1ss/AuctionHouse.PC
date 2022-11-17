package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ShopWindow {

    public ShopWindow(Integer size, String name, MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI) {
        this.xConomyAPI = xConomyAPI;
        this.moneyTransfer = moneyTransfer;
        this.name = name;
        shopWindow = Bukkit.createInventory(null, size, name);
    }
    String name;
    Inventory shopWindow;
    Shop shopList;
    MoneyTransfer moneyTransfer;
    XConomyAPI xConomyAPI;
    public void ShowWindow(Integer window, Player player) {
        //if (shopList.shopList.size() < window * shopWindow.getSize()) return;
        player.closeInventory();

        player.openInventory(shopWindow);
        FillWindow(window * shopWindow.getSize());
    }
    private void FillWindow(Integer startIndex) {
        int count = Math.min(shopWindow.getSize(), shopList.shopList.size() - startIndex);

        for (int i = startIndex; i < count; i++) {
            shopWindow.addItem(shopList.shopList.get(i).Item);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory() != null) {
                if (event.getView().getTitle().equals(name)) {
                    if (event.isLeftClick()) {
                        int itemPosition = event.getSlot();
                        moneyTransfer.BuyItem(getPlayerData(player.getName()), shopList.shopList.get(itemPosition).UniqId);
                    }
                }
            }
        }
    }
    private PlayerData getPlayerData(String playerName) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
