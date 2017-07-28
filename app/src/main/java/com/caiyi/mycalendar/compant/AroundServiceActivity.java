package com.caiyi.mycalendar.compant;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.adapter.AroundServiceAdapter;
import com.caiyi.mycalendar.apiService.ServeApiService;
import com.caiyi.mycalendar.model.DetailServeModel;
import com.caiyi.mycalendar.retrofit.ApiServiceFactory;
import com.caiyi.mycalendar.retrofit.model.HttpResults;
import com.caiyi.mycalendar.retrofit.subscriber.HttpResultsObserver;
import com.caiyi.mycalendar.rx.RxUtil;


public class AroundServiceActivity extends BaseActivity{

    /** 显示图片列表 */
    private ListView mListContainer;
    /** listView适配器 */
    private AroundServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_service);

        initToolbar();
        initViews();
        initAroundData();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.circum_service));
    }

    private void initViews() {
        mListContainer = (ListView) findViewById(R.id.lv_service_around);
        mListContainer.setDivider(null);
        adapter = new AroundServiceAdapter(getLayoutInflater());
        mListContainer.setAdapter(adapter);
    }

    /**
     * 获取周边服务详细夜数据
     */
    private void initAroundData() {
        ServeApiService serveApiService = ApiServiceFactory.getInstance().createService(ServeApiService.class);
        serveApiService.getAllAroundService("1", "021")
                .compose(RxUtil.<HttpResults<DetailServeModel>>defaultSingleSchedulers())
                .subscribe(new HttpResultsObserver<DetailServeModel>() {
                    @Override
                    public void onSuccess(DetailServeModel detailServeModel, String msg) {
                        if (null == detailServeModel) return;
                        adapter.updateData(detailServeModel.aroundervice, false);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        showToast(msg);
                    }
                });
    }
}
