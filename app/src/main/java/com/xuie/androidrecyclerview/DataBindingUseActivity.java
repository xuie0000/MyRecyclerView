package com.xuie.androidrecyclerview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xuie.androidrecyclerview.adapter.DataBindingUseAdapter;
import com.xuie.androidrecyclerview.base.BaseActivity;
import com.xuie.androidrecyclerview.entity.Movie;
import com.xuie.androidrecyclerview.util.ToastUtils;
import com.xuie.recyclerview.BaseQuickAdapter;
import com.xuie.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBindingUseActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    DataBindingUseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("DataBinding Use");
        setContentView(R.layout.activity_data_binding_use);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mAdapter = new DataBindingUseAdapter(R.layout.item_movie, genData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener<Movie>() {
            @Override
            public void onItemClick(BaseQuickAdapter<Movie, ? extends BaseViewHolder> adapter, View view, int position) {
                ToastUtils.showShortToast("onItemClick");
            }
        });
        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener<Movie>() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter<Movie, ? extends BaseViewHolder> adapter, View view, int position) {
                ToastUtils.showShortToast("onItemChildLongClick");
                return true;
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener<Movie>() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter<Movie, ? extends BaseViewHolder> adapter, View view, int position) {
                ToastUtils.showShortToast("onItemLongClick");
                return true;
            }
        });
    }


    private List<Movie> genData() {
        ArrayList<Movie> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String name = "Chad";
            int price = random.nextInt(10) + 10;
            int len = random.nextInt(80) + 60;
            Movie movie = new Movie(name, len, price, "He was one of Australia's most distinguished artistes");
            list.add(movie);
        }
        return list;
    }
}