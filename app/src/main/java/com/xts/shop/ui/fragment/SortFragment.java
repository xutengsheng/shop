package com.xts.shop.ui.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xts.shop.R;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.interfaces.sort.SortContract;
import com.xts.shop.model.bean.SortTabBean;
import com.xts.shop.presenter.sort.SortPresenter;
import com.xts.shop.ui.adapter.Vp2SortAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class SortFragment extends BaseFragment<SortContract.Presenter> implements SortContract.View {
    @BindView(R.id.tabLayout)
    VerticalTabLayout mTabLayout;
    @BindView(R.id.vp2)
    ViewPager2 mVp2;
    private Vp2SortAdapter mAdapter;

    public static SortFragment newInstance(){
        SortFragment fragment = new SortFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getTabBean();
    }

    @Override
    protected void initView() {
        mVp2.setUserInputEnabled(false);
        mAdapter = new Vp2SortAdapter(this);
        mVp2.setAdapter(mAdapter);

        mVp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.setTabSelected(position);
            }
        });

        mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                mVp2.setCurrentItem(mTabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

    }


    @Override
    protected SortContract.Presenter initPresenter() {
        return new SortPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sort;
    }

    @Override
    public void setTabBean(SortTabBean bean) {
        List<SortTabBean.DataBean.CategoryListBean> categoryList =
                bean.getData().getCategoryList();

        mTabLayout.setTabAdapter(new SimpleTabAdapter() {
            @Override
            public int getCount() {
                return categoryList.size();
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(categoryList.get(position).getName())
                        .build();
            }
        });

        initFragments(categoryList);
    }

    private void initFragments(List<SortTabBean.DataBean.CategoryListBean> categoryList) {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            SortTabBean.DataBean.CategoryListBean bean = categoryList.get(i);
            fragments.add(SortItemFragment.newInstance(bean.getId()));
        }

        mAdapter.setPage(fragments);
    }
}
