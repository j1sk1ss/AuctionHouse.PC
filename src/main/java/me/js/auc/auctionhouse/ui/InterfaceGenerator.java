package me.js.auc.auctionhouse.ui;

import me.js.auc.auctionhouse.AuctionHouse;
import me.js.auc.auctionhouse.scripts.ItemWorker;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
public class InterfaceGenerator {
    public void SetUserInterface(Inventory window, ItemWorker itemWorker, Integer indexWindow) {
        final int prevWindow = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.previous_page_position");
        final int nextWindow = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.next_page_position");

        final int dataSortButton          = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.data_sort_position");
        final int biggestPriseSortButton  = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.biggest_price_sort_position");
        final int smallestPriseSortButton = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.smallest_price_sort_position");

        var itemStack = itemWorker.SetName(new ItemStack(
                Material.getMaterial(AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.previous_icon"))
        ), indexWindow - 1 + "");

        if (indexWindow - 1 >= 0) window.setItem(prevWindow, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(
                Material.getMaterial(AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.data_icon"))
        ), AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.data_text"));
        window.setItem(dataSortButton, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(
                Material.getMaterial(AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.biggest_icon"))
        ), AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.biggest_text"));
        window.setItem(biggestPriseSortButton, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(
                Material.getMaterial(AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.smallest_icon"))
        ), AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.smallest_text"));
        window.setItem(smallestPriseSortButton, itemStack);

        itemStack = itemWorker.SetName(new ItemStack(
                Material.getMaterial(AuctionHouse.getPlugin(AuctionHouse.class).getConfig().getString("auction.windows.next_icon"))
        ), indexWindow + 1 + "");
        window.setItem(nextWindow, itemStack);
    }
}
