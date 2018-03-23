package com.example.room_persistence_demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class userDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private Intent data;

    private TextView detailsUserFirstName,detailsUserLastName,detailsUserDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        db = AppDatabase.getDatabaseInstance(this);
        data = getIntent();

        detailsUserFirstName = findViewById(R.id.detailsFirstName);
        detailsUserLastName = findViewById(R.id.detailsLastName);
        detailsUserDescription = findViewById(R.id.detailsUserDescription);
    }

    @Override
    protected void onStart() {
        super.onStart();

        detailsUserFirstName.setText("First Name: ".concat(data.getStringExtra("firstName")));
        detailsUserLastName.setText("Last Name: ".concat(data.getStringExtra("lastName")));
        detailsUserDescription.setText("Description: ".concat(data.getStringExtra("description")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.updateUser:
                Intent currentUserData = new Intent(getApplicationContext(),updateUserActivity.class);
                currentUserData.putExtras(new Bundle(data.getExtras()));
                startActivity(currentUserData);
                break;
            case R.id.deleteUser:
                DeleteUserTask deleteUserTask = new DeleteUserTask();
                deleteUserTask.execute();
                break;
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
        return true;
    }

    private class DeleteUserTask extends AsyncTask<Void,Void,Void>
    {
        private User deleteUser;

        @Override
        protected Void doInBackground(Void... voids) {
            deleteUser =  db.userDao().findUser(data.getIntExtra("id",-1));
            db.userDao().delete(deleteUser);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
            return null;
        }
    }
}
