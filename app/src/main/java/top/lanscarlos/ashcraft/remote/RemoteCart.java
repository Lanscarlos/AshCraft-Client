package top.lanscarlos.ashcraft.remote;

import java.util.Set;

public class RemoteCart {

    private int id;
    private double totalPrice;
    private Set<RemoteCartItem> items;

    public RemoteCart() {
    }

    public RemoteCart(int id, double totalPrice, Set<RemoteCartItem> items) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<RemoteCartItem> getItems() {
        return items;
    }

    public void setItems(Set<RemoteCartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "RemoteCart{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", items=" + items +
                '}';
    }
}
