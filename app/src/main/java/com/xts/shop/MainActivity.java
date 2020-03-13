package com.xts.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xts.shop.base.BaseActivity;
import com.xts.shop.base.BaseFragment;
import com.xts.shop.common.Constant;
import com.xts.shop.interfaces.main.MainContract;
import com.xts.shop.presenter.main.MainPresenter;
import com.xts.shop.ui.adapter.VpMainAdpater;
import com.xts.shop.ui.fragment.CartFragment;
import com.xts.shop.ui.fragment.MainPageFragment;
import com.xts.shop.ui.fragment.MyFragment;
import com.xts.shop.ui.fragment.NewMainPageFragment;
import com.xts.shop.ui.fragment.SortFragment;
import com.xts.shop.ui.fragment.TopicFragment;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {


    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private ArrayList<BaseFragment> mFragments;
    private ArrayList<String> mTitles;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mToolBar.setTitle(R.string.title);
        setSupportActionBar(mToolBar);
        initFragments();
        initTitles();

        VpMainAdpater adapter = new VpMainAdpater(getSupportFragmentManager(), mFragments, mTitles);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);

        for (int i = 0; i < mFragments.size(); i++) {
            mTabLayout.getTabAt(i).setCustomView(tabView(i));
        }
    }

    private void initTitles() {
        mTitles = new ArrayList<>();
        mTitles.add(getResources().getString(R.string.main_page));
        mTitles.add(getResources().getString(R.string.topic));
        mTitles.add(getResources().getString(R.string.sort));
        mTitles.add(getResources().getString(R.string.cart));
        mTitles.add(getResources().getString(R.string.me));
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(NewMainPageFragment.newInstance());
        mFragments.add(TopicFragment.newInstance());
        mFragments.add(SortFragment.newInstance());
        mFragments.add(CartFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
    }

    @Override
    protected MainContract.Presenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    //对外提供tab的view
    public View tabView(int position){
        View inflate = LayoutInflater.from(this).inflate(R.layout.tab, null);
        ImageView iv = inflate.findViewById(R.id.iv);
        TextView tv = inflate.findViewById(R.id.tv);
        switch (position){
            case 0:
                //选择器,根据不同的状态选用不同的资源
                iv.setImageResource(R.drawable.se_main_page);
                break;
            case 1:
                iv.setImageResource(R.drawable.se_section);
                break;
            case 2:
                iv.setImageResource(R.drawable.se_category);
                break;
            case 3:
                iv.setImageResource(R.drawable.se_cart);
                break;
            case 4:
                iv.setImageResource(R.drawable.se_me);
                break;
        }

        tv.setText(mTitles.get(position));
        return inflate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"test");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                go2Test();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void go2Test() {
        Intent intent = new Intent(this, TestActivity.class);
        String s = Base64.encodeToString(parseBitmap2Byte(), Base64.DEFAULT);
        intent.putExtra(Constant.DATA,s);
        startActivity(intent);
    }

    public byte[] parseBitmap2Byte(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_choice_pressed);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes = baos.toByteArray();
        byte[] word = "hello world".getBytes();
        int length = bytes.length;
        bytes = concat(bytes,word);
        byte[] lenghtByte = int2Byte(length);
        bytes = concat(bytes,lenghtByte);
        return bytes;
    }

    private byte[] int2Byte(int val) {
        byte[] b = new byte[4];
        b[0] = (byte)(val & 0xff);
        b[1] = (byte)((val >> 8) & 0xff);
        b[2] = (byte)((val >> 16) & 0xff);
        b[3] = (byte)((val >> 24) & 0xff);
        return b;
    }

    //拼接数组
    private byte[] concat(byte[] bytes, byte[] word) {
        byte[] data = Arrays.copyOf(bytes, bytes.length + word.length);
        System.arraycopy(word,0,data,bytes.length,word.length);
        return data;
    }
}
