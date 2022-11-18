package me.js.auc.auctionhouse.commands;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {
    public CommandManager(XConomyAPI xConomyAPI, Shop shop, MoneyTransfer moneyTransfer) {
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
        this.moneyTransfer = moneyTransfer;
    }
    private final Shop shop;
    private final MoneyTransfer moneyTransfer;
    private final XConomyAPI xConomyAPI;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if (command.getName().equals("shop")) {
            ShopWindow shopWindow = new ShopWindow(27, "Рынок", shop);
            shopWindow.ShowWindow(0, player);
        }

        if (command.getName().equals("sell")) {
            moneyTransfer.SellItem(Double.parseDouble(args[0]), xConomyAPI.getPlayerData(player.getUniqueId()),
                    player.getInventory().getItemInMainHand());
            player.getInventory().setItemInMainHand(null);
        }

        return true;
    }
}
