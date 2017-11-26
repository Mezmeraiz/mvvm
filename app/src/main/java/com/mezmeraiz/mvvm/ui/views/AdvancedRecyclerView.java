package com.mezmeraiz.mvvm.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mezmeraiz.mvvm.R;
import com.mezmeraiz.mvvm.etc.MarginItemDecoration;

public class AdvancedRecyclerView extends RecyclerView{

    public AdvancedRecyclerView(Context context) {
        super(context);
        init();
    }

    public AdvancedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdvancedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecoration(new MarginItemDecoration((int) getResources()
                .getDimension(R.dimen.margin_item_decoration)));
    }
}
