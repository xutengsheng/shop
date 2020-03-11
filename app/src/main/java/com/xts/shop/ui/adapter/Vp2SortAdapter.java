package com.xts.shop.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xts.shop.base.BaseFragment;

import java.util.ArrayList;

public class Vp2SortAdapter extends FragmentStateAdapter {
    private ArrayList<BaseFragment> mFragments;
    public Vp2SortAdapter(Fragment fragment){
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        if (mFragments == null){
            return 0;
        }
        return mFragments.size();
    }

    public void setPage(ArrayList<BaseFragment> fragments) {
        mFragments = fragments;
        notifyDataSetChanged();
    }
}
