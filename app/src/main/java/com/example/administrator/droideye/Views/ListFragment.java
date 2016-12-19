package com.example.administrator.droideye.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.example.administrator.droideye.R;


public class ListFragment extends android.support.v4.app.ListFragment {

    private SimpleAdapter adapter;
    private AdapterView.OnItemClickListener itemClickListener;
    private AdapterView.OnItemLongClickListener itemLongClickListener;

    public ListFragment() {
        // Required empty public constructor
    }

    public void init(SimpleAdapter adapter, @Nullable AdapterView.OnItemClickListener listener1, @Nullable AdapterView.OnItemLongClickListener listener2) {
        this.adapter = adapter;
        this.itemClickListener = listener1;
        this.itemLongClickListener = listener2;
    }

    public static ListFragment newInstance(SimpleAdapter adapter, @Nullable AdapterView.OnItemClickListener listener1, @Nullable AdapterView.OnItemLongClickListener listener2) {
        ListFragment fragment = new ListFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
        fragment.init(adapter,listener1,listener2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setListAdapter(adapter);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(itemClickListener);
        getListView().setOnItemLongClickListener(itemLongClickListener);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
