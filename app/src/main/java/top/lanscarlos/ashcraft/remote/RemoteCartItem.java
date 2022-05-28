package top.lanscarlos.ashcraft.remote;

public class RemoteCartItem {

    private int id;
    private RemoteCommodity commodity;
    private int amount;
    private double totalPrice;

    public RemoteCartItem() {
    }

    public RemoteCartItem(int id, RemoteCommodity commodity, int amount, double totalPrice) {
        this.id = id;
        this.commodity = commodity;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RemoteCommodity getCommodity() {
        return commodity;
    }

    public void setCommodity(RemoteCommodity commodity) {
        this.commodity = commodity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "RemoteCartItem{" +
                "id=" + id +
                ", commodity=" + commodity +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
