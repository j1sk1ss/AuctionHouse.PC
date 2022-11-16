package me.js.auc.auctionhouse.commands;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.ValueRange;

public class BalanceCommand implements CommandExecutor {
    private static final Logger log = Logger.getLogger("Minecraft");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        log.severe("CHECK!");
        return true;
    }
}
