package com.mezmeraiz.mvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.mezmeraiz.mvvm.databinding.ItemUserListBinding;
import com.mezmeraiz.mvvm.etc.Utils;
import com.mezmeraiz.mvvm.network.Request;
import com.mezmeraiz.mvvm.network.model.Users;
import com.mezmeraiz.mvvm.realm.User;

import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmResults;

public class UserListViewModel extends BaseObservable{

    private Disposable disposable;
    private Context context;
    private RecyclerView recyclerView;
    public ObservableBoolean isLoading = new ObservableBoolean();

    public UserListViewModel(Context context){
        this.context = context;
    }

    private void loadUsers(){
        if(disposable != null)
            disposable.dispose();
        disposable = new Request().users()
                .map(responseBody -> new GsonBuilder().create()
                        .fromJson(responseBody.string(), Users.class))
                .map(users -> saveInRealm(users))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result ->
                        {
                            recyclerView.setAdapter(new UserListAdapter(findAllUsers()));
                            isLoading.set(false);
                        },
                        error ->
                        {
                            isLoading.set(false);
                        }
                );
    }

    private RealmResults<User> findAllUsers(){
        return Realm.getInstance(context).where(User.class).findAll();
    }

    private RealmResults<User> saveInRealm(Users users){
        Collections.shuffle(users);
        Realm realm = Realm.getInstance(context);
        RealmResults<User> results = realm.where(User.class).findAll();
        realm.beginTransaction();
        results.clear();
        realm.commitTransaction();
        for (Users.User user : users){
            realm.beginTransaction();
            User realmUser = realm.createObject(User.class);
            realmUser.setName(user.name);
            realm.commitTransaction();
        }
        return results;
    }

    public void onRefresh(){
        isLoading.set(true);
        loadUsers();
    }

    public void setupRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        RealmResults<User> users = findAllUsers();
        UserListAdapter adapter = new UserListAdapter(users);
        adapter.getPositionClicks()
                .doOnNext(user -> Utils.toast(context, user.getName()))
                .subscribe(
                        result ->
                        {

                        },
                        error ->
                        {

                        });
        if(users.size() > 0)
            recyclerView.setAdapter(adapter);
        else
            loadUsers();

    }

    public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

        private RealmResults<User> users;
        private final PublishSubject<User> onClickSubject = PublishSubject.create();

        public UserListAdapter(RealmResults<User> users) {
            this.users = users;
        }

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemUserListBinding binding = ItemUserListBinding.inflate(inflater, parent, false);
            return new UserViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(UserViewHolder holder, int position) {
            holder.binding.setUser(users.get(position));
            holder.binding.setSubject(onClickSubject);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public Observable<User> getPositionClicks(){
            return onClickSubject.hide();
        }


        public class UserViewHolder extends RecyclerView.ViewHolder {

            ItemUserListBinding binding;

            public UserViewHolder(View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }

}
