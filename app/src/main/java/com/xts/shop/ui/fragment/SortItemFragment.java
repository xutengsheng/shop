package com.xts.shop.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.base.BaseRlvAdapter;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.sort.SortItemContract;
import com.xts.shop.model.bean.SortCurrentBean;
import com.xts.shop.presenter.sort.SortItemPresenter;
import com.xts.shop.ui.adapter.RlvSortItemAdapter;
import com.xts.shop.ui.goodsList.GoodsListActivity;
import com.xts.shop.ui.goodsList.GoodsListActivity2;

import java.util.ArrayList;

import butterknife.BindView;

public class SortItemFragment extends BaseFragment<SortItemContract.Presenter> implements SortItemContract.View {
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private RlvSortItemAdapter mAdapter;

    public static SortItemFragment newInstance(int id){
        SortItemFragment fragment = new SortItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.DATA,id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        int id = getArguments().getInt(Constant.DATA);
        mPresenter.getSortItemData(id);
    }

    @Override
    protected void initView() {
        mRlv.setLayoutManager(new GridLayoutManager(getContext(),3));
        ArrayList<SortCurrentBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list = new ArrayList<>();
        mAdapter = new RlvSortItemAdapter(getContext(), list);
        mRlv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRlvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                go2GoodsListActivity(position);
            }
        });
    }

    private void go2GoodsListActivity(int position) {
        Intent intent = new Intent(getContext(), GoodsListActivity2.class);
        intent.putExtra(Constant.DATA,mAdapter.mList);
        intent.putExtra(Constant.POSITION,position);
        startActivity(intent);
    }

    @Override
    protected SortItemContract.Presenter initPresenter() {
        return new SortItemPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sort_item;
    }

    @Override
    public void setSortItemData(SortCurrentBean bean) {
        SortCurrentBean.DataBean.CurrentCategoryBean currentCategory =
                bean.getData().getCurrentCategory();
        Glide.with(getContext()).load(currentCategory.getBanner_url()).into(mIv);
        String line = BaseApp.getRes().getString(R.string.line);
        mTv.setText(line +currentCategory.getName()+BaseApp.getRes().getString(R.string.sort)+line);

        mAdapter.update(bean.getData().getCurrentCategory().getSubCategoryList());

    }
}
