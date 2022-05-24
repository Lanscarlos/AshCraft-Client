package top.lanscarlos.ashcraft.remote;

import java.util.Set;

public class RemoteCart {

    private int id;
    private RemoteUser user;
    private double totalPrice;
    private Set<RemoteCartItem> items;

    public RemoteCart(int id, RemoteUser user, double totalPrice, Set<RemoteCartItem> items) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RemoteUser getUser() {
        return user;
    }

    public void setUser(RemoteUser user) {
        this.user = user;
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
}
