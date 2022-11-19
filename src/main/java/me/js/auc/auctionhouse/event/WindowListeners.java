package me.js.auc.auctionhouse.event;
import me.js.auc.auctionhouse.object.Item;
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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class WindowListeners<T> implements Listener {
    public WindowListeners(MoneyTransfer<T> moneyTransfer, XConomyAPI xConomyAPI, Shop shop, T window,
                           Player owner) {
        this.moneyTransfer = moneyTransfer;
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
        this.owner = owner;
        this.window = window;
    }
    private Player owner;
    public Shop shop;
    private MoneyTransfer<T> moneyTransfer;
    private XConomyAPI xConomyAPI;
    private PluginManager pluginManager = new PluginManager();
    private final T window;
    private Boolean isClose = false;
    private Integer clickPosition = 0;
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (player != owner || !event.isLeftClick() || event.getCurrentItem() == null) return;
            Inventory thisInventory = event.getInventory();
            final int windowCapacity = 45;

            if (!event.getView().getTitle().equals("Покупка")) clickPosition = event.getSlot();

            switch (event.getView().getTitle()) {
                case "Рынок" -> {
                    if (clickPosition < windowCapacity) {
                        isClose = false;
                        ApproveWindow approveWindow = new ApproveWindow(event.getCurrentItem());
                        approveWindow.ShowWindow(0, player, true);
                    } else {
                        ShopWindow tempWindow = (ShopWindow)window;
                        if (clickPosition == 45 || clickPosition == 53) {
                            SwipePage(Integer.parseInt(event.getCurrentItem().getItemMeta().getDisplayName()), player, window);
                            tempWindow.shopList = new ArrayList<>(shop.shopList);
                        }
                        switch (clickPosition) {
                            case 48 -> tempWindow.PriceSort(true);
                            case 49 -> tempWindow.PriceSort(false);
                            case 46 -> tempWindow.TimeSort(true);
                            case 47 -> tempWindow.TimeSort(false);
                        }
                    }
                    event.setCancelled(true);
                }
                case "Покупка" -> {
                    final int cancelPosition = 5;
                    if (event.getSlot() >= cancelPosition) {
                        ShopWindow tempWindow = (ShopWindow)window;
                        moneyTransfer.BuyItem(pluginManager.GetPlayerData(player.getName(), xConomyAPI), tempWindow.shopList.get(clickPosition).UniqId, window, shop);
                    }
                    isClose = false;

                    ShopWindow tempWindow = (ShopWindow)window;
                    tempWindow.ShowWindow(0, player, true);

                    event.setCancelled(true);
                }
                case "Просрочка" -> {
                    if (clickPosition < windowCapacity) {
                        ItemStack itemStack = thisInventory.getItem(clickPosition);
                        assert itemStack != null;

                        player.getInventory().addItem(new ItemStack(itemStack.getType(), itemStack.getAmount()));

                        ((ExpiredWindow)window).TakeItem(clickPosition);
                        thisInventory.setItem(clickPosition, null);

                        SwipePage(0, player, window);
                    } else {
                        ExpiredWindow tempWindow = (ExpiredWindow) window;
                        if (clickPosition == 45 || clickPosition == 53) {
                            SwipePage(Integer.parseInt(event.getCurrentItem().getItemMeta().getDisplayName()), player, window);
                        }
                        switch (clickPosition) {
                            case 48 -> tempWindow.PriceSort(true);
                            case 49 -> tempWindow.PriceSort(false);
                            case 46 -> tempWindow.TimeSort(true);
                            case 47 -> tempWindow.TimeSort(false);
                        }
                    }
                    SwipePage(0, player, window);
                    event.setCancelled(true);
                }
            }
        }
    }
    private void SwipePage(Integer page, Player player, T window) {
        if (window == null) return;

        if (window instanceof ShopWindow tempWindow) {
            tempWindow.ShowWindow(page, player, false);
        } else if (window instanceof ExpiredWindow tempWindow) {
            tempWindow.ShowWindow(page, player, false);
        }
    }
    @EventHandler
    public void Closed(InventoryCloseEvent event) {
        if (isClose) {
            owner = null;
            moneyTransfer = null;
            xConomyAPI = null;
            pluginManager = null;
            shop = null;
        } else {
            isClose = true;
        }
    }
}
