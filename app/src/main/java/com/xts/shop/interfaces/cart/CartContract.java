package com.xts.shop.interfaces.cart;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;

public interface CartContract {
    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View>{

    }
}
