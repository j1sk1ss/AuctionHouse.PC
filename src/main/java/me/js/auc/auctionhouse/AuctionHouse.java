package me.js.auc.auctionhouse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.logging.Logger;

import me.js.auc.auctionhouse.commands.CommandManager;
import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class AuctionHouse extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    XConomyAPI xcapi = new XConomyAPI();
    private final Shop shop = new Shop();
    @Override
    public void onEnable() {
        log.severe("STARTS!");

        Objects.requireNonNull(getCommand("auc")).setExecutor(new CommandManager(xcapi));
        Objects.requireNonNull(getCommand("balance")).setExecutor(new CommandManager(xcapi));
    }
    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }
    public void SellItem(Double price, BigInteger id, PlayerData player) {
        Player _player = (Player) player;
        ItemStack item = _player.getItemInHand();

        Item sellItem = new Item(item, price, id, player);
        shop.shopList.add(sellItem);

        _player.getInventory().remove(item);
    }
    public void BuyItem(PlayerData buyer, Integer uniqId) {
        for (int i = 0; i < shop.shopList.size(); i++) {
            Item item = shop.shopList.get(i);

            if (!Objects.equals(uniqId, item.UniqId)) continue;
            if (buyer.getBalance().doubleValue() < item.Price) continue;
            shop.shopList.remove(i);

            Player player = (Player) buyer;
            player.getInventory().addItem(item.Item);

            ChangeBalance(buyer, item.Price, false);
            ChangeBalance(item.Owner, item.Price, true);
            break;
        }
    }
    private void ChangeBalance(PlayerData player, Double amount, Boolean isAdd) {
        xcapi.changePlayerBalance(player.getUniqueId(), player.getName(), new BigDecimal(amount), isAdd);
    }
}
