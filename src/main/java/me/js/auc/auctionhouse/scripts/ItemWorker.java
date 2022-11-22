package me.js.auc.auctionhouse.scripts;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class ItemWorker {
    public ItemStack SetLore(ItemStack item, String loreLine) {
        var itemMeta = Objects.requireNonNull(item).getItemMeta();
        var lore = Arrays.stream(loreLine.split("\n")).toList();
        itemMeta.setLore(lore);
        Objects.requireNonNull(item).setItemMeta(itemMeta);
        return item;
    }
    public ItemStack SetName(ItemStack item, String name) {
        var itemMeta = Objects.requireNonNull(item).getItemMeta();
        itemMeta.setDisplayName(name);
        Objects.requireNonNull(item).setItemMeta(itemMeta);
        return item;
    }
}
