package si.fri.rso.rlamp.lairbnb.lairs.models;

import java.util.Date;

public class Reservation {
    private Integer id;
    private Integer hostUserId;
    private User host;
    private Integer userUserId;
    private User user;
    private Integer lairId;
    private Lair lair;
    private Date date;
    private Integer nights;
    private Float price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHostUserId() {
        return hostUserId;
    }

    public void setHostUserId(Integer hostUserId) {
        this.hostUserId = hostUserId;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Integer getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(Integer userUserId) {
        this.userUserId = userUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLairId() {
        return lairId;
    }

    public void setLairId(Integer lairId) {
        this.lairId = lairId;
    }

    public Lair getLair() {
        return lair;
    }

    public void setLair(Lair lair) {
        this.lair = lair;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
