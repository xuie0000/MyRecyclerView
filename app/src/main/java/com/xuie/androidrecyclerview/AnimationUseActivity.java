package com.xuie.androidrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kyleduo.switchbutton.SwitchButton;
import com.xuie.androidrecyclerview.adapter.AnimationAdapter;
import com.xuie.androidrecyclerview.animation.CustomAnimation;
import com.xuie.androidrecyclerview.entity.Status;
import com.xuie.recyclerview.BaseQuickAdapter;
import com.xuie.recyclerview.BaseViewHolder;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * <p>
 * modify by AllenCoder
 */
public class AnimationUseActivity extends Activity {
    private RecyclerView mRecyclerView;
    private AnimationAdapter mAnimationAdapter;
    private ImageView mImgBtn;
    private int mFirstPageItemCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_use);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        initMenu();
        initView();
    }

    private void initView() {

        mImgBtn = (ImageView) findViewById(R.id.img_back);
        mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
    }

    private void initAdapter() {
        mAnimationAdapter = new AnimationAdapter();
        mAnimationAdapter.openLoadAnimation();
        mAnimationAdapter.setNotDoAnimationCount(mFirstPageItemCount);
        mAnimationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener<Status>() {
            @Override
            public void onItemChildClick(BaseQuickAdapter<Status, ? extends BaseViewHolder> adapter, View view, int position) {
                String content = null;
                Status status = adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.img:
                        content = "img:" + status.getUserAvatar();
                        Toast.makeText(AnimationUseActivity.this, content, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.tweetName:
                        content = "name:" + status.getUserName();
                        Toast.makeText(AnimationUseActivity.this, content, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.tweetText:
                        content = "tweetText:" + status.getUserName();
                        Toast.makeText(AnimationUseActivity.this, content, Toast.LENGTH_LONG).show();
                        // you have set clickspan .so there should not solve any click event ,just empty
                        break;

                }
            }
        });
        mRecyclerView.setAdapter(mAnimationAdapter);
    }

    private void initMenu() {
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                        break;
                    case 1:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        break;
                    case 2:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                        break;
                    case 3:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                        break;
                    case 4:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                        break;
                    case 5:
                        mAnimationAdapter.openLoadAnimation(new CustomAnimation());
                        break;
                    default:
                        break;
                }
                mRecyclerView.setAdapter(mAnimationAdapter);
            }
        });
        SwitchButton switchButton = (SwitchButton) findViewById(R.id.switch_button);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    mAnimationAdapter.isFirstOnly(true);
                } else {
                    mAnimationAdapter.isFirstOnly(false);
                }
                mAnimationAdapter.notifyDataSetChanged();
            }
        });

    }

}
