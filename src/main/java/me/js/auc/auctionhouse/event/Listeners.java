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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    private double cost = 0;
    private double modification = 1;
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory() != null) {
                int itemPosition = 0;

                if (event.getView().getTitle().equals("Рынок")) {
                    if (event.isLeftClick()) {
                        itemPosition = event.getSlot();

                        ApproveWindow approveWindow = new ApproveWindow(xConomyAPI, moneyTransfer, shop, event.getInventory(),
                        event.getCurrentItem(), (Player) event.getWhoClicked());
                    }
                    event.setCancelled(true);
                }

                if (event.getView().getTitle().equals("Цена")) {
                    if (event.isLeftClick()) {

                        switch (event.getSlot()) {
                            case 4:
                                cost = 0;
                                break;
                            case 9:
                                cost -= 100d * modification;
                                break;
                            case 10:
                                cost -= 50d * modification;
                                break;
                            case 11:
                                cost -= 10d * modification;
                                break;
                            case 15:
                                cost += 10d * modification;
                                break;
                            case 16:
                                cost += 50d * modification;
                                break;
                            case 17:
                                cost += 100d * modification;
                                break;
                            case 13:
                                moneyTransfer.SellItem(cost, xConomyAPI.getPlayerData(player.getUniqueId()), new ItemStack(
                                        Objects.requireNonNull(event.getInventory().getItem(13))
                                ));
                                player.getInventory().setItemInMainHand(null);

                                player.closeInventory();
                                ShopWindow shopWindow = new ShopWindow(27, "Рынок", moneyTransfer, xConomyAPI, shop);
                                shopWindow.ShowWindow(0, (Player) event.getWhoClicked());
                                break;
                            case 21:
                                modification = .01d;
                                break;
                            case 22:
                                modification = .1d;
                                break;
                            case 23:
                                modification = 1d;
                                break;
                        }

                        ItemMeta itemMeta = Objects.requireNonNull(event.getInventory().getItem(13)).getItemMeta();
                        itemMeta.setDisplayName(Objects.requireNonNull(event.getInventory().getItem(13)).getI18NDisplayName()+" Цена: "+cost);
                        Objects.requireNonNull(event.getInventory().getItem(13)).setItemMeta(itemMeta);
                    }
                    event.setCancelled(true);
                }

                if (event.getView().getTitle().equals("Покупка")) {
                    if (event.isLeftClick()) {
                        if (event.getSlot() >= 5) {
                            moneyTransfer.BuyItem(getPlayerData(player.getName()), shop.shopList.get(itemPosition).UniqId);
                            event.getInventory().setItem(itemPosition, null);
                        }
                        player.closeInventory();
                        ShopWindow shopWindow = new ShopWindow(27, "Рынок", moneyTransfer, xConomyAPI, shop);
                        shopWindow.ShowWindow(0, (Player) event.getWhoClicked());
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
