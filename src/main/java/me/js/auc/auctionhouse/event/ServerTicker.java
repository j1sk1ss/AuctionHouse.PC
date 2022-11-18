package me.js.auc.auctionhouse.event;
import me.js.auc.auctionhouse.lists.Shop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class ServerTicker {
    public ServerTicker(Plugin plugin, Shop shop) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
            public void run() {
                shop.TimeDecrease();
            }
        }, 0L, 1L);
    }
}
