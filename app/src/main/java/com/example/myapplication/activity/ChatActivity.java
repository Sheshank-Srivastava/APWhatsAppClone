package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView chatListView;
    private ArrayList<String> chatsList;
    private ArrayAdapter adapter;
    private String selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        selectedUser = getIntent().getStringExtra("username");
        getSupportActionBar().setTitle(selectedUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Toast.makeText(this, "user is selected "+ intent.getStringExtra("username"), Toast.LENGTH_SHORT).show();

        findViewById(R.id.btn_sendMessage).setOnClickListener(this);

        chatListView = findViewById(R.id.recyc_chat);
        chatsList = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,chatsList);
        chatListView.setAdapter(adapter);
        ParseQuery<ParseObject> firstUserChatQuery = ParseQuery.getQuery("chat");
        ParseQuery<ParseObject> secondUserChatQuery = ParseQuery.getQuery("chat");
        firstUserChatQuery.whereEqualTo("waSender",ParseUser.getCurrentUser().getUsername());
        firstUserChatQuery.whereEqualTo("waTargetRecipient",selectedUser);

        secondUserChatQuery.whereEqualTo("waSender",selectedUser);
        secondUserChatQuery.whereEqualTo("waTargetRecipient",ParseUser.getCurrentUser().getUsername());

        ArrayList<ParseQuery<ParseObject>> allQueries = new ArrayList<>();
        allQueries.add(firstUserChatQuery);
        allQueries.add(secondUserChatQuery);

        ParseQuery<ParseObject> myQuery = ParseQuery.or(allQueries);
        myQuery.orderByAscending("creadtedAt");
        myQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size()<=0 || e!= null) return;

                for (ParseObject object : objects) {
                    String waMessage = object.get("waMessage")+"";
                    if (object.get("waSender").equals(ParseUser.getCurrentUser().getUsername())){
                        waMessage = ParseUser.getCurrentUser().getUsername() +": "+ waMessage;
                    }
                    if (object.get("waSender").equals(selectedUser)){
                        waMessage = selectedUser +": "+ waMessage;
                    }
                    chatsList.add(waMessage);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final EditText et_Message = findViewById(R.id.et_Message);

        ParseObject chat = new ParseObject("chat");
        chat.put("waSender", ParseUser.getCurrentUser().getUsername());
        chat.put("waTargetRecipient", selectedUser);
        chat.put("waMessage",et_Message.getText().toString().trim());

        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null) return;
                chatsList.add(ParseUser.getCurrentUser().getUsername()+":"+et_Message.getText().toString().trim());
                adapter.notifyDataSetChanged();
                et_Message.setText("");
            }
        });

    }
}
