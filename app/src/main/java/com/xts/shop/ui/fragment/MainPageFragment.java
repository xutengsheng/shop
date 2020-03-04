package com.xts.shop.ui.fragment;

import com.xts.shop.R;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.presenter.mainpage.MainPagePresenter;

public class MainPageFragment extends BaseFragment<MainPageContract.Presenter> implements MainContract.View {
    public static MainPageFragment newInstance(){
        MainPageFragment fragment = new MainPageFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected MainPageContract.Presenter initPresenter() {
        return new MainPagePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_page;
    }
}
