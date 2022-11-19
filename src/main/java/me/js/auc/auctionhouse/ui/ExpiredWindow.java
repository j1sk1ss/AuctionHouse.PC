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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ExpiredWindow implements IWindow<ExpiredWindow> {
    public ExpiredWindow(Integer size, String name, Shop shop, PlayerData playerData) {
        this.playerData = playerData;
        this.shop = shop;
        expiredWindow = Bukkit.createInventory(null, size, name);
    }
    private final PlayerData playerData;
    private final Shop shop;
    private final Inventory expiredWindow;
    final Integer PageCapacity = 45;
    @Override
    public void ShowWindow(Integer window, Player player, Boolean open) {
        expiredWindow.clear();
        FillWindow(window * PageCapacity, window);
        if (open) player.openInventory(expiredWindow);
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

        int count = Math.min(itemsOnPage, PageCapacity);

        for (int i = startIndex; i < PageCapacity * (indexWindow + 1); i++) {

            if (expired.expiredList.size() <= i) break;

            Item chosenItem = expired.expiredList.get(i);
            ItemStack tempItem = chosenItem.Item;
            tempItem = itemWorker.SetLore(tempItem, "Цена: " + chosenItem.Price);

            expiredWindow.setItem(i - startIndex, tempItem);
        }
    }
    public void TakeItem(Integer position) {expiredWindow.setItem(position, null);}
    private Expired getExpired() {
        for (Expired item : shop.expireds) {
            if (item.Owner.equals(playerData)) return item;
        }
        return null;
    }
    @Override
    public ExpiredWindow GetWindow() {
        return this;
    }
}
