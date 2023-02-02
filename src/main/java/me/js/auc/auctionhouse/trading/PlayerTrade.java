package me.js.auc.auctionhouse.trading;

import me.js.auc.auctionhouse.object.Item;
import me.js.auc.auctionhouse.scripts.MoneyTransfer;
import me.js.auc.auctionhouse.scripts.PluginManager;

import me.yic.xconomy.api.XConomyAPI;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerTrade {

    public PlayerTrade(MoneyTransfer moneyTransfer, XConomyAPI xConomyAPI) {
        ServerTrades       = new ArrayList<>();
        this.moneyTransfer = moneyTransfer;
        this.xConomyAPI    = xConomyAPI;
    }

    private final List<Trade> ServerTrades;
    private final MoneyTransfer moneyTransfer;
    private final XConomyAPI xConomyAPI;

    public void CreateTradeOffer(Player seller, Player buyer, Item item) {
        ServerTrades.add(new Trade(seller, buyer, item));
    }

    public void DeleteTradeOffer(Player player) {
        for (Trade trade : ServerTrades) {
            if (trade.Seller == player || trade.Buyer == player) {

                trade.Seller.sendMessage("Ваш запрос был отклонён.");
                trade.Buyer.sendMessage("Запрос отклонён.");

                ServerTrades.remove(trade);
                break;
            }
        }

        player.sendMessage("Нет каких-либо предложений.");
    }

    public void AcceptTradeOffer(Player buyer) {
        for (Trade trade : ServerTrades) {
            if (trade.Buyer == buyer) {

                if (trade.Item.Item.equals(trade.Seller.getInventory().getItemInMainHand())) {
                    moneyTransfer.BuyItem(buyer, new PluginManager().GetPlayerData(buyer.getUniqueId(), xConomyAPI),
                            trade.Item, null);
                    trade.Seller.getInventory().remove(trade.Item.Item);
                    ServerTrades.remove(trade);
                    return;
                }

                trade.Buyer.sendMessage("Предмет не найден в руке у продавца.");
                trade.Seller.sendMessage("Положите предмет в главную руку!");
                break;
            }
        }

        buyer.sendMessage("Нет входящих предложений.");
    }
}
