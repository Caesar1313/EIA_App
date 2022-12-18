package com.example.eia_app.api;


import com.example.eia_app.models.LoginResponseModel;
import com.example.eia_app.models.SignUpModel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("api/auth/authenticate")
    Call<LoginResponseModel> login(@Body JsonObject loginModel);

    @POST("api/person")
    Call<ResponseBody> signup(@Body SignUpModel signUpModel);

    @Multipart
    @POST("/uploadFile/owner/{owner_id}")
    Call<String> uploadFile(@Part("file") MultipartBody.Part file, @Path("owner_id") int ownerId);

    @GET("/api/person/all/users")
    Call<List<String>> getAllUsers();

    @GET("/api/group")
    Call<List<String>> getAllGroups();

    @GET("api/file/check-in")
    Call<ResponseBody> checkinFile(@Query("nameFile") String fileName);

    @GET("api/file/files/in/group/{group_id}")
    Call<List<String>> getFilesInGroup(@Path("group_id") Long groupId);

    @POST("api/group/person")
    Call<ResponseBody> joinGroup(@Query("personId") Long personId, @Query("groupId") Long groupId);

    @GET("/api/file/allFiles")
    Call<List<String>> getAllFiles();

    @POST("api/file/group")
    Call<ResponseBody> addFileToGroup(@Query("file_id") Long fileId, @Query("group_id") Long groupId);

    @DELETE("api/file/group")
    Call<ResponseBody> removeFileFromGroup(@Query("file_id") Long fileId, @Query("group_id") Long groupId);
}
