package me.js.auc.auctionhouse.interfaces;

import me.js.auc.auctionhouse.ui.ShopWindow;
import org.bukkit.entity.Player;

public interface IWindow {
    public void ShowWindow(Integer window, Player player);
    public ShopWindow GetShopWindow();
}
