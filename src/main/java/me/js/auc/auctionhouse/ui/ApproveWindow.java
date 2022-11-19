package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.scripts.ItemWorker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ApproveWindow implements IWindow<ApproveWindow> {

    public ApproveWindow(ItemStack chosenItem) {
        this.chosenItem = chosenItem;
    }
    private Inventory approveWindow;
    private final ItemStack chosenItem;

    private void FillWindow() {
        ItemWorker itemWorker = new ItemWorker();

        ItemStack tempBlock = itemWorker.SetName(new ItemStack(Material.RED_STAINED_GLASS_PANE), "ОТМЕНА");
        approveWindow.setItem(0, tempBlock);
        approveWindow.setItem(1, tempBlock);
        approveWindow.setItem(2, tempBlock);

        approveWindow.setItem(4, chosenItem);

        tempBlock = itemWorker.SetName(new ItemStack(Material.GREEN_STAINED_GLASS_PANE), "КУПИТЬ");
        approveWindow.setItem(6, tempBlock);
        approveWindow.setItem(7, tempBlock);
        approveWindow.setItem(8, tempBlock);
    }
    public void ShowWindow(Integer window, Player player, Boolean open) {
        approveWindow = Bukkit.createInventory(player, 9, "Покупка");
        FillWindow();
        if (open) player.openInventory(approveWindow);
    }

    @Override
    public ApproveWindow GetWindow() {
        return this;
    }
}
