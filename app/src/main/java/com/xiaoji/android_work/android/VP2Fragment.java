package com.xiaoji.android_work.android;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoji.android_work.BaseFragment;
import com.xiaoji.android_work.R;

import java.util.ArrayList;

public class VP2Fragment extends BaseFragment {
    RecyclerView recyclerView;
    HomeAdapter adapter;
    ArrayList<HomeBean> list=new ArrayList<>();
    int page=-1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.clear();
        list.addAll((ArrayList<HomeBean>) getArguments().getSerializable("list"));
        page=getArguments().getInt("page");
        Log.e("aa","onCreate:"+"  page:"+page);
    }
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.vp_fragment, container, false);
        initView(mRootView);
        return mRootView;
    }

    private void initView(View mRootView) {
        recyclerView=mRootView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        adapter=new HomeAdapter(R.layout.home_item,list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.e("aa","onStop:"+"  page:"+page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("aa","onDestroy:"+"  page:"+page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("aa","onDestroyView:"+"  page:"+page);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("aa","onDetach:"+"  page:"+page);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void refreshList(ArrayList<HomeBean> list){
        this.list.clear();
        this.list.addAll(list);
        if (null!=adapter){
            adapter.notifyDataSetChanged();
        }
    }
}
