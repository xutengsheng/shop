package com.xts.shop.interfaces.goods;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.GoodsDetailBean;

import java.util.List;

public interface GoodsDetailContract {
    interface View extends IBaseView{
        void setGoodsDetail(GoodsDetailBean bean);
        void addCartResult(AddCartBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getGoodsDetail(int id);
        void addCart(int goodsId,int productId,int number);
        void getCart();
    }
}
