package com.mezmeraiz.mvvm.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mezmeraiz.mvvm.R;
import com.mezmeraiz.mvvm.databinding.FragmentUserListBinding;
import com.mezmeraiz.mvvm.viewmodel.UserListViewModel;

public class UserListFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentUserListBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_list, container, false);
        binding.setViewModel(new UserListViewModel(getContext()));
        return binding.getRoot();
    }
}
