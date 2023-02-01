package me.js.auc.auctionhouse.commands;

import me.js.auc.auctionhouse.dataStore.DataWorker;
import me.js.auc.auctionhouse.event.WindowListeners;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.ui.ExpiredWindow;
import me.js.auc.auctionhouse.ui.ShopWindow;

import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {
    public CommandManager(XConomyAPI xConomyAPI, Shop shop, Plugin plugin) {
        this.xConomyAPI = xConomyAPI;
        this.shop = shop;
        this.plugin = plugin;
        moneyTransfer = new MoneyTransfer(xConomyAPI);
    }
    private final Plugin plugin;
    private final String[] accessedGroups = {"testGroup"};
    private final Shop shop;
    private final XConomyAPI xConomyAPI;
    private final MoneyTransfer moneyTransfer;
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
        player.closeInventory();

        if (command.getName().equals("shop")) {
            ShopWindow shopWindow = new ShopWindow(54, "Рынок", shop, plugin);

            Bukkit.getPluginManager().registerEvents(
                    new WindowListeners<>(moneyTransfer, xConomyAPI, shop, shopWindow, player), plugin
            );

            shopWindow.ShowWindow(0, player, true);
        }

        PlayerData playerData = xConomyAPI.getPlayerData(player.getUniqueId());
        if (command.getName().equals("sell")) {
            final int costArg = 1;
            ItemStack sellingItem = player.getInventory().getItemInMainHand();

            if (sellingItem.getType() == Material.AIR) {
                player.sendMessage("Рука пуста!");
                return true;
            }

            moneyTransfer.SellItem(Double.parseDouble(args[costArg]), playerData, sellingItem, shop);
            player.sendMessage("Предмет: " + sellingItem.getI18NDisplayName() + " X" + sellingItem.getAmount() +
                    ". Выставлен на торговую площадку за: " + args[costArg]+ "₽");
            player.getInventory().setItemInMainHand(null);
        }

        if (command.getName().equals("expired")) {
            new DataWorker().SaveData(shop);
            ExpiredWindow expiredWindow = new ExpiredWindow(54, "Просрочка", shop, playerData);
            WindowListeners<ExpiredWindow> windowListeners = new WindowListeners<>
                    (moneyTransfer, xConomyAPI, shop, expiredWindow, player);
            Bukkit.getPluginManager().registerEvents(windowListeners, plugin);

            expiredWindow.ShowWindow(0, player, true);
        }

        if (command.getName().equals("save")) new DataWorker().SaveData(shop);

        return true;
    }
    public static boolean isHaveAccess(Player player, String[] groups) {
        for (String group : groups) {
            if (player.hasPermission("group." + group)) return true;
        }
        return false;
    }
}
