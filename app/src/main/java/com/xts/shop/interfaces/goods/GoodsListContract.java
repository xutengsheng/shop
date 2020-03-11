package com.xts.shop.interfaces.goods;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.GoodsListBean;

public interface GoodsListContract {
    interface View extends IBaseView{
        void setGoodsList(GoodsListBean bean);
    }

    interface Presenter extends IBasePresenter<View>{
        void getGoodsList(int categoryId,int page,int size);
    }
}
