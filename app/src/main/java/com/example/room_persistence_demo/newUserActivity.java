package com.example.room_persistence_demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newUserActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText userFirstName,userLastName,userDescription;
    private Button createUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        db = AppDatabase.getDatabaseInstance(this);

        userFirstName = findViewById(R.id.userFirstName);
        userLastName = findViewById(R.id.userLastName);
        userDescription = findViewById(R.id.userDescription);

        createUser = findViewById(R.id.createUser);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newUser = new User(getViewString(userFirstName.getId()),getViewString(userLastName.getId()),getViewString(userDescription.getId()));
                CreateNewUserTask newUserTask = new CreateNewUserTask(newUser);
                newUserTask.execute();
            }
        });
    }

    private class CreateNewUserTask extends AsyncTask<Void,Void,Void>
    {
        private User user;

        public CreateNewUserTask(User user)
        {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.userDao().insertAll(user);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
            return null;
        }
    }

    private String getViewString(int id)
    {
        return ((EditText)findViewById(id)).getText().toString();
    }
}
