package me.js.auc.auctionhouse.scripts;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class ItemWorker {
    public ItemStack SetName(ItemStack item,String name) {
        ItemMeta itemMeta = Objects.requireNonNull(item).getItemMeta();
        itemMeta.setDisplayName(name);
        Objects.requireNonNull(item).setItemMeta(itemMeta);
        return null;
    }
}
