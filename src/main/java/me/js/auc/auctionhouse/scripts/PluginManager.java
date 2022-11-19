package me.js.auc.auctionhouse.scripts;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;

public class PluginManager {

    public void GetDefaultWindow(Player player, Shop shop) {
        player.closeInventory();
        ShopWindow shopWindow = new ShopWindow(54, "Рынок", shop);
        shopWindow.ShowWindow(0, player, true);
    }

    public PlayerData GetPlayerData(String playerName, XConomyAPI xConomyAPI) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
