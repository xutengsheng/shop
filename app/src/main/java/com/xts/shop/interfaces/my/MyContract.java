package com.xts.shop.interfaces.my;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.HomeBean;

public interface MyContract {
    interface View extends IBaseView{
        void setData(HomeBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getHomeData();
    }
}
