package me.js.auc.auctionhouse.event;
import me.js.auc.auctionhouse.lists.Shop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ServerTicker {
    public ServerTicker(Plugin plugin, Shop shop) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, shop::TimeDecrease, 1L, 50L);
    }
}
