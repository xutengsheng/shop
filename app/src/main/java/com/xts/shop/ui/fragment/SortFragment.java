package com.xts.shop.ui.fragment;

import com.xts.shop.R;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.interfaces.sort.SortContract;
import com.xts.shop.presenter.sort.SortPresenter;

public class SortFragment extends BaseFragment<SortContract.Presenter> implements SortContract.View {
    public static SortFragment newInstance(){
        SortFragment fragment = new SortFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected SortContract.Presenter initPresenter() {
        return new SortPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sort;
    }
}
