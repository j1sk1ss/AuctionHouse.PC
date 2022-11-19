package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.ItemWorker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class ShopWindow implements Listener, IWindow<ShopWindow> {
    public ShopWindow(Integer size, String name, Shop shopList, Plugin plugin) {
        shopWindow = Bukkit.createInventory(null, size, name);
        this.shopList = shopList;
        this.plugin = plugin;
    }
    private final Plugin plugin;
    private final Inventory shopWindow;
    private final Shop shopList;
    final Integer PageCapacity = 45;
    int tasked = 9;
    @Override
    public void ShowWindow(Integer window, Player player, Boolean open) {
        if (open) player.openInventory(shopWindow);

        FillWindow(window * PageCapacity, window);

        Bukkit.getServer().getScheduler().cancelTask(tasked);
        tasked = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
            public void run() {
                FillWindow(window * PageCapacity, window);
            }
        }, 1L, 50L);
        player.sendMessage(tasked + "");
    }
    public void FillWindow(Integer startIndex, Integer indexWindow) {

        shopWindow.clear();
        ItemWorker itemWorker = new ItemWorker();

        for (int i = startIndex; i < PageCapacity * (indexWindow + 1); i++) {

            if (shopList.shopList.size() <= i) break;

            Item chosenItem = shopList.shopList.get(i);
            ItemStack tempItem = chosenItem.Item;
            tempItem = itemWorker.SetLore(tempItem,
                    "Цена: " + chosenItem.Price + "₽" +
                    "\nЦена за еденицу: " + (chosenItem.Price/chosenItem.Item.getAmount())+ "₽" +
                    "\nВладелец: " + chosenItem.Owner.getName() +
                    "\nСрок: " + chosenItem.expiredDelay + " тик");

            shopWindow.setItem(i - startIndex, tempItem);
        }

        ItemStack itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_BOAT), indexWindow - 1 + "");
        shopWindow.setItem(45, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_BOAT), indexWindow + 1 + "");
        shopWindow.setItem(53, itemStack);
    }
    @Override
    public ShopWindow GetWindow() {
        return this;
    }
}
