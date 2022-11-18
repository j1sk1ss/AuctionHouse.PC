package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.ItemWorker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopWindow implements Listener, IWindow {
    public ShopWindow(Integer size, String name, Shop shopList) {
        shopWindow = Bukkit.createInventory(null, size, name);
        this.shopList = shopList;
    }
    private final Inventory shopWindow;
    private final Shop shopList;
    public void ShowWindow(Integer window, Player player) {
        shopWindow.clear();
        FillWindow(window * shopWindow.getSize(), window);
        player.openInventory(shopWindow);
    }
    private void FillWindow(Integer startIndex, Integer indexWindow) {
        ItemWorker itemWorker = new ItemWorker();
        int itemsOnPage = Math.min(shopWindow.getSize(), shopList.shopList.size() - startIndex);

        final int PageCapacity = 18;
        int count = Math.min(itemsOnPage, PageCapacity);
        for (int i = startIndex; i < count; i++) {
            Item chosenItem = shopList.shopList.get(i);
            ItemStack tempItem = chosenItem.Item;
            itemWorker.SetName(tempItem, tempItem.getI18NDisplayName() + " Цена:" +
                    "" + chosenItem.Price + " Владелец:" + chosenItem.Owner.getName());

            shopWindow.setItem(i, shopList.shopList.get(i).Item);
        }

        ItemStack itemStack = new ItemStack(Material.ACACIA_BOAT);
        shopWindow.setItem(18, itemWorker.SetName(itemStack, indexWindow - 1 + ""));
        shopWindow.setItem(19, itemWorker.SetName(itemStack, indexWindow + 1 + ""));
    }
}
