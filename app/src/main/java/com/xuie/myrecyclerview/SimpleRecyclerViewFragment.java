package com.xuie.myrecyclerview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.recyclerview.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleRecyclerViewFragment extends Fragment {
    static final String TAG = SimpleRecyclerViewFragment.class.getSimpleName();
    static final String TYPE = "type";
    public static int DEFAULT = 0;
    public static int CUSTOM = 1;
    public static int CARD = 2;

    RecyclerView.LayoutManager layoutManager;
    ArrayAdapter arrayAdapter;
    @Bind(R.id.recycler)
    RecyclerView recycler;

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
            arrayAdapter = new ArrayAdapter<String>(android.R.layout.simple_list_item_1, strings);
        } else if (type == CUSTOM) {
            arrayAdapter = new ArrayAdapter<>(R.layout.custom_item, R.id.text, strings);
            arrayAdapter.setAnimation(ArrayAdapter.ANI_RIGHT_IN);
        } else if (type == CARD) {
            arrayAdapter = new ArrayAdapter<>(R.layout.custom_item2, R.id.text, strings);
            arrayAdapter.setAnimation(ArrayAdapter.ANI_BOTTOM_IN);
        }

        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(arrayAdapter);
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item:" + i);
        }
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
