package com.xts.shop.interfaces.goods;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.GoodsListBean;

public interface GoodsListContract2 {
    interface View extends IBaseView{
        void setData(GoodsListBean bean);
    }
    interface Presenter extends IBasePresenter<View>{
        void getData(int categoryId,int page,int size);
    }
}
