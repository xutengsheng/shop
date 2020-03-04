package com.xts.shop.ui.fragment;

import com.xts.shop.R;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.interfaces.cart.CartContract;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.presenter.cart.CartPresenter;
import com.xts.shop.presenter.mainpage.MainPagePresenter;

public class CartFragment extends BaseFragment<CartContract.Presenter> implements CartContract.View {
    public static CartFragment newInstance(){
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected CartContract.Presenter initPresenter() {
        return new CartPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_cart;
    }
}
