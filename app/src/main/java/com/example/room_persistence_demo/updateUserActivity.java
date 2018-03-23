package com.example.room_persistence_demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class updateUserActivity extends AppCompatActivity {

    private AppDatabase db;
    private Intent data;

    private TextView typeOfAction;
    private EditText updateFirstName,updateLastName,updateDescription;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        db = AppDatabase.getDatabaseInstance(this);
        data = getIntent();

        typeOfAction = findViewById(R.id.typeOfAction);
        updateFirstName = findViewById(R.id.userFirstName);
        updateLastName = findViewById(R.id.userLastName);
        updateDescription = findViewById(R.id.userDescription);

        update = findViewById(R.id.createUser);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserTask updateUserTask = new UpdateUserTask();
                updateUserTask.execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        typeOfAction.setText(getString(R.string.updateRecipe));
        update.setText(getString(R.string.update));

        updateFirstName.setText(data.getStringExtra("firstName"));
        updateLastName.setText(data.getStringExtra("lastName"));
        updateDescription.setText(data.getStringExtra("description"));
    }

    private String getViewString(int id)
    {
        return ((EditText)findViewById(id)).getText().toString();
    }

    private class UpdateUserTask extends AsyncTask<Void,Void,Void>
    {
        private User user;

        @Override
        protected Void doInBackground(Void... voids) {
            user = db.userDao().findUser(data.getIntExtra("id",-1));
            user.setFirstName(getViewString(updateFirstName.getId()));
            user.setLastName(getViewString(updateLastName.getId()));
            user.setDescription(getViewString(updateDescription.getId()));
            db.userDao().updateUser(user);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            return null;
        }
    }
}
