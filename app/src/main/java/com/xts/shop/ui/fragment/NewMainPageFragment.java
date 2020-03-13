package com.xts.shop.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xts.shop.R;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.common.GlideImageLoader;
import com.xts.shop.interfaces.mainpage.MainPageContract;
import com.xts.shop.interfaces.mainpage.NewMainPageContract;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.presenter.mainpage.MainPagePresenter;
import com.xts.shop.ui.adapter.NewRlvMainPageAdapter;
import com.xts.shop.ui.adapter.RlvMainPageAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewMainPageFragment extends BaseFragment<NewMainPageContract.Presenter>
        implements NewMainPageContract.View {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.srl)
    SmartRefreshLayout mSrl;
    private NewRlvMainPageAdapter mAdapter;

    public static NewMainPageFragment newInstance() {
        NewMainPageFragment fragment = new NewMainPageFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        ArrayList<MainPageBean.MainPageListBean> list = new ArrayList<>();

        mAdapter = new NewRlvMainPageAdapter(list);

        mRlv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        //监听计算当前条目占用的列表
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int type = list.get(i).currentType;
                switch (type) {
                    case MainPageBean.MainPageListBean.TYPE_BANNER:
                    case MainPageBean.MainPageListBean.TYPE_CHANNEL:
                    case MainPageBean.MainPageListBean.TYPE_TITLE:
                    case MainPageBean.MainPageListBean.TYPE_HOTGOOD:
                    case MainPageBean.MainPageListBean.TYPE_TOPIC:
                    case MainPageBean.MainPageListBean.TYPE_VIEW_LINE:
                        return 2;
                    case MainPageBean.MainPageListBean.TYPE_BRAND:
                    case MainPageBean.MainPageListBean.TYPE_CATEGORY:
                    case MainPageBean.MainPageListBean.TYPE_NEWGOOD:
                        return 1;
                }
                return 0;
            }
        });
        mAdapter.bindToRecyclerView(mRlv);

        mSrl.setEnableLoadMore(false);
        mSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mAdapter.getData().clear();
                initData();
            }
        });
    }

    @Override
    protected NewMainPageContract.Presenter initPresenter() {
        return new MainPagePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void setData(List<MainPageBean.MainPageListBean> list) {
        addHeader(list.remove(0));
        mAdapter.addData(list);
        mSrl.finishRefresh();
    }

    private void addHeader(MainPageBean.MainPageListBean head){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_banner,null);
        Banner banner = view.findViewById(R.id.banner);
        List<String> imgs = new ArrayList<>();
        for (MainPageBean.DataBean.BannerBean item : (List<MainPageBean.DataBean.BannerBean>) head.data) {
            imgs.add(item.getImage_url());
        }
        banner.tag = "true";
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgs);
        banner.start();

        mAdapter.addHeaderView(view);
    }
}
