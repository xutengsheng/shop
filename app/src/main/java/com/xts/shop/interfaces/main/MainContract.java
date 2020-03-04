package com.xts.shop.interfaces.main;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.HomeBean;

public interface MainContract {
    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View>{

    }
}
