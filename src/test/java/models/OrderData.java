package models;

public class OrderData {
    public final String name;
    public final String surname;
    public final String address;
    public final String metro;
    public final String phone;
    public final int day;
    public final String period;
    public final boolean black;
    public final boolean grey;
    public final String comment;

    public OrderData(String name, String surname, String address, String metro, String phone, int day, String period, boolean black, boolean grey, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.day = day;
        this.period = period;
        this.black = black;
        this.grey = grey;
        this.comment = comment;
    }
}
