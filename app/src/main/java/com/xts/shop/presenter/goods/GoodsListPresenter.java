package com.xts.shop.presenter.goods;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.goods.GoodsListContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.GoodsListBean;
import com.xts.shop.utils.RxUtils;

public class GoodsListPresenter extends BasePresenter<GoodsListContract.View>
        implements GoodsListContract.Presenter {
    @Override
    public void getGoodsList(int categoryId, int page, int size) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                    .getGoodsList(categoryId,page,size)
                    .compose(RxUtils.<GoodsListBean>rxScheduler())
                    .subscribeWith(new ResponceSubscriber<GoodsListBean>(mView){
                        @Override
                        public void onNext(GoodsListBean goodsListBean) {
                            if (goodsListBean.getErrno() == Constant.SUCCESS_CODE){
                                mView.setGoodsList(goodsListBean);
                            }
                        }
                    })

        );
    }

}
