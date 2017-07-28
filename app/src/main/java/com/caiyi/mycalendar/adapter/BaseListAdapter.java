package com.caiyi.mycalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本List类型Adapter
 * @param <T> list中数据类型
 * @author CJL
 * @since 2015-03-23
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    /** LayoutInflater **/
    private LayoutInflater mInflater;
    /** 数据 **/
    private ArrayList<T> mDatas = new ArrayList<T>();

    /**
     * Constructor
     *
     * @param inflater
     *            LayoutInflater
     */
    public BaseListAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    /**
     * 更新数据
     *
     * @param datas
     *            数据
     * @param isAdd
     *            true 添加到末尾，false覆盖
     */
    public void updateData(List<T> datas, boolean isAdd) {
        if (datas == null) {
            return;
        }
        if (!isAdd) {
            mDatas.clear();
        }
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @return LayoutInflater
     **/
    public LayoutInflater getInflater() {
        return mInflater;
    }

    /**
     * @return Context
     **/
    public Context getContext() {
        return mInflater == null ? null : mInflater.getContext();
    }

    /**
     * @return 获取所有数据
     **/
    public ArrayList<T> getAllDatas() {
        return mDatas;
    }
}
