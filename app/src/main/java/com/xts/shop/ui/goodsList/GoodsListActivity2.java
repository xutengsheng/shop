package com.xts.shop.ui.goodsList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xts.shop.R;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.goods.GoodsListContract2;
import com.xts.shop.model.bean.GoodListBean;
import com.xts.shop.model.bean.SortCurrentBean;
import com.xts.shop.presenter.goods.GoodsListPresenter2;
import com.xts.shop.ui.adapter.RlvGoodsListAdapter2;

import java.util.ArrayList;

import butterknife.BindView;

public class GoodsListActivity2 extends BaseActivity<GoodsListContract2.Presenter>
    implements GoodsListContract2.View{
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.srl)
    SmartRefreshLayout mSrl;
    private ArrayList<SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean> mList;
    private int mPosition;
    private int mCategoryId;
    private int mPage = 1;
    private int mSize = 20;
    private RlvGoodsListAdapter2 mAdapter;
    private TextView mTvTitle;
    private TextView mTvSubTitle;

    @Override
    protected void initData() {
        mPresenter.getData(mCategoryId,mPage,mSize);
    }

    @Override
    protected void initView() {
        mList = (ArrayList<SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean>) getIntent().getSerializableExtra(Constant.DATA);
        mPosition = getIntent().getIntExtra(Constant.POSITION, 0);

        //1.tablayout
        for (int i = 0; i < mList.size(); i++) {
            SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean bean = mList.get(i);
            mTabLayout.addTab(mTabLayout.newTab().setText(bean.getName()));
        }

        //选中我们点击的tab,并且滑动到屏幕范围内
        mTabLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTabLayout.getTabAt(mPosition).select();
            }
        },100);
        mCategoryId = mList.get(mPosition).getId();
        //tabLayout的切换监听,切换tab切换数据
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中哪个切换对应的数据
                mPosition = tab.getPosition();
                mCategoryId = mList.get(mPosition).getId();
                mPage = 1;
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                mPresenter.getData(mCategoryId,mPage,mSize);

                setHeaderData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //2.recyclerview
        ArrayList<GoodListBean.DataBeanX.DataBean> list = new ArrayList<>();
        mRlv.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new RlvGoodsListAdapter2(R.layout.item_item_news_good, list);
        mAdapter.bindToRecyclerView(mRlv);

        addHeader();

        mSrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //加载更多
                mPage++;
                mPresenter.getData(mCategoryId,mPage,mSize);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新的
                mAdapter.getData().clear();
                mPage = 1;
                mPresenter.getData(mCategoryId,mPage,mSize);
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                go2GoodsDetail(position);
            }
        });
    }

    private void go2GoodsDetail(int position) {
        Intent intent = new Intent(this,GoodsDetailActivity.class);
        intent.putExtra(Constant.DATA,mAdapter.getData().get(position).getId());

        startActivity(intent);
    }

    private void addHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_goods_list_header,null);
        mTvTitle = header.findViewById(R.id.tv_title);
        mTvSubTitle = header.findViewById(R.id.tv_subtitle);
        setHeaderData();
        mAdapter.addHeaderView(header);
    }

    private void setHeaderData() {
        SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean bean = mList.get(mPosition);
        mTvSubTitle.setText(bean.getFront_name());
        mTvTitle.setText(bean.getFront_desc());
    }

    @Override
    protected GoodsListContract2.Presenter initPresenter() {
        return new GoodsListPresenter2();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_list2;
    }

    @Override
    public void setData(GoodListBean bean) {
        mAdapter.addData(bean.getData().getData());
        finishLoadMoreAndRefresh();
    }

    private void finishLoadMoreAndRefresh() {
        mSrl.finishRefresh();
        mSrl.finishLoadMore();
    }
}
