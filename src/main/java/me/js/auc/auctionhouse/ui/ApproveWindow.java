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
    private final ItemStack chosenItem;
    private Inventory approveWindow;
    public void ShowWindow(Integer window, Player player, Boolean open) {
        approveWindow = Bukkit.createInventory(player, 9, "Покупка");
        FillWindow();
        if (open) player.openInventory(approveWindow);
    }
    private void FillWindow() {
        var itemWorker = new ItemWorker();

        var tempBlock = itemWorker.SetName(new ItemStack(Material.RED_STAINED_GLASS_PANE), "ОТМЕНА");
        for (int i = 0; i < 3; i++) approveWindow.setItem(i, tempBlock);

        approveWindow.setItem(4, chosenItem);

        tempBlock = itemWorker.SetName(new ItemStack(Material.GREEN_STAINED_GLASS_PANE), "КУПИТЬ");
        for (int i = 6; i < 9; i++) approveWindow.setItem(i, tempBlock);
    }
    @Override
    public ApproveWindow GetWindow() {
        return this;
    }
    @Override
    public void PriceSort(boolean Biggest) {}
    @Override
    public void TimeSort() {}
}
