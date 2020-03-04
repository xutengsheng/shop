package com.xts.shop.interfaces.mainpage;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;

public interface MainPageContract {
    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View>{

    }
}
