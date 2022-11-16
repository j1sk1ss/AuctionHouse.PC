package me.js.auc.auctionhouse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.logging.Logger;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.commands.CommandManager;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class AuctionHouse extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    XConomyAPI xcapi = new XConomyAPI();
    MoneyTransfer moneyTransfer = new MoneyTransfer(new Shop(), xcapi);
    @Override
    public void onEnable() {
        log.severe("STARTS!");

        Objects.requireNonNull(getCommand("moneys")).setExecutor(new CommandManager(xcapi));
    }
    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }
}
