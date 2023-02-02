package me.js.auc.auctionhouse.trading;

import me.js.auc.auctionhouse.object.Item;
import org.bukkit.entity.Player;

public class Trade {
    Trade(Player seller, Player buyer, Item item) {
        Seller = seller;
        Buyer  = buyer;
        Item   = item;
    }
    Player Seller;
    Player Buyer;
    Item Item;
}
