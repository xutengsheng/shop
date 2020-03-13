package com.xts.shop.ui.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xts.shop.R;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.presenter.mainpage.MainPagePresenter;
import com.xts.shop.ui.adapter.RlvMainPageAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class MainPageFragment extends BaseFragment<MainPageContract.Presenter>
        implements MainPageContract.View {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.srl)
    SmartRefreshLayout mSrl;
    private RlvMainPageAdapter mAdapter;

    public static MainPageFragment newInstance(){
        MainPageFragment fragment = new MainPageFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        ArrayList<MainPageBean.DataBean.BannerBean> banner = new ArrayList<>();
        ArrayList<MainPageBean.DataBean.ChannelBean> channel = new ArrayList<>();
        ArrayList<MainPageBean.DataBean.NewGoodsListBean> newGoodsList = new ArrayList<>();
        ArrayList<MainPageBean.DataBean.HotGoodsListBean> hotGoodsList = new ArrayList<>();
        ArrayList<MainPageBean.DataBean.BrandListBean> brandList = new ArrayList<>();
        ArrayList<MainPageBean.DataBean.TopicListBean> topicList = new ArrayList<>();
        ArrayList<MainPageBean.DataBean.CategoryListBean> categoryList = new ArrayList<>();

        mAdapter = new RlvMainPageAdapter(getContext(), banner, channel,
                newGoodsList, hotGoodsList, brandList, topicList, categoryList);

        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.setAdapter(mAdapter);

        mSrl.setEnableLoadMore(false);
        mSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    @Override
    protected MainPageContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void setData(MainPageBean bean) {
        mAdapter.addData(bean);
        mSrl.finishRefresh();
    }
}
