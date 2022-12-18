package com.example.eia_app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.eia_app.R;
import com.example.eia_app.api.APIClient;
import com.example.eia_app.ui.dialogs.AddFileToGroupDialog;
import com.example.eia_app.ui.dialogs.CheckinDialog;
import com.example.eia_app.ui.dialogs.GetFilesInGroupDialog;
import com.example.eia_app.ui.dialogs.JoinGroupDialog;
import com.example.eia_app.ui.dialogs.RemoveFileFromGroupDialog;
import com.example.eia_app.ui.dialogs.ResponseDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button uploadBtn;
    Button checkinBtn;
    Button checkoutBtn;
    Button filesInGroupBtn;
    Button joinGroupButton;
    Button getAllUsersBtn;
    Button getAllGroupsBtn;
    Button getAllFilesBtn;
    Button addFileToGroupBtn;
    Button removeFileFromGroupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        uploadBtn = findViewById(R.id.upload_btn);
        checkinBtn = findViewById(R.id.checkin_btn);
        checkoutBtn = findViewById(R.id.checkout_btn);
        filesInGroupBtn = findViewById(R.id.files_in_group_btn);
        getAllUsersBtn = findViewById(R.id.all_users_btn);
        getAllGroupsBtn = findViewById(R.id.all_groups_btn);
        joinGroupButton = findViewById(R.id.join_group_btn);
        getAllFilesBtn = findViewById(R.id.all_files_btn);
        addFileToGroupBtn = findViewById(R.id.add_file_to_group_btn);
        removeFileFromGroupBtn = findViewById(R.id.remove_file_from_group_btn);
        addFileToGroupBtn.setOnClickListener(this);
        removeFileFromGroupBtn.setOnClickListener(this);
        getAllFilesBtn.setOnClickListener(this);
        joinGroupButton.setOnClickListener(this);
        getAllGroupsBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        checkinBtn.setOnClickListener(this);
        checkoutBtn.setOnClickListener(this);
        filesInGroupBtn.setOnClickListener(this);
        getAllUsersBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_btn:{

            }
            case R.id.checkin_btn:{
                new CheckinDialog(this).show();
                break;
            }
            case R.id.checkout_btn:{

                break;
            }
            case R.id.files_in_group_btn:{
                new GetFilesInGroupDialog(this, getSupportFragmentManager()).show();
                break;
            }
            case R.id.join_group_btn:{
                new JoinGroupDialog(this).show();
                break;
            }
            case R.id.all_groups_btn:{
                APIClient.getAPI().getAllGroups().enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        ResponseDialog dialog = new ResponseDialog(response.body().toString());
                        dialog.show(getSupportFragmentManager(), "dialog");
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case R.id.all_users_btn:{
                APIClient.getAPI().getAllUsers().enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        ResponseDialog dialog = new ResponseDialog(response.body().toString());
                        dialog.show(getSupportFragmentManager(), "dialog");
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case R.id.all_files_btn:{
                APIClient.getAPI().getAllFiles().enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if(response.isSuccessful()){
                            ResponseDialog dialog = new ResponseDialog(response.body().toString());
                            dialog.show(getSupportFragmentManager(), "dialog");
                        } else
                            Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

                    }
                });
                break;
            }
            case R.id.add_file_to_group_btn:{
                new AddFileToGroupDialog(this).show();
                break;
            }
            case R.id.remove_file_from_group_btn:{
                new RemoveFileFromGroupDialog(this).show();
                break;
            }
        }
    }
}