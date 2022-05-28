package top.lanscarlos.ashcraft.remote;

public class RemoteShop {

    private int id;
    private String name;
    private String password;
    private String avatar;
    private String description;
    private String contact;
    private long regTime;

    public RemoteShop() {
    }

    public RemoteShop(int id, String name, String password, String avatar, String description, String contact, long regTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.description = description;
        this.contact = contact;
        this.regTime = regTime;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getRegTime() {
        return regTime;
    }

    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "RemoteSeller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                ", regTime=" + regTime +
                '}';
    }
}
