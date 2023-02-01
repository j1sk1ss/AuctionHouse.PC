package me.js.auc.auctionhouse.dataStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.js.auc.auctionhouse.lists.Shop;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class DataWorker implements Serializable {
    public void SaveData(Shop shop) {
        try {
            FileWriter writer = new FileWriter("AuctionData.txt", false);

            new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ItemStackTypeAdaptor())
                    .create()
                    .toJson(shop, writer);

            writer.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public Shop GetData() throws IOException {
        try {
            String json = new String(Files.readAllBytes(Paths.get("AuctionData.txt")));
            System.out.println(json);

            return new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ItemStackTypeAdaptor())
                    .create()
                    .fromJson(json, Shop.class);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null;
    }
}
