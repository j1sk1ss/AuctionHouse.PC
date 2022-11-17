package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.scripts.ItemWorker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ApproveWindow implements IWindow {

    public ApproveWindow(ItemStack chosenItem, Player player) {
        this.player = player;
        this.chosenItem = chosenItem;
    }
    private Inventory approveWindow;
    private final Player player;
    private final ItemStack chosenItem;

    private void FillWindow() {
        ItemWorker itemWorker = new ItemWorker();
        ItemStack tempBlock = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        for (int i = 0; i < 3; i++) approveWindow.setItem(i, itemWorker.SetName(tempBlock, "ОТМЕНА"));

        approveWindow.setItem(4, chosenItem);

        tempBlock = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        for (int i = 6; i < 9; i++) approveWindow.setItem(i, itemWorker.SetName(tempBlock, "КУПИТЬ"));
    }
    public void ShowWindow(Integer window, Player player) {
        approveWindow = Bukkit.createInventory(null, 9, "Покупка");
        FillWindow();
        player.openInventory(approveWindow);
    }
}
