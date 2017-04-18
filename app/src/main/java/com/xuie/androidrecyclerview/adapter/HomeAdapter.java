package com.xuie.androidrecyclerview.adapter;

import com.xuie.androidrecyclerview.R;
import com.xuie.androidrecyclerview.entity.HomeItem;
import com.xuie.recyclerview.BaseQuickAdapter;
import com.xuie.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.text, item.getTitle());
        helper.setImageResource(R.id.icon, item.getImageResource());
    }
}
