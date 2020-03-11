package com.xts.shop.interfaces.sort;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.SortCurrentBean;

public interface SortItemContract {
    public interface View extends IBaseView{
        void setSortItemData(SortCurrentBean bean);
    }

    public interface Presenter extends IBasePresenter<View>{
        void getSortItemData(int id);
    }
}
