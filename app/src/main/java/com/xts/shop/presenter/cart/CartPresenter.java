package com.xts.shop.presenter.cart;

import com.xts.shop.base.BasePresenter;
import com.xts.shop.common.Constant;
import com.xts.shop.common.ResponceSubscriber;
import com.xts.shop.interfaces.cart.CartContract;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.model.HttpManager;
import com.xts.shop.model.bean.AddCartBean;
import com.xts.shop.utils.RxUtils;

public class CartPresenter extends BasePresenter<CartContract.View> implements CartContract.Presenter {
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
                                    mView.setCart(bean);
                                }
                            }
                        })

        );
    }

    @Override
    public void modifyCartChecked(int checked, String productIds) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                        .modifyCartChecked(checked,productIds)
                        .compose(RxUtils.<AddCartBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<AddCartBean>(mView) {
                            @Override
                            public void onNext(AddCartBean bean) {

                            }
                        })

        );
    }

    @Override
    public void deleteCart(String productIds) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                        .deleteCart(productIds)
                        .compose(RxUtils.<AddCartBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<AddCartBean>(mView) {
                            @Override
                            public void onNext(AddCartBean bean) {
                                if (bean.getErrno() == Constant.SUCCESS_CODE) {
                                    mView.setDelete(bean);
                                }
                            }
                        })

        );
    }

    @Override
    public void updateCart(int goodsId, int productId, int id, int number) {
        addDisposable(
                HttpManager.getInstance().getApiService()
                        .updateCart(goodsId,productId,id,number)
                        .compose(RxUtils.<AddCartBean>rxScheduler())
                        .subscribeWith(new ResponceSubscriber<AddCartBean>(mView) {
                            @Override
                            public void onNext(AddCartBean bean) {
                                if (bean.getErrno() == Constant.SUCCESS_CODE) {
                                    mView.setDelete(bean);
                                }
                            }
                        })

        );
    }
}
