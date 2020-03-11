package com.xts.shop.interfaces.sort;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.SortTabBean;

public interface SortContract {
    interface View extends IBaseView{
        void setTabBean(SortTabBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getTabBean();
    }
}
