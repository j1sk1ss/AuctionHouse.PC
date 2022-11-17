package me.js.auc.auctionhouse;

import java.util.Objects;
import java.util.logging.Logger;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.commands.CommandManager;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class AuctionHouse extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    Shop shop = new Shop();
    XConomyAPI xConomyAPI = new XConomyAPI();
    @Override
    public void onEnable() {
        log.severe("STARTS!");
        Objects.requireNonNull(getCommand("moneys")).setExecutor(new CommandManager(xConomyAPI, shop));
    }
    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }
}
