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
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopWindow implements Listener, IWindow<ShopWindow> {
    public ShopWindow(Integer size, String name, Shop shopList, Plugin plugin) {
        shopWindow = Bukkit.createInventory(null, size, name);
        this.shopList = shopList.shopList;
        this.plugin = plugin;
    }
    private final Plugin plugin;
    private final Inventory shopWindow;
    public List<Item> shopList;
    final Integer PageCapacity = 45;
    int tasked = 9;
    public void TimeSort(Boolean Biggest) {
        int temp = Biggest ? 1 : -1;
        List<Item> tempList = new ArrayList<Item>(shopList);
        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < tempList.size() - 1; j++) {
                if (tempList.get(j).expiredDelay * temp < tempList.get(j+1).expiredDelay * temp) {
                    Item tempItem = tempList.get(j);
                    tempList.set(j, tempList.get(j+1));
                    tempList.set(j + 1, tempItem);
                }
            }
        }
        shopList = new ArrayList<Item>(tempList);
    }
    public void PriceSort(Boolean Biggest) {
        int temp = Biggest ? 1 : -1;
        List<Item> tempList = new ArrayList<Item>(shopList);
        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < tempList.size() - 1; j++) {
                if (tempList.get(j).Price * temp < tempList.get(j+1).Price * temp) {
                    Item tempItem = tempList.get(j);
                    tempList.set(j, tempList.get(j+1));
                    tempList.set(j + 1, tempItem);
                }
            }
        }
        shopList = new ArrayList<Item>(tempList);
    }
    @Override
    public void ShowWindow(Integer window, Player player, Boolean open) {
        if (open) player.openInventory(shopWindow);

        FillWindow(window * PageCapacity, window);
        Bukkit.getServer().getScheduler().cancelTask(tasked);
        tasked = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
            public void run() {
                FillWindow(window * PageCapacity, window);
            }
        }, 1L, 1L);
    }
    public void FillWindow(Integer startIndex, Integer indexWindow) {
        shopWindow.clear();
        ItemWorker itemWorker = new ItemWorker();

        for (int i = startIndex; i < PageCapacity * (indexWindow + 1); i++) {
            if (shopList.size() <= i) break;
            Item chosenItem = shopList.get(i);
            ItemStack tempItem = chosenItem.Item;
            tempItem = itemWorker.SetLore(tempItem,
                    "Цена: " + chosenItem.Price + "₽" +
                    "\nЦена за еденицу: " + String.format("%.3f",(chosenItem.Price/chosenItem.Item.getAmount()))+ "₽" +
                    "\nВладелец: " + chosenItem.Owner.getName() +
                    "\nСрок: " + chosenItem.expiredDelay + " тик");
            shopWindow.setItem(i - startIndex, tempItem);
        }
        List<Integer> positions = Arrays.asList(45, 53);
        new InterfaceGenerator().SetUserInterface(shopWindow, itemWorker, indexWindow, positions);
    }
    @Override
    public ShopWindow GetWindow() {return this;}
}
