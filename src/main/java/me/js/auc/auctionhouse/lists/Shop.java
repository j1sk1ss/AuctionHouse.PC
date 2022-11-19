package me.js.auc.auctionhouse.lists;

import me.js.auc.auctionhouse.object.Item;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    public Shop() {
        shopList = new ArrayList<>();
        expireds = new ArrayList<>();
    }
    public List<Item> shopList;
    public List<Expired> expireds;
    public void TimeDecrease() {
        for (int i = 0; i < shopList.size(); i++) {
            Item item = shopList.get(i);
            item.expiredDelay -= 10;
            if (!isExpired(item)) continue;
            Expired tempExpired = PlayerExpired(item);
                tempExpired.expiredList.add(item);
                expireds.add(tempExpired);
            shopList.remove(i);
        }
    }
    private Boolean isExpired(Item item) { return item.expiredDelay < 0; }
    private Expired PlayerExpired(Item item) {
        for (Expired elem:expireds) {
            if (item.Owner.equals(elem.Owner)) return elem;
        }
        return new Expired(item.Owner);
    }

}
