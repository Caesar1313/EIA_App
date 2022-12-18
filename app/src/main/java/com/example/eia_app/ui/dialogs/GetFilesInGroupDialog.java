package com.example.eia_app.ui.dialogs;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFilesInGroupDialog extends Dialog implements View.OnClickListener{

    EditText groupId;
    Button getFilesInGroupBtn;
    FragmentManager fragmentManager;

    public GetFilesInGroupDialog(@NonNull Context context, FragmentManager fragmentManager) {
        super(context);
        this.fragmentManager = fragmentManager;
        setContentView(R.layout.files_in_group_dialog);
        init();
    }

    private void init(){
        groupId = findViewById(R.id.group_id);
        getFilesInGroupBtn = findViewById(R.id.get_btn);
        getFilesInGroupBtn.setOnClickListener(this);
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
        APIClient.getAPI().getFilesInGroup(Long.parseLong(groupId.getText().toString())).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    ResponseDialog dialog = new ResponseDialog(response.body().toString());
                    dialog.show(fragmentManager, "dialog");
                    dismiss();
                } else
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }
}
