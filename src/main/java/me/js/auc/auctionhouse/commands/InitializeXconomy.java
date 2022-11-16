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
public class InitializeXconomy implements CommandExecutor {
    private static final Logger log = Logger.getLogger("Minecraft");
    XConomyAPI xcapi = new XConomyAPI();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        PlayerData playerData = xcapi.getPlayerData(sender.getName());
        if (playerData == null) xcapi.createPlayerData(UUID.randomUUID(), sender.getName());
        else log.info("Player are initialized");

        if (command.getName().equalsIgnoreCase("auc")) {

        }

        if (command.getName().equalsIgnoreCase("balance")) {
            Player player = (Player) sender;
            player.sendMessage(playerData.getBalance().toString());
            xcapi.changePlayerBalance(playerData.getUniqueId(), playerData.getName(), new BigDecimal(100), true);
        }

        return true;
    }
}
