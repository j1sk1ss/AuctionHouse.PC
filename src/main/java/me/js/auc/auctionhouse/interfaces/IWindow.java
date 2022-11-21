package me.js.auc.auctionhouse.interfaces;

import org.bukkit.entity.Player;

public interface IWindow<T> {
    public void ShowWindow(Integer window, Player player, Boolean open);
    public T GetWindow();
    public void PriceSort(boolean Biggest);
    public void TimeSort();
}
