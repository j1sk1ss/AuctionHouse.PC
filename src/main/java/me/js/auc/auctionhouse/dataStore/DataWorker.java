package me.js.auc.auctionhouse.dataStore;

import me.js.auc.auctionhouse.lists.Shop;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

public class DataWorker {
    public void SaveData(Shop shop) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("market.json"), shop);
        } catch (Exception ignored) {}
    }
    public Shop GetData(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = new Shop();
        try {
            shop = objectMapper.readValue(jsonData, Shop.class);
        } catch (Exception ignored) {}
        return shop;
    }
}
