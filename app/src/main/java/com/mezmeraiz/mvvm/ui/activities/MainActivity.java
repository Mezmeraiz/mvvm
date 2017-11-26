package com.mezmeraiz.mvvm.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mezmeraiz.mvvm.R;
import com.mezmeraiz.mvvm.ui.fragments.UserListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(new UserListFragment(), false, false);
    }

    private void addFragment(Fragment fragment, boolean popBackStack, boolean addToBackStack){
        FragmentManager manager = getSupportFragmentManager();
        if(popBackStack){
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if(addToBackStack)
            transaction.addToBackStack(fragment.getClass().toString());
        transaction.replace(R.id.container,fragment)
                .commit();
    }
}
