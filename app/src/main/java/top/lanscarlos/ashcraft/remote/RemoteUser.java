package top.lanscarlos.ashcraft.remote;

import java.util.Date;

public class RemoteUser {

    private int id;
    private int gender;
    private String avatar;
    private String name;
    private String signature;
    private String password;
    private double money;
    private String address;
    private String phone;
    private long regTime;

    public RemoteUser() {
    }

    public RemoteUser(int id, int gender, String avatar, String name, String signature, String password, double money, String address, String phone, long regTime) {
        this.id = id;
        this.gender = gender;
        this.avatar = avatar;
        this.name = name;
        this.signature = signature;
        this.password = password;
        this.money = money;
        this.address = address;
        this.phone = phone;
        this.regTime = regTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getRegTime() {
        return regTime;
    }

    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "RemoteUser{" +
                "id=" + id +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", regTime=" + regTime +
                '}';
    }
}
