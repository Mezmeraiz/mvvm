package com.mezmeraiz.mvvm.etc;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.mezmeraiz.mvvm.viewmodel.UserListViewModel;

public class Bindings {

    @BindingAdapter("recyclerViewViewModel")
    public static void setRecyclerViewViewModel(RecyclerView recyclerView,
                                                UserListViewModel viewModel) {
        viewModel.setupRecyclerView(recyclerView);
    }

}
