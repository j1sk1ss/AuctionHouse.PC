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
        TransactionSyncing();
    }
    private List<Item> shopList;
    private final Plugin plugin;
    private final Inventory shopWindow;
    private final Integer PageCapacity = 45;
    private int tasked = 9;

    public Shop shop;
    public void TimeSort() { shopList = new Sorting().TimeSort(shop.shopList); }
    public void PriceSort(boolean Biggest) { shopList = new Sorting().PriceSort(Biggest, shop.shopList); }
    public void TransactionSyncing() { this.shopList = shop.shopList; }
    private final List<Integer> positions = Arrays.asList(45, 53);
    @Override
    public void ShowWindow(Integer window, Player player, Boolean open) {
        if (open) player.openInventory(shopWindow);

        new InterfaceGenerator().SetUserInterface(shopWindow, new ItemWorker(), window, positions);
        FillWindow(window * PageCapacity, window);

        Bukkit.getServer().getScheduler().cancelTask(tasked);
        tasked = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () ->
                FillWindow(window * PageCapacity, window), 0L, 1L);
    }
    public void FillWindow(int startIndex, int indexWindow) {
        if (shopList.size() != shop.shopList.size()) TransactionSyncing();
        shopWindow.clear();

        var itemWorker = new ItemWorker();
        for (int i = startIndex; i < PageCapacity * (indexWindow + 1); i++) {
            if (shopList.size() <= i) break;
            var chosenItem = shopList.get(i);
            var tempItem = chosenItem.Item;
            tempItem = itemWorker.SetLore(tempItem,
                    "????????: " + chosenItem.Price + "???" +
                    "\n???????? ???? ??????????????: " + String.format("%.1f",(chosenItem.Price/chosenItem.Item.getAmount()))+ "???" +
                    "\n????????????????: " + chosenItem.OwnerData.getName() +
                    "\n????????: " + chosenItem.expiredDelay + " ??????");
            shopWindow.setItem(i - startIndex, tempItem);
        }
    }
    @Override
    public ShopWindow GetWindow() {return this;}
}
