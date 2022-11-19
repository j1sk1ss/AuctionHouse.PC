package me.js.auc.auctionhouse.event;
import me.js.auc.auctionhouse.scripts.PluginManager;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.ui.ApproveWindow;
import me.js.auc.auctionhouse.ui.ExpiredWindow;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class WindowListeners<T> implements Listener {
    public WindowListeners(MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI, Shop shop, T window,
                           Player owner) {
        this.moneyTransfer = moneyTransfer;
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
        this.owner = owner;
        this.window = window;
    }
    private final Player owner;
    private final Shop shop;
    private final MoneyTransfer moneyTransfer;
    private final XConomyAPI xConomyAPI;
    private final PluginManager pluginManager = new PluginManager();
    public T window;
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (player != owner) return;
            if (event.getClickedInventory() != null) {
                int itemPosition = 0;
                if (!event.isLeftClick()) return;
                if (event.getCurrentItem() == null) return;
                switch (event.getView().getTitle()) {

                    case "Рынок" -> {
                        final int windowCapacity = 45;

                        if (event.getSlot() < windowCapacity) {
                            ApproveWindow approveWindow =
                                    new ApproveWindow(event.getCurrentItem());
                            approveWindow.ShowWindow(0, player);
                        } else {
                            SwipePage(Integer.parseInt(Objects.requireNonNull
                                    (event.getCurrentItem()).getItemMeta().getDisplayName()), player, window);
                        }
                        event.setCancelled(true);
                    }

                    case "Покупка" -> {
                        final int acceptIndex = 5;

                        if (event.getSlot() >= acceptIndex) {
                            moneyTransfer.BuyItem(pluginManager.GetPlayerData(player.getName(), xConomyAPI), shop.shopList.get(itemPosition).UniqId);
                            event.getInventory().setItem(itemPosition, null);
                        }
                        pluginManager.GetDefaultWindow(player, shop);
                        event.setCancelled(true);
                    }

                    case "Просрочка" -> {
                        final int windowCapacity = 45;

                        if (event.getSlot() < windowCapacity) {
                            player.getInventory().addItem(Objects.requireNonNull(event.getInventory().getItem(itemPosition)));
                            event.getInventory().setItem(itemPosition, null);
                        } else {
                            SwipePage(Integer.parseInt(Objects.requireNonNull
                                    (event.getCurrentItem()).getItemMeta().getDisplayName()), player, window);
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    private void SwipePage(Integer page, Player player, T window) {
        if (window == null) return;

        if (window instanceof ShopWindow tempWindow) {
            tempWindow.ShowWindow(page, player);
        } else if (window instanceof ExpiredWindow tempWindow) {
            tempWindow.ShowWindow(page, player);
        }
    }
}
