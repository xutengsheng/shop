package com.xts.shop.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xts.shop.R;
import com.xts.shop.apps.BaseApp;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.IBasePresenter;
import com.xts.shop.interfaces.my.MyContract;
import com.xts.shop.model.bean.HomeBean;
import com.xts.shop.model.bean.LoginBean;
import com.xts.shop.presenter.my.MyPresenter;
import com.xts.shop.ui.login.LoginActivity;
import com.xts.shop.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    public void setData(HomeBean bean) {
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
