package top.lanscarlos.ashcraft.remote;

public class RemoteOrderItem {

    private int id;
    private int amount;
    private double totalPrice;
    private RemoteCommodity commodity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public RemoteCommodity getCommodity() {
        return commodity;
    }

    public void setCommodity(RemoteCommodity commodity) {
        this.commodity = commodity;
    }
}
