package com.xuie.androidrecyclerview.adapter;

import com.xuie.androidrecyclerview.R;
import com.xuie.recyclerview.BaseItemDraggableAdapter;
import com.xuie.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by luoxw on 2016/6/20.
 */
public class ItemDragAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    public ItemDragAdapter(List data) {
        super(R.layout.item_draggable_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        switch (helper.getLayoutPosition() %
                3) {
            case 0:
                helper.setImageResource(R.id.iv_head, R.mipmap.head_img0);
                break;
            case 1:
                helper.setImageResource(R.id.iv_head, R.mipmap.head_img1);
                break;
            case 2:
                helper.setImageResource(R.id.iv_head, R.mipmap.head_img2);
                break;
        }
        helper.setText(R.id.tv, item);
    }
}
