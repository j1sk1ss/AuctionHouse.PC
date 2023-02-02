package me.js.auc.auctionhouse.commands;

import me.js.auc.auctionhouse.dataStore.DataWorker;
import me.js.auc.auctionhouse.event.WindowListeners;
import me.js.auc.auctionhouse.lists.Expired;
import me.js.auc.auctionhouse.lists.Shop;
import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.ItemWorker;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.scripts.PlayerWorker;
import me.js.auc.auctionhouse.trading.PlayerTrade;
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
        this.shop       = shop;
        this.plugin     = plugin;
        moneyTransfer   = new MoneyTransfer(xConomyAPI);
        playerTrade     = new PlayerTrade(moneyTransfer, xConomyAPI);
    }
    private final Plugin plugin;
    private final String[] accessedGroups = {"testGroup"};
    private final Shop shop;
    private final PlayerTrade playerTrade;
    private final XConomyAPI xConomyAPI;
    private final MoneyTransfer moneyTransfer;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        final int nicknameArg = 0;
        if (command.getName().equals("trade")) {
            Player seller = (Player) sender;
            Player buyer  = Bukkit.getPlayer(args[nicknameArg]);
            Item sellItem = new Item(seller.getInventory().getItemInMainHand(), seller.getInventory().getItemInMainHand().getItemMeta(),
                    Double.parseDouble(args[nicknameArg + 1]), xConomyAPI.getPlayerData(seller.getUniqueId()));

            assert buyer != null;
            if (seller.getLocation().distanceSquared(buyer.getLocation()) > 10) {
                seller.sendMessage("Вы находитесь слишком далеко от игрока!");
                return true;
            }

            if (sellItem.Item.getType() == Material.AIR) { // Проверка на пустоту
                seller.sendMessage("Рука пуста!");
                return true;
            }

            if (playerTrade.isActiveTrade(seller)) {
                seller.sendMessage("У вас имеется исходящее предложение продажи.\nОтмените его перед созданием нового.");
                return true;
            }

            if (playerTrade.isActiveTrade(buyer)) {
                seller.sendMessage("У игрока имеется входящее предложение продажи.\nПодождите его принятия или отказа.");
                return true;
            }

            playerTrade.CreateTradeOffer(seller, buyer, sellItem);
            seller.sendMessage("Вы отправили запрос на продажу!\nДля его отмены используйте /trdreject");
            buyer.sendMessage("У вас появился запрос на покупку!\nДанные:\n"+sellItem.Item.getI18NDisplayName()+" за: "
                    + sellItem.Price + "₽\nИспользуйте /trdaccept для покупки\nИспользуйте /trdreject для отказа");
            return true;
        }
        if (command.getName().equals("trdaccept")) {
            Player buyer = (Player) sender;

            playerTrade.AcceptTradeOffer(buyer);
            return true;
        }
        if (command.getName().equals("trdreject")) {
            Player buyer = (Player) sender;

            playerTrade.DeleteTradeOffer(buyer);
            return true;
        }

        Player player = Bukkit.getPlayer(args[nicknameArg]); // Получение данных об игроке по его никнейму
        if (player == null) return true;

        if (!isHaveAccess(player, accessedGroups)) { // Проверка прав
            player.sendMessage("У вас нет прав для использования этой комманды!");
            return true;
        }

        player.closeInventory(); // Закрываем любой инвентарь
        PlayerData playerData = xConomyAPI.getPlayerData(player.getUniqueId()); // Получаем данные об игроке из XConomy

        if (command.getName().equals("shop")) { // Команда /shop
            ShopWindow shopWindow = new ShopWindow(54, "Рынок", shop, plugin); // Создаём окно рынка

            Bukkit.getPluginManager().registerEvents(
                    new WindowListeners<>(moneyTransfer, xConomyAPI, shop, shopWindow, player), plugin
            ); // Отслеживаем клики игрока по данному окну

            shopWindow.ShowWindow(0, player, true); // Показываем данное окно
        }

        if (command.getName().equals("sell")) {
            final int costArg = 1;
            ItemStack sellingItem = player.getInventory().getItemInMainHand(); // Запоминаем выбранный предмет

            if (sellingItem.getType() == Material.AIR) { // Проверка на пустоту
                player.sendMessage("Рука пуста!");
                return true;
            }

            final int maxItemCount = 15;
            if (shop.PlayerItemsCount(playerData) >= maxItemCount) {
                player.sendMessage("Вы выставили слишком много предметов на продажу!");
                return true;
            }

            moneyTransfer.SellItem(Double.parseDouble(args[costArg]), playerData, sellingItem, shop); // Выставляем на продажу
            player.sendMessage("Предмет: " + sellingItem.getI18NDisplayName() + " X" + sellingItem.getAmount() +
                    ". Выставлен на торговую площадку за: " + args[costArg]+ "₽"); // Информирование
            player.getInventory().setItemInMainHand(null); // Опустошение руки
        }

        if (command.getName().equals("expired")) {
            final int secondArg = 1;
            if (args[secondArg].equals("return")) {
                Expired playerExpired = shop.PlayerExpired(playerData);

                if (playerExpired.expiredItems.size() > new PlayerWorker().getEmptySlots(player)) {
                    player.sendMessage("Недостаточно места!");
                    player.sendMessage("Необходимо: " + playerExpired.expiredItems.size() + " слота");
                    return true;
                }

                for (Item item : playerExpired.expiredItems) {
                    player.getInventory().addItem(new ItemWorker().SetLore(item.Item, ""));
                    playerExpired.expiredItems.clear();
                }
                player.sendMessage("Все вещи возвращены!");
                return true;
            }

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
