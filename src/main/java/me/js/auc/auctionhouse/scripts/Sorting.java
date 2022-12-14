package me.js.auc.auctionhouse.scripts;

import me.js.auc.auctionhouse.object.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sorting {
    public List<Item> TimeSort(List<Item> needSortList) {
        var tempList = new ArrayList<Item>(needSortList);
        Collections.reverse(tempList);
        return tempList;
    }
    public List<Item> PriceSort(boolean Biggest, List<Item> needSortList) {
        int sortType = Biggest ? 1 : -1;
        var tempList = new ArrayList<Item>(needSortList);

        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < tempList.size() - 1; j++) {
                var tempItem = tempList.get(j);
                if ((tempItem.Price / tempItem.Item.getAmount()) * sortType <
                        (tempList.get(j + 1).Price / tempList.get(j + 1).Item.getAmount()) * sortType) {
                    tempList.set(j, tempList.get(j+1));
                    tempList.set(j + 1, tempItem);
                }
            }
        }
        return tempList;
    }
}
