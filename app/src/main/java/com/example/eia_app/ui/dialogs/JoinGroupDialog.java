package com.example.eia_app.ui.dialogs;

import static com.example.eia_app.config.AppSharedPreferences.GET_USER_ID;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.eia_app.R;
import com.example.eia_app.api.APIClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinGroupDialog extends Dialog implements View.OnClickListener {

    EditText groupId;
    Button joinGroupBtn;

    public JoinGroupDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.join_group_dialog);
        init();
    }

    private void init(){
        groupId = findViewById(R.id.group_id);
        joinGroupBtn = findViewById(R.id.join_btn);
        joinGroupBtn.setOnClickListener(this);
    }

    @Override
    public void show() {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wlp);
        // match width dialog
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.show();
    }

    @Override
    public void onClick(View v) {
        APIClient.getAPI().joinGroup(GET_USER_ID(), Long.parseLong(groupId.getText().toString())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "joined group", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
