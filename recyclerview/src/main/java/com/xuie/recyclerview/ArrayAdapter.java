package com.xuie.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuie on 16-1-15.
 */
public class ArrayAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ANI_NONE = 0;
    public static final int ANI_BOTTOM_IN = 1;
    public static final int ANI_RIGHT_IN = 2;
    public static final int ANI_CUSTOM = 3;

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    private int mResource;
    private int mFieldId = 0;
    private List<T> mObjects;
    private int mLastPosition = -1;
    private int mAnimationType;
    private int mAnimationId;
    private boolean mEnableOnceAnimation = false;

    public ArrayAdapter(@LayoutRes int resource, @NonNull T[] objects) {
        this(resource, 0, objects);
    }

    public ArrayAdapter(@LayoutRes int resource, @IdRes int fieldId, @NonNull T[] objects) {
        this(resource, fieldId, Arrays.asList(objects));
    }

    public ArrayAdapter(@LayoutRes int resource, @NonNull List<T> objects) {
        this(resource, 0, objects);
    }

    public ArrayAdapter(@LayoutRes int resource, @IdRes int fieldId, @NonNull List<T> objects) {
        this.mResource = resource;
        this.mFieldId = fieldId;
        this.mObjects = objects;
        setAnimation(ANI_NONE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T item = mObjects.get(position);
        TextView text;
        try {
            if (mFieldId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView) holder.itemView;
            } else {
                //  Otherwise, find the TextView field within the layout
                text = (TextView) holder.itemView.findViewById(mFieldId);
            }
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        if (item instanceof CharSequence) {
            text.setText((CharSequence) item);
        } else {
            text.setText(item.toString());
        }
        startAnimation(holder.itemView, position);
    }

    public void setAnimation(int type) {
        if (type >= ANI_NONE && type <= ANI_RIGHT_IN) {
            mAnimationType = type;
            if (type == ANI_BOTTOM_IN) {
                mAnimationId = R.anim.item_bottom_in;
            } else if (type == ANI_RIGHT_IN) {
                mAnimationId = R.anim.item_right_in;
            }
        }
    }

    public void setCustomAnimation(int animId) {
        mAnimationId = animId;
        mAnimationType = ANI_CUSTOM;
    }

    private void startAnimation(View viewToAnimate, int position) {
        if (mAnimationType == ANI_NONE)
            return;

        if (!mEnableOnceAnimation || position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), mAnimationId);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    public void setOnceAnimation(boolean mEnableOnceAnimation) {
        this.mEnableOnceAnimation = mEnableOnceAnimation;
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null && getAdapterPosition() != -1) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return mOnItemLongClickListener != null && getAdapterPosition() != -1 && mOnItemLongClickListener.onItemLongClick(v, getAdapterPosition());
        }
    }
}
