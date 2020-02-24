package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserListAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppActivity extends AppCompatActivity {
    List<String> mUserList;
    RecyclerView mUserListRecycler;
    UserListAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app);
        mUserListRecycler = findViewById(R.id.recyc_UserList);
        mSwipeRefreshLayout = findViewById(R.id.swipe_userList);

        mUserList = new ArrayList<>();
        mAdapter = new UserListAdapter(WhatsAppActivity.this, mUserList, new UserListAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View v, int position) {
                Intent intent = new Intent(WhatsAppActivity.this, ChatActivity.class);
                intent.putExtra("username", mUserList.get(position).trim());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mUserListRecycler.setLayoutManager(layoutManager);
        mUserListRecycler.setAdapter(mAdapter);
        inflateData();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inflateData();
            }

        });
    }

    private void inflateData() {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                dialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                if (objects.size() <= 0 || e != null) return;
                mUserList.clear();
                for (ParseUser object : objects) {
                    mUserList.add(object.getUsername() + "");
                }
                mAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_layout:
                ParseUser.logOut();
                startActivity(new Intent(WhatsAppActivity.this, SignUpActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
