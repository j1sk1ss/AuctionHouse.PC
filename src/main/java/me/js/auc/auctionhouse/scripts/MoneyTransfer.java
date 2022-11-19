package me.js.auc.auctionhouse.scripts;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class MoneyTransfer {
    public MoneyTransfer(Shop shop, XConomyAPI xConomyAPI) {
        this.shop = shop;
        this.xConomyAPI = xConomyAPI;
    }
    private final Shop shop;
    private final XConomyAPI xConomyAPI;
    public void SellItem(Double price, PlayerData player, ItemStack item) {
        Item sellItem = new Item(item, price, player);
        shop.shopList.add(sellItem);
    }
    public void BuyItem(PlayerData buyer, UUID uniqId) {
        for (int i = 0; i < shop.shopList.size(); i++) {
            Item item = shop.shopList.get(i);

            if (!Objects.equals(uniqId, item.UniqId)) continue;
            if (buyer.getBalance().doubleValue() < item.Price) continue;
            shop.shopList.remove(i);

            Player player = getPlayerByUuid(buyer.getUniqueId());
            player.getInventory().addItem(item.Item);

            Objects.requireNonNull(Bukkit.getPlayer(item.Owner.getUniqueId())).sendMessage("Продан предмет: "
                    + item.Item.getI18NDisplayName() + ". За: " + item.Price + "₽");

            ChangeBalance(buyer, item.Price, false);
            ChangeBalance(item.Owner, item.Price, true);
            break;
        }
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
