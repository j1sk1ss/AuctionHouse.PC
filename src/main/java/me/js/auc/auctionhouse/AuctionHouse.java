package me.js.auc.auctionhouse;

import java.io.*;

import java.util.Objects;

import me.js.auc.auctionhouse.dataStore.DataWorker;
import me.js.auc.auctionhouse.event.ServerTicker;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.commands.CommandManager;

import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.plugin.java.JavaPlugin;
public final class AuctionHouse extends JavaPlugin {
    private Shop shop;
    @Override
    public void onEnable() {
        shop = new Shop();

        LoadShop();
        saveDefaultConfig();

        XConomyAPI xConomyAPI = new XConomyAPI();
        new ServerTicker(this, shop);

        CommandManager commandManager = new CommandManager(xConomyAPI, shop, this);

        Objects.requireNonNull(getCommand("shop")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("sell")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("expired")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("save")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("trade")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("trdaccept")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("trdreject")).setExecutor(commandManager);
    }

    private void LoadShop() {
        if (new File("AuctionData.txt").exists()) {
            try {
                shop = new DataWorker().GetData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDisable() {
        new DataWorker().SaveData(shop);
        super.onDisable();
    }
}
