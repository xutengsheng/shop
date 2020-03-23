package com.xts.shop.presenter.goods;

import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.goods.GoodsDetailContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.GoodsDetailBean;
import com.xts.shop.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.View>
        implements GoodsDetailContract.Presenter {
    @Override
    public void getGoodsDetail(int id) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                        .getGoodDetail(id)
                        .compose(RxUtils.<GoodsDetailBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<GoodsDetailBean>(mView) {
                            @Override
                            public void onNext(GoodsDetailBean bean) {
                                mView.setGoodsDetail(bean);
                            }
                        })

        );
    }

    @Override
    public void addCart(int goodsId, int productId, int number) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                        .addCart(goodsId, productId, number)
                        .compose(RxUtils.<AddCartBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<AddCartBean>(mView) {
                            @Override
                            public void onNext(AddCartBean bean) {
                                if (bean.getErrno() == Constant.SUCCESS_CODE) {
                                    mView.addCartResult(bean);
                                    mView.showTips(BaseApp.getRes().getString(R.string.add_cart_success));
                                }
                            }
                        })

        );
    }

    @Override
    public void getCart() {
        addDisposable(
                HttpManager.getInstance().getApiService()
                        .getCart()
                        .compose(RxUtils.<AddCartBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<AddCartBean>(mView) {
                            @Override
                            public void onNext(AddCartBean bean) {
                                if (bean.getErrno() == Constant.SUCCESS_CODE) {
                                    mView.addCartResult(bean);
                                }
                            }
                        })

        );
    }

}
