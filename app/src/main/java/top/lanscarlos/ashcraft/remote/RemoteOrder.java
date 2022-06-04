package top.lanscarlos.ashcraft.remote;

import java.util.ArrayList;
import java.util.List;

public class RemoteOrder {

    private int id;
    private long purchaseTime;
    private double totalPrice = 0.0;
    private List<RemoteOrderItem> items = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RemoteOrderItem> getItems() {
        return items;
    }

    public void setItems(List<RemoteOrderItem> items) {
        this.items = items;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
