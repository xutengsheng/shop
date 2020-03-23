package com.xts.shop.interfaces.cart;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.AddCartBean;

public interface CartContract {
    interface View extends IBaseView{
        void setCart(AddCartBean bean);

        void setDelete(AddCartBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getCart();

        void modifyCartChecked(int checked, String productIds);

        void deleteCart(String productIds);

        void updateCart(int goodsId, int productId, int id, int number);
    }
}
