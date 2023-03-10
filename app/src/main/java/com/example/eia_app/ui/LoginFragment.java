package com.example.eia_app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eia_app.R;
import com.example.eia_app.api.APIClient;
import com.example.eia_app.config.AppSharedPreferences;
import com.example.eia_app.helper.FileHelper;
import com.example.eia_app.models.LoginResponseModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener{

    EditText username;
    EditText password;

    Button loginBtn;

    View view;

    Button testFiles;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        init();
        return view;
    }

    private void init(){
        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        loginBtn = view.findViewById(R.id.login_button);
        testFiles = view.findViewById(R.id.test);
        testFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileHelper.getInstance().init(requireActivity());
                FileHelper.getInstance().setLauncher(launcher);
                FileHelper.getInstance().pickFile("*/*");
            }
        });
        loginBtn.setOnClickListener(this);
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
        }
    });


    private JsonObject getLoginJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());
        return jsonObject;
    }

    private void cacheData(String token, Long userId){
        AppSharedPreferences.CACHE_AUTH_DATA(token);
        AppSharedPreferences.CACHE_USER(userId);
    }

    private void openMainActivity() {
        startActivity(new Intent(requireActivity(), MainActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onClick(View v) {
        APIClient.getAPI().login(getLoginJsonObject()).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if(response.isSuccessful()){
                    cacheData(response.body().getJwtToken(), response.body().getUserId());
                    openMainActivity();
                } else
                    Toast.makeText(requireActivity(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(requireActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
