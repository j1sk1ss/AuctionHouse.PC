package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.lists.Expired;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.ItemWorker;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ExpiredWindow implements IWindow {
    public ExpiredWindow(Integer size, String name, Shop shop, PlayerData playerData) {
        this.playerData = playerData;
        this.shop = shop;
        expiredWindow = Bukkit.createInventory(null, size, name);
    }
    private final PlayerData playerData;
    private final Shop shop;
    private final Inventory expiredWindow;
    @Override
    public void ShowWindow(Integer window, Player player) {
        expiredWindow.clear();
        FillWindow(window * expiredWindow.getSize(), window);
        player.openInventory(expiredWindow);
    }
    @Override
    public ShopWindow GetShopWindow() {
        return null;
    }
    @Override
    public ExpiredWindow GetExpiredWindow() {
        return this;
    }
    private void FillWindow(Integer startIndex, Integer indexWindow) {
        ItemWorker itemWorker = new ItemWorker();

        Expired expired = getExpired();

        ItemStack itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_BOAT), indexWindow - 1 + "");
        expiredWindow.setItem(45, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_BOAT), indexWindow + 1 + "");
        expiredWindow.setItem(53, itemStack);

        if (expired == null) return;

        int itemsOnPage = Math.min(expiredWindow.getSize(), expired.expiredList.size() - startIndex);

        final int PageCapacity = 41;
        int count = Math.min(itemsOnPage, PageCapacity);

        for (int i = startIndex; i < count; i++) {
            Item chosenItem = expired.expiredList.get(i);
            ItemStack tempItem = chosenItem.Item;

            tempItem = itemWorker.SetLore(tempItem, "Цена: " + chosenItem.Price);

            expiredWindow.setItem(i, tempItem);
        }
    }

    private Expired getExpired() {
        for (Expired item : shop.expireds) {
            if (item.Owner.equals(playerData)) return item;
        }
        return null;
    }
}
