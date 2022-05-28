package top.lanscarlos.ashcraft.remote;

public class RemoteCommodity {

    private int id;
    private String name;
    private String description;
    private double price;
    private int storage;
    private String image;
    private String category;
    private RemoteShop seller;

    public RemoteCommodity() {
    }

    public RemoteCommodity(int id, String name, String introduce, double price, int storage, String image, String category, RemoteShop seller) {
        this.id = id;
        this.name = name;
        this.description = introduce;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public RemoteShop getSeller() {
        return seller;
    }

    public void setSeller(RemoteShop seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "RemoteCommodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduce='" + description + '\'' +
                ", price=" + price +
                ", storage=" + storage +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", seller=" + seller +
                '}';
    }
}
