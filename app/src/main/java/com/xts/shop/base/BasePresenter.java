package com.xts.shop.base;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    public V mView;
    private WeakReference<V> mReference;

    @Override
    public void attachView(V view) {
        mReference = new WeakReference<>(view);
        mView = mReference.get();
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
