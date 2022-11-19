package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.lists.Expired;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.ItemWorker;
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
    public List<Item> expiredItems;
    private final Inventory expiredWindow;
    private final Integer PageCapacity = 45;
    public void TimeSort(boolean Biggest) {
        int temp = Biggest ? 1 : -1;
        List<Item> tempList = new ArrayList<Item>(shop.PlayerExpired(playerData).expiredItems);
        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < tempList.size() - 1; j++) {
                if (tempList.get(j).expiredDelay * temp < tempList.get(j+1).expiredDelay * temp) {
                    Item tempItem = tempList.get(j);
                    tempList.set(j, tempList.get(j+1));
                    tempList.set(j + 1, tempItem);
                }
            }
        }
        shop.PlayerExpired(playerData).expiredItems = new ArrayList<Item>(tempList);
        expiredItems = new ArrayList<Item>(tempList);
    }
    public void PriceSort(boolean Biggest) {
        int temp = Biggest ? 1 : -1;
        List<Item> tempList = new ArrayList<Item>(shop.PlayerExpired(playerData).expiredItems);
        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < tempList.size() - 1; j++) {
                if (tempList.get(j).Price * temp < tempList.get(j+1).Price * temp) {
                    Item tempItem = tempList.get(j);
                    tempList.set(j, tempList.get(j+1));
                    tempList.set(j + 1, tempItem);
                }
            }
        }
        shop.PlayerExpired(playerData).expiredItems = new ArrayList<Item>(tempList);
        expiredItems = new ArrayList<Item>(tempList);
    }
    @Override
    public void ShowWindow(Integer window, Player player, Boolean open) {
        expiredWindow.clear();
        FillWindow(window * PageCapacity, window);
        if (open) player.openInventory(expiredWindow);
    }
    private void FillWindow(Integer startIndex, Integer indexWindow) {
        ItemWorker itemWorker = new ItemWorker();
        List<Integer> positions = Arrays.asList(45, 53);
        new InterfaceGenerator().SetUserInterface(expiredWindow, itemWorker, indexWindow, positions);

        for (int i = startIndex; i < PageCapacity * (indexWindow + 1); i++) {
            if (shop.PlayerExpired(playerData).expiredItems.size() <= i) break;
            Item chosenItem = shop.PlayerExpired(playerData).expiredItems.get(i);
            ItemStack tempItem = itemWorker.SetLore(chosenItem.Item, "Цена: " + chosenItem.Price);
            expiredWindow.setItem(i - startIndex, tempItem);
        }
    }
    public void TakeItem(int position) {
        shop.PlayerExpired(playerData).expiredItems.remove(position);
        expiredItems = shop.PlayerExpired(playerData).expiredItems;
    }
    @Override
    public ExpiredWindow GetWindow() {
        return this;
    }
}
