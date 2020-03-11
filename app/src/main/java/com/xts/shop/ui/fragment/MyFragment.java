package com.xts.shop.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.base.BaseRlvAdapter;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.my.MyContract;
import com.xts.shop.model.bean.MainPageBean;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.presenter.my.MyPresenter;
import com.xts.shop.ui.login.LoginActivity;
import com.xts.shop.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseFragment<MyContract.Presenter> implements MyContract.View {
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rlv)
    RecyclerView mRlv;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getHomeData();
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        String avatar = SpUtils.getInstance().getString(Constant.AVATAR);
        String name = SpUtils.getInstance().getString(Constant.USER_NAME);
        if (!TextUtils.isEmpty(name)) {
            mTvName.setText(name);
        }

        RequestOptions options = new RequestOptions().circleCrop();
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(getContext()).load(avatar).apply(options).into(mIvHead);
        } else {
            Glide.with(getContext()).load(R.drawable.header_place).apply(options).into(mIvHead);
        }

        rlv();
    }

    private void rlv() {

        ArrayList<String> list = new ArrayList<>();
        list.add(BaseApp.getRes().getString(R.string.my_order));
        list.add(BaseApp.getRes().getString(R.string.coupon));
        list.add(BaseApp.getRes().getString(R.string.gift_card));
        list.add(BaseApp.getRes().getString(R.string.my_collect));
        list.add(BaseApp.getRes().getString(R.string.my_trace));
        list.add(BaseApp.getRes().getString(R.string.welfare));
        list.add(BaseApp.getRes().getString(R.string.address_manager));
        list.add(BaseApp.getRes().getString(R.string.account_safe));
        list.add(BaseApp.getRes().getString(R.string.call_customer_service));
        list.add(BaseApp.getRes().getString(R.string.help_centre));
        list.add(BaseApp.getRes().getString(R.string.feedback));

        mRlv.setLayoutManager(new GridLayoutManager(getContext(),3));
        mRlv.setAdapter(new BaseRlvAdapter<String>(getContext(),list) {
            @Override
            protected int getLayout() {
                return R.layout.item_me;
            }

            @Override
            protected void bindData(VH vh, String o) {
                TextView tv = (TextView) vh.getViewById(R.id.tv);
                tv.setText(o);
            }
        });
    }

    @Override
    protected MyContract.Presenter initPresenter() {
        return new MyPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public void setData(MainPageBean bean) {
        Log.d("MyFragment", "setData: " + bean.toString());
    }

    @OnClick({R.id.iv_head, R.id.tv_name})
    public void onClick(View v) {
        if (BaseApp.sLogined) {
            go2UserDetail();
        } else {
            go2Login();
        }
    }

    private void go2Login() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    private void go2UserDetail() {

    }

    @Subscribe()
    public void receive(LoginBean.DataBean.UserInfoBean bean) {
        mTvName.setText(bean.getNickname());
        RequestOptions options = new RequestOptions().circleCrop();
        if (!TextUtils.isEmpty(bean.getAvatar())) {
            Glide.with(getContext()).load(bean.getAvatar()).apply(options).into(mIvHead);
        } else {
            Glide.with(getContext()).load(R.drawable.header_place).apply(options).into(mIvHead);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
