package com.xts.shop.model.bean;

public class UpdateCartEvent {
    public int goodsId;
    public int productId;
    public int id;
    public int number;

    public UpdateCartEvent(int goodsId, int productId, int id, int number) {
        this.goodsId = goodsId;
        this.productId = productId;
        this.id = id;
        this.number = number;
    }
}
