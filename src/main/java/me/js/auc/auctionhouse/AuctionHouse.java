package me.js.auc.auctionhouse;

import java.util.Objects;

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

        CommandManager commandManager = new CommandManager(xConomyAPI, shop, moneyTransfer, this);

        Objects.requireNonNull(getCommand("shop")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("sell")).setExecutor(commandManager);

    }
    @Override
    public void onDisable() {
        super.onDisable();
    }
}
