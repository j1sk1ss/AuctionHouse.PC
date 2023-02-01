package me.js.auc.auctionhouse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

        /*
        if (new File("AuctionData.txt").exists()) {
            try {
                shop = new DataWorker().GetData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        */

        XConomyAPI xConomyAPI = new XConomyAPI();
        new ServerTicker(this, shop);
        CommandManager commandManager = new CommandManager(xConomyAPI, shop, this);

        Objects.requireNonNull(getCommand("shop")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("sell")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("expired")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("save")).setExecutor(commandManager);
    }
    @Override
    public void onDisable() {
        new DataWorker().SaveData(shop);
        super.onDisable();
    }
}
