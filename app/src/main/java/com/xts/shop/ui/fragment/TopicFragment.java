package com.xts.shop.ui.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.common.ColorDividerItemDecoration;
import com.xts.shop.interfaces.topic.TopicContract;
import com.xts.shop.model.bean.TopicBean;
import com.xts.shop.presenter.topic.TopicPresenter;
import com.xts.shop.ui.adapter.RlvTopicAdapter;
import com.xts.shop.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TopicFragment extends BaseFragment<TopicContract.Presenter> implements TopicContract.View {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.srl)
    SmartRefreshLayout mSrl;
    private int mPage = 1;
    private int mSize = 10;
    private RlvTopicAdapter mAdapter;
    private int mTotalPages;

    public static TopicFragment newInstance() {
        TopicFragment fragment = new TopicFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getTopicData(mPage, mSize);
    }

    @Override
    protected void initView() {
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<TopicBean.DataBeanX.DataBean> list = new ArrayList<>();
        mAdapter = new RlvTopicAdapter(getContext(), list);
        mRlv.setAdapter(mAdapter);
        mRlv.addItemDecoration(new ColorDividerItemDecoration(
                getResources().getColor(R.color.c_eaeaea), DpUtil.dp2px(8)
        ));


        mSrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPage < mTotalPages) {
                    mPage++;
                    mPresenter.getTopicData(mPage, mSize);
                }else {
                    mSrl.setNoMoreData(true);
                    showTips(BaseApp.getRes().getString(R.string.no_more_data));
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mAdapter.mList.clear();
                mPage = 1;
                mPresenter.getTopicData(mPage, mSize);
            }
        });
    }

    private void hideHeader() {
        mSrl.finishLoadMore();
        mSrl.finishRefresh();
    }

    @Override
    protected TopicContract.Presenter initPresenter() {
        return new TopicPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_topic;
    }

    @Override
    public void setData(TopicBean bean) {
        TopicBean.DataBeanX dataBeanX = bean.getData();
        mTotalPages = dataBeanX.getTotalPages();
        List<TopicBean.DataBeanX.DataBean> data = dataBeanX.getData();
        if (data != null && data.size() > 0) {
            mAdapter.addData(data);
        }
        hideHeader();
    }
}
