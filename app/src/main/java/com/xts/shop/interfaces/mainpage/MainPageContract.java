package com.xts.shop.interfaces.mainpage;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.MainPageBean;

public interface MainPageContract {
    interface View extends IBaseView{
        void setData(MainPageBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getData();
    }
}
