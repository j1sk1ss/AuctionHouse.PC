package me.js.auc.auctionhouse;

import java.util.Objects;
import java.util.logging.Logger;

import me.js.auc.auctionhouse.event.Listeners;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.commands.CommandManager;

import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AuctionHouse extends JavaPlugin {
    Shop shop = new Shop();
    XConomyAPI xConomyAPI;
    private MoneyTransfer moneyTransfer;
    @Override
    public void onEnable() {
        xConomyAPI = new XConomyAPI();
        moneyTransfer = new MoneyTransfer(shop, xConomyAPI);
        CommandManager commandManager = new CommandManager(xConomyAPI, shop, moneyTransfer);

        Objects.requireNonNull(getCommand("shop")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("sell")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("item_info")).setExecutor(commandManager);

        Bukkit.getPluginManager().registerEvents(new Listeners(moneyTransfer, xConomyAPI, shop), this);
    }
    @Override
    public void onDisable() {

    }
}
