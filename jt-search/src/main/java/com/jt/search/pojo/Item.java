package com.jt.search.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by tarena on 2016/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Item {
    @Field("id")
    private Long id;
    @Field("title")
    private String title;
    @Field("price")
    private Long price;
    @Field("image")
    private String image;
    @Field("sell_point")
    private String sellPoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
