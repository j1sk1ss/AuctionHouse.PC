package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CostSetter {

    public CostSetter(Player player, ItemStack chosenItem) {
        approveWindow = Bukkit.createInventory(null, 27, "Цена");
        this.chosenItem = chosenItem;
        FillCostWindow();
        player.openInventory(approveWindow);
    }
    public CostSetter(XConomyAPI xConomyAPI, MoneyTransfer moneyTransfer, Shop shopList, Inventory shopWindow, ItemStack chosenItem,
                         Player player, double startCost, Item item) {
        this.xConomyAPI = xConomyAPI;
        this.moneyTransfer = moneyTransfer;
        this.shopList = shopList;
        this.shopWindow = shopWindow;
        this.chosenItem = chosenItem;

        this.item = item;

        approveWindow = Bukkit.createInventory(null, 27, "Цена");
        FillCostWindow();
        player.openInventory(approveWindow);
    }
    private Item item;
    private Inventory approveWindow;
    private XConomyAPI xConomyAPI;
    private MoneyTransfer moneyTransfer;
    private Shop shopList;
    private Inventory shopWindow;
    private ItemStack chosenItem;

    public void IncreaseCost(double cost) {
        item.Price += cost;
    }

    public void DecreaseCost(double cost) {
        item.Price -= cost;
    }
    private void FillCostWindow() {
        approveWindow.setItem(9, new ItemStack(Material.RED_STAINED_GLASS_PANE)); // -100
        approveWindow.setItem(10, new ItemStack(Material.RED_STAINED_GLASS_PANE)); // -50
        approveWindow.setItem(11, new ItemStack(Material.RED_STAINED_GLASS_PANE)); // -10

        approveWindow.setItem(13, chosenItem); // sell
        approveWindow.setItem(4, new ItemStack(Material.YELLOW_BANNER)); // 0

        approveWindow.setItem(21, new ItemStack(Material.BLACK_BANNER)); // .01d
        approveWindow.setItem(22, new ItemStack(Material.BLUE_BANNER)); // .1d
        approveWindow.setItem(23, new ItemStack(Material.BROWN_BANNER)); // 1d


        approveWindow.setItem(15, new ItemStack(Material.GREEN_STAINED_GLASS_PANE)); // +10
        approveWindow.setItem(16, new ItemStack(Material.GREEN_STAINED_GLASS_PANE)); // +50
        approveWindow.setItem(17, new ItemStack(Material.GREEN_STAINED_GLASS_PANE)); // +100
    }

}
