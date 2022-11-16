package me.js.auc.auctionhouse;

import me.js.auc.auctionhouse.object.Item;
import me.yic.xconomy.data.syncdata.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Expired {

    public Expired(PlayerData owner) {
        Owner = owner;
        expiredList = new ArrayList<Item>();
    }
    public PlayerData Owner;
    public List<Item> expiredList;

    public void GetItem(Integer uniqId) {
        for (int i = 0; i < expiredList.size(); i++) {
            Item item = expiredList.get(i);

            if (!Objects.equals(uniqId, item.UniqId)) continue;
            expiredList.remove(i);

            Player player = (Player) Owner;
            player.getInventory().addItem(item.Item);
            break;
        }
    }
}
