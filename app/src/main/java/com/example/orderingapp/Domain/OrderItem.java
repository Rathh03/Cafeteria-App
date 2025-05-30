package com.example.orderingapp.Domain;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
    private int id;
    private String title;
    private String picUrl;
    private double price;
    private int quantity;

    public OrderItem(int id, String title, String picUrl, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.picUrl = picUrl;
        this.price = price;
        this.quantity = quantity;
    }

    protected OrderItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        picUrl = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(picUrl);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }

    // Getters here (optional setters)
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getPicUrl() { return picUrl; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
