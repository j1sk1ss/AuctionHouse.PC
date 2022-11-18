package me.js.auc.auctionhouse.event;

import me.js.auc.auctionhouse.interfaces.IWindow;
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

public class WindowListeners implements Listener {
    public WindowListeners(MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI, Shop shop, IWindow window,
                           Player owner) {
        this.moneyTransfer = moneyTransfer;
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
        this.window = window;
        shopWindow = window.GetShopWindow();
        this.owner = owner;
    }
    private final Player owner;
    private final IWindow window;
    private final Shop shop;
    private final MoneyTransfer moneyTransfer;
    private final XConomyAPI xConomyAPI;
    public ShopWindow shopWindow;
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (player != owner) return;
            if (event.getClickedInventory() != null) {
                int itemPosition = 0;
                if (!event.isLeftClick()) return;
                switch (event.getView().getTitle()) {
                    case "Рынок" -> {
                        final int windowCapacity = 45;
                        if (event.getSlot() < windowCapacity) {
                            ApproveWindow approveWindow =
                                    new ApproveWindow(event.getCurrentItem());
                            approveWindow.ShowWindow(0, player);
                        } else {
                            SwipePage(Integer.parseInt(Objects.requireNonNull
                                    (event.getCurrentItem()).getItemMeta().getDisplayName()), player);
                        }
                        event.setCancelled(true);
                    }
                    case "Покупка" -> {
                        final int acceptIndex = 5;
                        if (event.getSlot() >= acceptIndex) {
                            moneyTransfer.BuyItem(getPlayerData(player.getName()), shop.shopList.get(itemPosition).UniqId);
                            event.getInventory().setItem(itemPosition, null);
                        }
                        GetDefaultWindow(player, 0);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    private void SwipePage(Integer window, Player player) {
        if (shopWindow == null) return;
        player.sendMessage("1");
        shopWindow.ShowWindow(window, player);
    }
    private void GetDefaultWindow(Player player, Integer window) {
        player.closeInventory();
        ShopWindow shopWindow = new ShopWindow(54, "Рынок", shop);
        shopWindow.ShowWindow(window, player);
    }
    private PlayerData getPlayerData(String playerName) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
