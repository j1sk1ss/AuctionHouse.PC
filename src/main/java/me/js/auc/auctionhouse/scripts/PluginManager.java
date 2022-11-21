package me.js.auc.auctionhouse.scripts;

import me.yic.xconomy.api.XConomyAPI;
import me.yic.xconomy.data.syncdata.PlayerData;

import java.util.UUID;

public class PluginManager {
    public PlayerData GetPlayerData(UUID playerName, XConomyAPI xConomyAPI) {
        return xConomyAPI.getPlayerData(playerName);
    }
}
