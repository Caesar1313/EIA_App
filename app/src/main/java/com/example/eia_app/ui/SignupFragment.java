package com.example.eia_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eia_app.R;
import com.example.eia_app.api.APIClient;
import com.example.eia_app.models.SignUpModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment implements View.OnClickListener{

    EditText username;
    EditText password;

    Button loginBtn;

    TextView goToLogin;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        init();
        return view;
    }

    private void init(){
        username = view.findViewById(R.id.signup_username);
        password = view.findViewById(R.id.signup_password);
        loginBtn = view.findViewById(R.id.signup_button);
        goToLogin = view.findViewById(R.id.login_tv);
        loginBtn.setOnClickListener(this);
        goToLogin.setOnClickListener(this);
    }

    private SignUpModel getSignupModel(){
        SignUpModel model = new SignUpModel();
        model.name = username.getText().toString();
        model.password = username.getText().toString();
        model.authorities.authority = "ROLE_USER";
        return model;
    }

    private void goToLogin(){
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, new LoginFragment());
        fragmentTransaction.addToBackStack("loginFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_button: {
                APIClient.getAPI().signup(getSignupModel()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(requireActivity(), "User Created", Toast.LENGTH_SHORT).show();
                            goToLogin();
                        } else
                            Toast.makeText(requireActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(requireActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case R.id.login_tv:{
                goToLogin();
            }
        }
    }

}
