package me.js.auc.auctionhouse.lists;

import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.data.syncdata.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class Shop implements java.io.Serializable {
    public Shop() {
        shopList       = new ArrayList<>();
        playerExpireds = new ArrayList<>();
    }
    public List<Item> shopList; // Лист предметов в магазине
    public List<Expired> playerExpireds; // Лист обьектов просрочки.
    final int expiredDelayModificator = 10; // Модификатор по уменьшению срока годности

    public void TimeDecrease() { // Метод по уменьшению времени годности
        for (int i = 0; i < shopList.size(); i++) {
            var item = shopList.get(i);
            item.expiredDelay -= expiredDelayModificator;

            if (!isExpired(item)) continue;
            var tempExpired = PlayerExpired(item.OwnerData);
            tempExpired.expiredItems.add(item);
            playerExpireds.add(tempExpired);
            shopList.remove(i);
        }
    }

    private Boolean isExpired(Item item) { return item.expiredDelay < 0; }

    public Expired PlayerExpired(PlayerData playerData) { // Либо найти обьект просрочки игрока либо создать новый
        for (Expired expired: playerExpireds) {
            if (playerData.equals(expired.Owner)) {
                playerExpireds.remove(expired);
                return expired;
            }
        }
        return new Expired(playerData);
    }

    public int PlayerItemsCount(PlayerData playerData) {
        int count = 0;

        for (Item item : shopList) {
            if (playerData.equals(item.OwnerData)) {
                count++;
            }
        }

        return count;
    }
}
