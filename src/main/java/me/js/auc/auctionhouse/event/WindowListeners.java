package me.js.auc.auctionhouse.event;

import me.js.auc.auctionhouse.AuctionHouse;
import me.js.auc.auctionhouse.interfaces.IWindow;
import me.js.auc.auctionhouse.scripts.ItemWorker;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Objects;

public class WindowListeners<T> implements Listener {
    public WindowListeners(MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI, Shop shop, T window,
                           Player owner) {
        this.moneyTransfer = moneyTransfer;
        this.xConomyAPI    = xConomyAPI;
        this.shop          = shop;
        this.owner         = owner;
        this.window        = window;
    }
    private T window;
    private Player owner;
    private Shop shop;
    private MoneyTransfer moneyTransfer;
    private XConomyAPI xConomyAPI;
    private PluginManager pluginManager = new PluginManager();
    private Boolean isClose = false;
    private Integer clickPosition = 0;

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {

            String windowName = event.getView().getTitle();

            if (Objects.requireNonNull(event.getClickedInventory()).getSize() == 41 && !windowName.equals("Покупка")
            && !windowName.equals("Crafting")) {
                event.setCancelled(true);
                return;
            }
            if (player != owner || !event.isLeftClick() || event.getCurrentItem() == null) return;
            if (!windowName.equals("Покупка")) clickPosition = event.getSlot();

            final Inventory thisInventory = event.getInventory();

            final int windowCapacity         = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                    getInt("auction.shop_window.shop_page_capacity");
            final int previousButtonPosition = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                    getInt("auction.shop_window.previous_page_position");
            final int nextButtonPosition     = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                    getInt("auction.shop_window.next_page_position");

            switch (windowName) {
                case "Рынок" -> {
                    if (clickPosition < windowCapacity) {
                        isClose = false;
                        var approveWindow = new ApproveWindow(event.getCurrentItem());
                        approveWindow.ShowWindow(0, player, true);
                    } else {
                        var tempWindow = (ShopWindow)window;
                        if (clickPosition == previousButtonPosition || clickPosition == nextButtonPosition) {
                            SwipePage(Integer.parseInt(event.getCurrentItem().getItemMeta().getDisplayName()), player, window);
                            tempWindow.shop.shopList = new ArrayList<>(shop.shopList);
                        }
                        SortPage(tempWindow, clickPosition);
                    }
                    event.setCancelled(true);
                }

                case "Покупка" -> {
                    final int cancelPosition = 5;
                    var tempWindow = (ShopWindow)window;

                    if (event.getSlot() >= cancelPosition) {
                        moneyTransfer.BuyItem(owner, pluginManager.GetPlayerData(player.getUniqueId(), xConomyAPI),
                                tempWindow.shop.shopList.get(clickPosition), shop);
                    }

                    isClose = false;
                    tempWindow.ShowWindow(0, player, true);
                    event.setCancelled(true);
                }

                case "Просрочка" -> {
                    ExpiredWindow tempWindow = (ExpiredWindow) window;

                    if (clickPosition < windowCapacity) {
                        var itemStack = thisInventory.getItem(clickPosition);
                        assert itemStack != null;

                        player.getInventory().addItem(new ItemWorker().SetLore(itemStack, ""));

                        tempWindow.TakeItem(clickPosition);
                        thisInventory.setItem(clickPosition, null);
                        SwipePage(0, player, window);
                    } else {
                        if (clickPosition == previousButtonPosition || clickPosition == nextButtonPosition) {
                            SwipePage(Integer.parseInt(event.getCurrentItem().getItemMeta().getDisplayName()), player, window);
                        } else {
                            SortPage(tempWindow, clickPosition);
                            SwipePage(0, player, window);
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    private void SortPage(IWindow window, int clickPosition) {
        final int biggestPriceSortButton  = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.biggest_price_sort_position");
        final int smallestPriceSortButton = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.smallest_price_sort_position");
        final int timeSortButton          = AuctionHouse.getPlugin(AuctionHouse.class).getConfig().
                getInt("auction.windows.data_sort_position");

        if (clickPosition == biggestPriceSortButton)  window.PriceSort(true);
        if (clickPosition == smallestPriceSortButton)  window.PriceSort(false);
        if (clickPosition == timeSortButton) window.TimeSort();
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
    private void Closed(InventoryCloseEvent event) {
        if (isClose && event.getPlayer() == owner) {
            owner = null;
            moneyTransfer = null;
            xConomyAPI = null;
            pluginManager = null;
            shop = null;
            window = null;
            PlayerInteractEvent.getHandlerList().unregister(this);
        } else {
            isClose = true;
        }
    }
}
