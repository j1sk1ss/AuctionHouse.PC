package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.scripts.ItemWorker;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InterfaceGenerator {
    public void SetUserInterface(Inventory window, ItemWorker itemWorker, Integer indexWindow,
    List<Integer> positions) {
        final Integer prevWindow = positions.get(0);
        final Integer nextWindow = positions.get(1);

        var itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_BOAT), indexWindow - 1 + "");

        if (indexWindow - 1 >= 0) window.setItem(prevWindow, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_SIGN), "По дате:");
        window.setItem(46, itemStack);
        itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_SIGN), "Самые дорогие:");
        window.setItem(48, itemStack);
        itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_SIGN), "Самые дешёвые:");
        window.setItem(49, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(Material.ACACIA_BOAT), indexWindow + 1 + "");
        window.setItem(nextWindow, itemStack);
    }
}
