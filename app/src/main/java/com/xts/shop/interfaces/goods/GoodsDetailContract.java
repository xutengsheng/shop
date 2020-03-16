package com.xts.shop.interfaces.goods;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.GoodsDetailBean;

import java.util.List;

public interface GoodsDetailContract {
    interface View extends IBaseView{
        void setGoodsDetail(List<GoodsDetailBean.GoodsDetailListBean> list);
    }

    interface Presenter extends IBasePresenter<View>{
        void getGoodsDetail(int id);
    }
}
