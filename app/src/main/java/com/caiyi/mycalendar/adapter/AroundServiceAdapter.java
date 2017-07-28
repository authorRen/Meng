package com.caiyi.mycalendar.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.compant.WebActivity;
import com.caiyi.mycalendar.data.AroundServiceData;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by RZQ on 2017/5/12.
 */

public class AroundServiceAdapter extends BaseListAdapter<AroundServiceData> {

    /**
     * Constructor
     *
     * @param inflater LayoutInflater
     */
    public AroundServiceAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_aroundservice_item, parent, false);
        }

        SimpleDraweeView view = ViewHolder.get(convertView, R.id.aroundservice_img);
        final AroundServiceData aroundServiceData = getAllDatas().get(position);
        if (null != aroundServiceData) {
            if(!TextUtils.isEmpty(aroundServiceData.allServiceImg)) {
                view.setImageURI(Uri.parse(aroundServiceData.allServiceImg));
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), aroundServiceData.title, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }
}
