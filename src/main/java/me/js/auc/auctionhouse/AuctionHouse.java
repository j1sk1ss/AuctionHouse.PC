package me.js.auc.auctionhouse;

import java.util.Objects;

import me.js.auc.auctionhouse.event.ServerTicker;
import me.js.auc.auctionhouse.lists.Expired;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.commands.CommandManager;

import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.plugin.java.JavaPlugin;
public final class AuctionHouse extends JavaPlugin {
    @Override
    public void onEnable() {
        Shop shop = new Shop();
        XConomyAPI xConomyAPI = new XConomyAPI();
        MoneyTransfer moneyTransfer = new MoneyTransfer(shop, xConomyAPI);

        ServerTicker serverTicker = new ServerTicker(this, shop);

        CommandManager commandManager = new CommandManager(xConomyAPI, shop, moneyTransfer, this);

        Objects.requireNonNull(getCommand("shop")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("sell")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("expired")).setExecutor(commandManager);

    }
    @Override
    public void onDisable() {
        super.onDisable();
    }
}
