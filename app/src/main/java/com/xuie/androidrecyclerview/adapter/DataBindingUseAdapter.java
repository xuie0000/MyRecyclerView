package com.xuie.androidrecyclerview.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.androidrecyclerview.BR;
import com.xuie.androidrecyclerview.R;
import com.xuie.androidrecyclerview.entity.Movie;
import com.xuie.androidrecyclerview.entity.MoviePresenter;
import com.xuie.recyclerview.BaseQuickAdapter;
import com.xuie.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by luoxiongwen on 16/10/24.
 */

public class DataBindingUseAdapter extends BaseQuickAdapter<Movie, DataBindingUseAdapter.MovieViewHolder> {

    private MoviePresenter mPresenter;

    public DataBindingUseAdapter(int layoutResId, List<Movie> data) {
        super(layoutResId, data);

        mPresenter = new MoviePresenter();
    }

    @Override
    protected void convert(MovieViewHolder helper, Movie item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.movie, item);
        binding.setVariable(BR.presenter, mPresenter);
        binding.executePendingBindings();
        switch (helper.getLayoutPosition() %
                2) {
            case 0:
                helper.setImageResource(R.id.iv, R.mipmap.m_img1);
                break;
            case 1:
                helper.setImageResource(R.id.iv, R.mipmap.m_img2);
                break;

        }
    }

  /*  @Override
    protected MovieViewHolder createBaseViewHolder(View view) {
        return new MovieViewHolder(view);
    }
*/
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    public class MovieViewHolder extends BaseViewHolder {

        public MovieViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
