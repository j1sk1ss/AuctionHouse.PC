package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.ItemWorker;
import me.js.auc.auctionhouse.scripts.Sorting;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpiredWindow implements IWindow<ExpiredWindow> {
    public ExpiredWindow(Integer size, String name, Shop shop, PlayerData playerData) {
        this.playerData = playerData;
        this.shop = shop;
        expiredItems = shop.PlayerExpired(playerData).expiredItems;
        expiredWindow = Bukkit.createInventory(null, size, name);
    }

    private final PlayerData playerData;
    private final Shop shop;
    private List<Item> expiredItems;
    private final Inventory expiredWindow;
    private final Integer PageCapacity = 45;

    public void TimeSort() {
        shop.PlayerExpired(playerData).expiredItems = new ArrayList<Item>(
                new Sorting().TimeSort(expiredItems)
        );
        UpdateLocalList();
    }

    public void PriceSort(boolean Biggest) {
        shop.PlayerExpired(playerData).expiredItems = new ArrayList<Item>(
                new Sorting().PriceSort(Biggest, expiredItems)
        );
        UpdateLocalList();
    }

    @Override
    public void ShowWindow(Integer window, Player player, Boolean open) {
        expiredWindow.clear();
        FillWindow(window * PageCapacity, window);
        if (open) player.openInventory(expiredWindow);
    }

    private void FillWindow(int startIndex, int indexWindow) {
        var itemWorker = new ItemWorker();
        var positions = Arrays.asList(45, 53);
        new InterfaceGenerator().SetUserInterface(expiredWindow, itemWorker, indexWindow, positions);

        for (int i = startIndex; i < PageCapacity * (indexWindow + 1); i++) {
            if (shop.PlayerExpired(playerData).expiredItems.size() <= i) break;
            Item chosenItem = shop.PlayerExpired(playerData).expiredItems.get(i);
            ItemStack tempItem = itemWorker.SetLore(chosenItem.Item, "Цена: " + chosenItem.Price);
            expiredWindow.setItem(i - startIndex, tempItem);
        }
    }

    private void UpdateLocalList() {
        expiredItems = shop.PlayerExpired(playerData).expiredItems;
    }

    public void TakeItem(int position) {
        shop.PlayerExpired(playerData).expiredItems.remove(position);
        UpdateLocalList();
    }

    @Override
    public ExpiredWindow GetWindow() {
        return this;
    }
}
