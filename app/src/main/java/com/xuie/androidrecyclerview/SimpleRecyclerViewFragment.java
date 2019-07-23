package com.xuie.androidrecyclerview;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.xuie.recyclerview.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleRecyclerViewFragment extends Fragment {
    static final String TAG = SimpleRecyclerViewFragment.class.getSimpleName();
    static final String TYPE = "type";
    public static int DEFAULT = 0;
    public static int EXAMPLE1 = 1;
    public static int EXAMPLE2 = 2;

    RecyclerView.LayoutManager layoutManager;
    ArrayAdapter arrayAdapter;
    @BindView(R.id.recycler) RecyclerView recycler;

    public static SimpleRecyclerViewFragment newInstance(int type) {
        SimpleRecyclerViewFragment fragment = new SimpleRecyclerViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> strings = getData();

        int type = getArguments().getInt(TYPE);
        if (type == DEFAULT) {
            arrayAdapter = new ArrayAdapter<>(android.R.layout.simple_list_item_1, strings);
        } else if (type == EXAMPLE1) {
            arrayAdapter = new ArrayAdapter<>(R.layout.custom_item, R.id.text, strings);
            arrayAdapter.setAnimation(ArrayAdapter.ANI_RIGHT_IN);
            arrayAdapter.setOnceAnimation(true);
        } else if (type == EXAMPLE2) {
            arrayAdapter = new ArrayAdapter<>(R.layout.custom_item2, R.id.text, strings);
            arrayAdapter.setCustomAnimation(R.anim.item_custom);
            arrayAdapter.setOnItemClickListener(new ArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Snackbar.make(view, "点击Item " + position, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
            arrayAdapter.setOnItemLongClickListener(new ArrayAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(View view, int position) {
                    Snackbar.make(view, "长按Item " + position, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return true;
                }
            });
        }

        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(arrayAdapter);
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("Item:" + i);
        }
        return list;
    }
}
