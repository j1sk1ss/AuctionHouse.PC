package me.js.auc.auctionhouse.commands;

import java.util.UUID;
import java.util.logging.Logger;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.yic.xconomy.api.XConomyAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class CommandManager implements CommandExecutor {
    private static final Logger log = Logger.getLogger("Minecraft");
    public CommandManager(XConomyAPI xcapi, Shop shop) {
        xConomyAPI = xcapi;
        this.moneyTransfer = new MoneyTransfer(shop, xcapi);
        shopWindow = new ShopWindow(27, "Shop test", moneyTransfer, xConomyAPI);
    }
    MoneyTransfer moneyTransfer;
    ShopWindow shopWindow;
    XConomyAPI xConomyAPI;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PlayerData playerData = getPlayerData(sender.getName());
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("moneys")) {
            assert playerData != null;
            shopWindow.ShowWindow(0, player);
            player.sendMessage(playerData.getBalance().toString());
        }

        return true;
    }
    private PlayerData getPlayerData(String playerName) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
