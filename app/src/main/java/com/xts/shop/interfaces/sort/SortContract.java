package com.xts.shop.interfaces.sort;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;

public interface SortContract {
    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View>{

    }
}
