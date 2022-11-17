package me.js.auc.auctionhouse.commands;

import java.util.UUID;
import java.util.logging.Logger;

import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.ui.CostSetter;
import me.js.auc.auctionhouse.ui.ShopWindow;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.yic.xconomy.api.XConomyAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
public class CommandManager implements CommandExecutor {
    private static final Logger log = Logger.getLogger("Minecraft");
    public CommandManager(XConomyAPI xcapi, Shop shop, MoneyTransfer moneyTransfer) {
        xConomyAPI = xcapi;
        this.shop = shop;
        this.moneyTransfer = moneyTransfer;
        shopWindow = new ShopWindow(27, "Рынок", moneyTransfer, xConomyAPI, shop);
    }
    private final Shop shop;
    private final MoneyTransfer moneyTransfer;
    private final ShopWindow shopWindow;
    private final XConomyAPI xConomyAPI;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PlayerData playerData = getPlayerData(sender.getName());
        Player player = (Player) sender;

        if (command.getName().equals("shop")) {
            shopWindow.ShowWindow(0, player);
        }

        if (command.getName().equals("sell")) {
            CostSetter costSetter = new CostSetter(player, player.getInventory().getItemInMainHand());
        }

        if (command.getName().equals("item_info")) {

        }

        return true;
    }
    private PlayerData getPlayerData(String playerName) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
