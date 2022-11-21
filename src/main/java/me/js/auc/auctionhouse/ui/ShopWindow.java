package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.ItemWorker;

import me.js.auc.auctionhouse.scripts.Sorting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ShopWindow implements Listener, IWindow<ShopWindow> {
    public ShopWindow(Integer size, String name, Shop shop, Plugin plugin) {
        shopWindow = Bukkit.createInventory(null, size, name);
        this.shop = shop;
        this.plugin = plugin;
    }
    private final Plugin plugin;
    private final Inventory shopWindow;
    private final Integer PageCapacity = 45;
    private int tasked = 9;

    public Shop shop;
    public void TimeSort(boolean Biggest) {
        shop.shopList = new Sorting().TimeSort(Biggest, shop.shopList);
    }
    public void PriceSort(boolean Biggest) {
        shop.shopList = new Sorting().PriceSort(Biggest, shop.shopList);
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
    public void FillWindow(int startIndex, int indexWindow) {
        shopWindow.clear();
        ItemWorker itemWorker = new ItemWorker();

        for (int i = startIndex; i < PageCapacity * (indexWindow + 1); i++) {
            if (shop.shopList.size() <= i) break;
            Item chosenItem = shop.shopList.get(i);
            ItemStack tempItem = chosenItem.Item;
            tempItem = itemWorker.SetLore(tempItem,
                    "Цена: " + chosenItem.Price + "₽" +
                    "\nЦена за еденицу: " + String.format("%.1f",(chosenItem.Price/chosenItem.Item.getAmount()))+ "₽" +
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
