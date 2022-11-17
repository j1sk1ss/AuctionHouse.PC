package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ApproveWindow {

    public ApproveWindow(XConomyAPI xConomyAPI, MoneyTransfer moneyTransfer, Shop shopList, Inventory shopWindow, ItemStack chosenItem,
    Player player) {
        this.xConomyAPI = xConomyAPI;
        this.moneyTransfer = moneyTransfer;
        this.shopList = shopList;
        this.shopWindow = shopWindow;
        this.chosenItem = chosenItem;

        approveWindow = Bukkit.createInventory(null, 9, "Покупка");
        FillApprove();
        player.openInventory(approveWindow);
    }
    private final Inventory approveWindow;
    private final XConomyAPI xConomyAPI;
    private final MoneyTransfer moneyTransfer;
    private final Shop shopList;
    private final Inventory shopWindow;
    private final ItemStack chosenItem;

    private void FillApprove() {
        approveWindow.setItem(0, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        approveWindow.setItem(1, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        approveWindow.setItem(2, new ItemStack(Material.RED_STAINED_GLASS_PANE));

        approveWindow.setItem(4, chosenItem);

        approveWindow.setItem(6, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
        approveWindow.setItem(7, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
        approveWindow.setItem(8, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
    }
}
