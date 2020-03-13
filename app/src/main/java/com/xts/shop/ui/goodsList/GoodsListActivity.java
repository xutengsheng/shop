package com.xts.shop.ui.goodsList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.xts.shop.R;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.goods.GoodsListContract;
import com.xts.shop.model.bean.GoodsListBean;
import com.xts.shop.model.bean.SortCurrentBean;
import com.xts.shop.presenter.goods.GoodsListPresenter;
import com.xts.shop.ui.adapter.RlvGoodsListAdapter;
import com.xts.shop.utils.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsListActivity extends BaseActivity<GoodsListContract.Presenter> implements GoodsListContract.View  {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private RlvGoodsListAdapter mAdapter;
    private ArrayList<SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean> mData;
    private int mPage = 1;
    private int mSize = 20;
    private int mCategoryId;
    private int mClickPosition;

    @Override
    protected void initData() {
        mPresenter.getGoodsList(mCategoryId,mPage,mSize);
    }

    @Override
    protected void initView() {
        mData = (ArrayList<SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean>) getIntent().getSerializableExtra(Constant.DATA);
        mClickPosition = getIntent().getIntExtra(Constant.POSITION, 0);

        tab();

        mCategoryId = mData.get(mClickPosition).getId();
        ArrayList<GoodsListBean.DataBeanX.DataBean> list = new ArrayList<>();
        //mRlv.setLayoutManager(new GridLayoutManager(this,2));
        mRlv.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new RlvGoodsListAdapter(this, list);
        mRlv.setAdapter(mAdapter);

        View inflate = LayoutInflater.from(this).inflate(R.layout.item_goods_list_header, null);
        TextView tvTitle = inflate.findViewById(R.id.tv_title);
        TextView tvSubTitle = inflate.findViewById(R.id.tv_subtitle);
        mAdapter.addHeaderView(inflate);

        tvTitle.setText(mData.get(mClickPosition).getName());
        tvSubTitle.setText(mData.get(mClickPosition).getFront_name());

        mTabLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTabLayout.getTabAt(mClickPosition).select();
            }
        },100);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mCategoryId = mData.get(position).getId();
                mAdapter.mList.clear();
                mPage = 1;
                initData();
                tvTitle.setText(mData.get(position).getName());
                tvSubTitle.setText(mData.get(position).getFront_name());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void tab() {
        for (int i = 0; i < mData.size(); i++) {
            SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean bean = mData.get(i);
            mTabLayout.addTab(mTabLayout.newTab().setText(bean.getName()));
        }
    }

    @Override
    protected GoodsListContract.Presenter initPresenter() {
        return new GoodsListPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void setGoodsList(GoodsListBean bean) {
        List<GoodsListBean.DataBeanX.DataBean> list = bean.getData().getData();
        mAdapter.addData(list);
    }
}
