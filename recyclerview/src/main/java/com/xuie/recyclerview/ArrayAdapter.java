package com.xuie.recyclerview;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

    private int mResource;
    private int mFieldId = 0;
    private List<T> mObjects;
    private int lastPosition = -1;
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
            mAnimType = type;
            if (type == ANI_BOTTOM_IN) {
                mAnimId = R.anim.item_bottom_in;
            } else if (type == ANI_RIGHT_IN) {
                mAnimId = R.anim.item_right_in;
            }
        }
    }

    private void startAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            if (mAnimType != ANI_NONE) {
                Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), mAnimId);
                viewToAnimate.startAnimation(animation);
            }
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
