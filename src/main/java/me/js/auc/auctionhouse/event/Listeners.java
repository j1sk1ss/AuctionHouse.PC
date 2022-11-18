package me.js.auc.auctionhouse.event;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.ui.ApproveWindow;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class Listeners implements Listener {
    public Listeners(MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI, Shop shop) {
        this.moneyTransfer = moneyTransfer;
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
    }
    private final Shop shop;
    private final MoneyTransfer moneyTransfer;
    private final XConomyAPI xConomyAPI;
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory() != null) {
                int itemPosition = 0;
                if (!event.isLeftClick()) return;
                switch (event.getView().getTitle()) {
                    case "Рынок":
                            if (event.getSlot() < 18) {
                                ApproveWindow approveWindow =
                                        new ApproveWindow(event.getCurrentItem());
                                approveWindow.ShowWindow(0, player);
                            } else {
                                GetDefaultWindow(player, Integer.getInteger(Objects.requireNonNull(event.getInventory().
                                        getItem(event.getSlot())).getItemMeta().getDisplayName()));
                            }
                        break;

                    case "Покупка":
                        final int acceptIndex = 5;
                            if (event.getSlot() >= acceptIndex) {
                                moneyTransfer.BuyItem(getPlayerData(player.getName()), shop.shopList.get(itemPosition).UniqId);
                                event.getInventory().setItem(itemPosition, null);
                            }
                        GetDefaultWindow(player, 0);
                        break;
                }
                event.setCancelled(true);
            }
        }
    }
    private void GetDefaultWindow(Player player, Integer window) {
        player.closeInventory();
        ShopWindow shopWindow = new ShopWindow(27, "Рынок", shop);
        shopWindow.ShowWindow(window, player);
    }
    private PlayerData getPlayerData(String playerName) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
