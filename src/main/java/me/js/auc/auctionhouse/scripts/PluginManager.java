package me.js.auc.auctionhouse.scripts;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PluginManager {
    public void GetDefaultWindow(Player player, Shop shop, Plugin plugin) {
        player.closeInventory();
        ShopWindow shopWindow = new ShopWindow(54, "Рынок", shop, plugin);
        shopWindow.ShowWindow(0, player, true);
    }
    public PlayerData GetPlayerData(String playerName, XConomyAPI xConomyAPI) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
