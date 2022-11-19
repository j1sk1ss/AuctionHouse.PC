package me.js.auc.auctionhouse.interfaces;

import me.js.auc.auctionhouse.ui.ExpiredWindow;
import me.js.auc.auctionhouse.ui.ShopWindow;
import org.bukkit.entity.Player;

public interface IWindow<T> {
    public void ShowWindow(Integer window, Player player, Boolean open);
    public T GetWindow();
}
