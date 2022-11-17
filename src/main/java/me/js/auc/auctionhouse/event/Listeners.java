package me.js.auc.auctionhouse.event;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Listeners implements Listener {

    public Listeners(MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI, Shop shop) {
        this.moneyTransfer = moneyTransfer;
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
    }
    private Shop shop;
    private MoneyTransfer moneyTransfer;
    private XConomyAPI xConomyAPI;
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory() != null) {
                if (event.getView().getTitle().equals("Shop test")) {

                    if (event.isRightClick()) {
                        int itemPosition = event.getSlot();
                        moneyTransfer.BuyItem(getPlayerData(player.getName()), shop.shopList.get(itemPosition).UniqId);
                        event.getInventory().setItem(itemPosition, null);
                    }

                    event.setCancelled(true);
                }
            }
        }

    }

    private PlayerData getPlayerData(String playerName) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
