package me.js.auc.auctionhouse.lists;

import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Shop implements java.io.Serializable {
    public Shop() {
        shopList = new ArrayList<>();
        playerExpireds = new ArrayList<>();
    }
    public List<Item> shopList;
    public List<Expired> playerExpireds;
    public void TimeDecrease() {
        for (int i = 0; i < shopList.size(); i++) {
            var item = shopList.get(i);
            item.expiredDelay -= 10;

            if (!isExpired(item)) continue;
            var tempExpired = PlayerExpired(item.OwnerData);
            tempExpired.expiredItems.add(item);
            playerExpireds.add(tempExpired);
            shopList.remove(i);
        }
    }
    private Boolean isExpired(Item item) { return item.expiredDelay < 0; }
    public Expired PlayerExpired(PlayerData playerData) {
        for (Expired elem: playerExpireds) {
            if (playerData.equals(elem.Owner)) {
                playerExpireds.remove(elem);
                return elem;
            }
        }
        return new Expired(playerData);
    }

}
