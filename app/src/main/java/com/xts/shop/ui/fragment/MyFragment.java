package com.xts.shop.ui.fragment;

import android.util.Log;

import com.xts.shop.R;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.my.MyContract;
import com.xts.shop.model.bean.HomeBean;
import com.xts.shop.presenter.my.MyPresenter;

public class MyFragment extends BaseFragment<MyContract.Presenter> implements MyContract.View {
    @Override
    protected void initData() {
        mPresenter.getHomeData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected MyContract.Presenter initPresenter() {
        return new MyPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public void setData(HomeBean bean) {
        Log.d("MyFragment", "setData: "+bean.toString());
    }
}
