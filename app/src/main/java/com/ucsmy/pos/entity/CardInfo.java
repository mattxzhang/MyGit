package com.ucsmy.pos.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */

public class CardInfo implements Serializable{
    private String request_id;
    private List<Card> cards;
    private String image_id;
    private int time_used;
    private String error_message;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
