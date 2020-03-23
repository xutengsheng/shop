package com.xts.shop.interfaces.order;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.MainPageBean;

public interface OrderContract {
    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View>{

    }
}
