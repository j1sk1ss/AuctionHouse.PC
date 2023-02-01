package me.js.auc.auctionhouse.dataStore;

import com.google.gson.Gson;
import me.js.auc.auctionhouse.lists.Shop;

import java.io.*;
import java.util.Scanner;

public class DataWorker implements Serializable {
    public void SaveData(Shop shop) {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter("AuctionData.txt", false);

            System.out.println(shop.shopList.get(0).Item);
            gson.toJson(shop.shopList.get(0).Item.serialize(), writer);

            writer.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
    public Shop GetData() throws IOException {
        Gson gson = new Gson();

        FileReader reader = new FileReader("AuctionData.txt");
        Scanner scan = new Scanner(reader);
        reader.close();
        StringBuilder json = new StringBuilder();
        while (scan.hasNextLine()) {
            json.append(scan.nextLine());
        }

        Shop shop = gson.fromJson(String.valueOf(json), Shop.class);
        return shop;
    }
}
