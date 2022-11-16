package me.js.auc.auctionhouse;

import java.util.Objects;
import java.util.logging.Logger;

import me.js.auc.auctionhouse.commands.BalanceCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AuctionHouse extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        log.severe("STARTS!");
        Objects.requireNonNull(getCommand("check")).setExecutor(new BalanceCommand());
    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }
}
