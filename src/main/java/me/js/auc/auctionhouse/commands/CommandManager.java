package me.js.auc.auctionhouse.commands;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.logging.Logger;

import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.yic.xconomy.api.XConomyAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class CommandManager implements CommandExecutor {
    private static final Logger log = Logger.getLogger("Minecraft");
    public CommandManager(XConomyAPI xcapi) {
        xConomyAPI = xcapi;
    }
    XConomyAPI xConomyAPI;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        PlayerData playerData = xConomyAPI.getPlayerData(sender.getName());
        if (playerData == null) xConomyAPI.createPlayerData(UUID.randomUUID(), sender.getName());
        else log.info("Player are initialized");

        if (command.getName().equalsIgnoreCase("auc")) {
            return false;
        }

        if (command.getName().equalsIgnoreCase("balance")) {
            return false;
        }

        return true;
    }
}