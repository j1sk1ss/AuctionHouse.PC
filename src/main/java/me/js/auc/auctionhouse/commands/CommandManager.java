package me.js.auc.auctionhouse.commands;

import me.js.auc.auctionhouse.event.WindowListeners;
import me.js.auc.auctionhouse.lists.Expired;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.ui.ExpiredWindow;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {
    public CommandManager(XConomyAPI xConomyAPI, Shop shop, MoneyTransfer moneyTransfer, Plugin plugin) {
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
        this.moneyTransfer = moneyTransfer;
        this.plugin = plugin;
    }
    private final Plugin plugin;
    private final String[] accessedGroups = {"testGroup"};
    private final Shop shop;
    private final MoneyTransfer moneyTransfer;
    private final XConomyAPI xConomyAPI;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        final int nicknameArg = 0;

        Player player = Bukkit.getPlayer(args[nicknameArg]);
        if (player == null) return true;

        if (!isHaveAccess(player, accessedGroups)) {
            player.sendMessage("У вас нет прав для использования этой комманды!");
            return true;
        }

        if (command.getName().equals("shop")) {
            ShopWindow shopWindow = new ShopWindow(54, "Рынок", shop);

            WindowListeners windowListeners = new WindowListeners(moneyTransfer, xConomyAPI, shop, shopWindow, player);
            Bukkit.getPluginManager().registerEvents(windowListeners, plugin);

            shopWindow.ShowWindow(0, player);
        }

        if (command.getName().equals("sell")) {
            final int costArg = 1;
            moneyTransfer.SellItem(Double.parseDouble(args[costArg]), xConomyAPI.getPlayerData(player.getUniqueId()),
                    player.getInventory().getItemInMainHand());
            player.getInventory().setItemInMainHand(null);
        }

        if (command.getName().equals("expired")) {
            ExpiredWindow expiredWindow = new ExpiredWindow(54, "Просрочка", shop, xConomyAPI.getPlayerData(player.getUniqueId()));

            WindowListeners windowListeners = new WindowListeners(moneyTransfer, xConomyAPI, shop, expiredWindow, player);
            Bukkit.getPluginManager().registerEvents(windowListeners, plugin);

            expiredWindow.ShowWindow(0, player);
        }

        return true;
    }
    public static boolean isHaveAccess(Player player, String[] groups) {
        for (String group : groups) {
            if (player.hasPermission("group." + group)) return true;
        }
        return false;
    }
}
