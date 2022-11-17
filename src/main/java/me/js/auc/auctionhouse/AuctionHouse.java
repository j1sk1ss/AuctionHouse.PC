package me.js.auc.auctionhouse;

import java.util.Objects;

import me.js.auc.auctionhouse.event.Listeners;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.commands.CommandManager;

import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/*
    <->
    ROADMAP:

    1. Window switcher in aucWin
        1.1 Sorting in aucWin

    2. Include LuckPerms

    <->
*/

public final class AuctionHouse extends JavaPlugin {
    @Override
    public void onEnable() {
        Shop shop = new Shop();
        XConomyAPI xConomyAPI = new XConomyAPI();
        MoneyTransfer moneyTransfer = new MoneyTransfer(shop, xConomyAPI);
        CommandManager commandManager = new CommandManager(xConomyAPI, shop, moneyTransfer);

        Objects.requireNonNull(getCommand("shop")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("sell")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("item_info")).setExecutor(commandManager);

        Bukkit.getPluginManager().registerEvents(new Listeners(moneyTransfer, xConomyAPI, shop), this);
    }
}
