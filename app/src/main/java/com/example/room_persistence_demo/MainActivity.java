package com.example.room_persistence_demo;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private UserAdapter userAdapter;
    private ListView list;
    private ArrayList<User> users = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabaseInstance(this);

        list = findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = (User) adapterView.getItemAtPosition(i);
                Intent data = new Intent(getApplicationContext(),userDetailsActivity.class);
                data.putExtra("id",user.getUserId());
                data.putExtra("firstName",user.getFirstName());
                data.putExtra("lastName",user.getLastName());
                data.putExtra("description",user.getDescription());
                startActivity(data);
            }
        });

        userAdapter = new UserAdapter(getApplicationContext(),R.layout.user_item,users);

        FetchAllUsersTask fetchAllUsersTask = new FetchAllUsersTask();
        fetchAllUsersTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.newUser:
                startActivity(new Intent(getApplicationContext(),newUserActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class UserAdapter extends ArrayAdapter<User>
    {
        private ArrayList<User> users;
        private int textViewResourceId;

        public UserAdapter(Context context,int textViewResourceId, ArrayList<User> users)
        {
            super(context,textViewResourceId,users);
            this.textViewResourceId = textViewResourceId;
            this.users = users;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            TextView firstName,lastName;
            if(view == null)
            {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(textViewResourceId,null);

            }
            User user = users.get(position);

            firstName = view.findViewById(R.id.firstName);
            lastName = view.findViewById(R.id.lastName);

            firstName.setText("First Name: ".concat(user.getFirstName()));
            lastName.setText("Last Name: ".concat(user.getLastName()));

            return view;
        }
    }

    private class FetchAllUsersTask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            users.addAll(db.userDao().getAll());
            list.setAdapter(userAdapter);
            return null;
        }
    }
}
