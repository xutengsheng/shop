package com.xts.shop.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.IBaseView;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mPresenter = initPresenter();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract P initPresenter();

    protected abstract int getLayout();

    @Override
    public void showTips(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }
}
