package com.xuie.recyclerview;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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

    OnItemClickListener mOnItemClickListener;

    OnItemLongClickListener mOnItemLongClickListener;

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
    private int mAnimType;
    private int mAnimId;

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
        return new MyHolder(LayoutInflater.from(parent.getContext())
                .inflate(mResource, parent, false));
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
            mAnimType = type;
            if (type == ANI_BOTTOM_IN) {
                mAnimId = R.anim.item_bottom_in;
            } else if (type == ANI_RIGHT_IN) {
                mAnimId = R.anim.item_right_in;
            }
        }
    }

    public void setCustomAnimation(int animId) {
        mAnimId = animId;
        mAnimType = ANI_CUSTOM;
    }

    private void startAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            if (mAnimType != ANI_NONE) {
                Animation animation = AnimationUtils
                        .loadAnimation(viewToAnimate.getContext(), mAnimId);
                viewToAnimate.startAnimation(animation);
            }
            mLastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return mOnItemLongClickListener != null
                    && mOnItemLongClickListener.onItemLongClick(v, getAdapterPosition());
        }
    }
}
