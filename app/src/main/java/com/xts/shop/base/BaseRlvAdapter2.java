package com.xts.shop.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRlvAdapter2<T> extends RecyclerView.Adapter {

    public ArrayList<T> mList;
    public Context mContext ;
    public OnItemClickListener mListener;


    public BaseRlvAdapter2(Context context, ArrayList<T> list){
        mList = list;
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(getLayout(), parent, false);
        VH vh = new VH(inflate);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onItemClick(v,vh.getLayoutPosition());
                }
            }
        });

        return vh;
    }

    protected abstract int getLayout();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        VH vh = (VH) holder;
        T t = mList.get(position);
        bindData(vh,t);
    }

    public void update(List<T> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<T> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }


    protected abstract void bindData(VH vh, T t);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        SparseArray<View> mViews;
        public VH(@NonNull View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        public View getViewById(int id){
            View view = mViews.get(id);
            if (view == null){
                view = itemView.findViewById(id);
                mViews.append(id,view);
            }

            return view;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
