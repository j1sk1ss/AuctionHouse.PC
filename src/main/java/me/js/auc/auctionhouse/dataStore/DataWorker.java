package me.js.auc.auctionhouse.dataStore;

import com.google.gson.Gson;
import me.js.auc.auctionhouse.lists.Shop;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Scanner;

public class DataWorker implements Serializable {
    public void SaveData(Shop shop) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(shop);
            FileWriter writer = new FileWriter("AuctionData.txt", false);
            writer.write(json);
            writer.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
    public Shop GetData() throws FileNotFoundException {
        Gson gson = new Gson();

        FileReader reader = new FileReader("AuctionData.txt");
        Scanner scan = new Scanner(reader);

        StringBuilder json = new StringBuilder();
        while (scan.hasNextLine()) {
            json.append(scan.nextLine());
        }

        Shop shop = gson.fromJson(String.valueOf(json), Shop.class);
        return shop;
    }
}
