package com.xts.shop.interfaces.search;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.GoodListBean;

public interface SearchContract {
    interface View extends IBaseView{
        void setSearchData(GoodListBean bean);
    }

    interface Presenter extends IBasePresenter<View>{

        void search(String content, int page, int size, String sort, String order, int categoryId);
    }
}
