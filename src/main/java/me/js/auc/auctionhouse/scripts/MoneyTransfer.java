package me.js.auc.auctionhouse.scripts;

import me.js.auc.auctionhouse.event.WindowListeners;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class MoneyTransfer<T> {
    public MoneyTransfer(Shop shop, XConomyAPI xConomyAPI) {
        this.xConomyAPI = xConomyAPI;
    }
    private final XConomyAPI xConomyAPI;
    public void SellItem(Double price, PlayerData player, ItemStack item, Shop shop) {
        Item sellItem = new Item(item, price, player);
        shop.shopList.add(sellItem);
    }
    public void BuyItem(PlayerData buyer, UUID uniqId, T cls, Shop shop) {
        List<Item> shopList = ((ShopWindow) cls).shopList;
        for (int i = 0; i < shopList.size(); i++) {
            Item item = shopList.get(i);

            if (!Objects.equals(uniqId, item.UniqId)) continue;
            if (buyer.getBalance().doubleValue() < item.Price) break;

            shopList.remove(i);

            Player player = getPlayerByUuid(buyer.getUniqueId());

            player.getInventory().addItem(new ItemStack(item.Item.getType(), item.Item.getAmount()));

            Objects.requireNonNull(Bukkit.getPlayer(item.Owner.getUniqueId())).sendMessage("Продан предмет: "
                    + item.Item.getI18NDisplayName() + ". За: " + item.Price + "₽");

            ChangeBalance(buyer, item.Price, false);
            ChangeBalance(item.Owner, item.Price, true);
            break;
        }
        ((ShopWindow) cls).shopList = new ArrayList<Item>(shopList);
        shop.shopList = new ArrayList<>(shopList);
    }
    private void ChangeBalance(PlayerData player, Double amount, Boolean isAdd) {
        xConomyAPI.changePlayerBalance(player.getUniqueId(), player.getName(), new BigDecimal(amount), isAdd);
    }
    private Player getPlayerByUuid(UUID uuid) {
        for(Player p : getServer().getOnlinePlayers())
            if(p.getUniqueId().equals(uuid)) return p;
        throw new IllegalArgumentException();
    }
}
