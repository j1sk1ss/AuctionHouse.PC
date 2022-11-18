package me.js.auc.auctionhouse.scripts;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemWorker {
    public ItemStack SetLore(ItemStack item, String loreLine) {
        ItemMeta itemMeta = Objects.requireNonNull(item).getItemMeta();

        List<String> lore = Arrays.stream(loreLine.split("\n")).toList();

        itemMeta.setLore(lore);

        Objects.requireNonNull(item).setItemMeta(itemMeta);
        return item;
    }
    public ItemStack SetName(ItemStack item, String name) {
        ItemMeta itemMeta = Objects.requireNonNull(item).getItemMeta();

        itemMeta.setDisplayName(name);

        Objects.requireNonNull(item).setItemMeta(itemMeta);
        return item;
    }
}
