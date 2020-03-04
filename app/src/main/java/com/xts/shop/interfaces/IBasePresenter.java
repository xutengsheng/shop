package com.xts.shop.interfaces;

public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);
    void detachView();
}
