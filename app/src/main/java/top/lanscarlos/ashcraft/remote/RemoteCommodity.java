package top.lanscarlos.ashcraft.remote;

public class RemoteCommodity {

    private int id;
    private String name;
    private String introduce;
    private double price;
    private int storage;
    private String image;
    private String category;
    private RemoteSeller seller;

    public RemoteCommodity(int id, String name, String introduce, double price, int storage, String image, String category, RemoteSeller seller) {
        this.id = id;
        this.name = name;
        this.introduce = introduce;
        this.price = price;
        this.storage = storage;
        this.image = image;
        this.category = category;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public RemoteSeller getSeller() {
        return seller;
    }

    public void setSeller(RemoteSeller seller) {
        this.seller = seller;
    }
}
