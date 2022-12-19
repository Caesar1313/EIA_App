package com.example.eia_app.helper;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileHelper {

    private static FileHelper instance;
    private Activity activity;

    public static FileHelper getInstance() {
        if (instance == null) {
            instance = new FileHelper();
        }
        return instance;
    }

    public void init(Activity activity) {
        this.activity = activity;
    }

   public void pickFile(String fileRegex) {

        // in our assignment the regex have to be a '*/*'

        if (PermissionsHelper.getREAD_EXTERNAL_STORAGE(activity))
            launcher.launch("*/*");
    }

    public void setLauncher(ActivityResultLauncher<String> launcher) {
        this.launcher = launcher;
    }

    private ActivityResultLauncher<String> launcher;


    public static String createImageFilePath(Activity activity, Uri uri) {
        try {
            // Create an image file name
            String timeStamp = DateConverter.getTimeStampAs("yyyyMMdd_HH_mm_ss");
            String imageFileName = "eia" + timeStamp + "_";

            File storageDir = activity.getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            String exe =activity.getContentResolver().getType(uri).split("/")[1];
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    "."+exe,         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents

            String path = image.getAbsolutePath();
            writeByteAsFile(activity,uri,path,2048);
            return path;
        } catch (IOException e) {
            Log.e("createImageFilePath", "createImageFilePath: ",e );
        }
        return "";
    }

    public static void writeByteAsFile(Activity activity,Uri uri,String filePath,int bufferCapacity) {

        try {
            InputStream in =  activity.getContentResolver().openInputStream(uri);
            OutputStream out = new FileOutputStream(filePath);
            byte[] buf = new byte[bufferCapacity];
            int len;
            while(true){
                len=in.read(buf);
                if(len < 0) break;
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }catch (Exception e){
            Log.e("writeByteAsFile", "writeByteAsFile: ",e );
        }
    }

    public MultipartBody.Part buildMultiPartFile(Activity activity,Uri uri){
        String path = FileHelper.createImageFilePath(activity,uri);
        File file = new File(path);
        RequestBody photoBody = HttpRequestConverter.createFileAsRequestBody("multipart/form-data",file);
        return HttpRequestConverter.createFormDataFile("file",file.getName(),photoBody);
    }




    /**
     * this is an example of how to implement a launcher methods
     * note that you have to implement them on activity context
     */
//    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//        @Override
//        public void onActivityResult(Uri result) {
//
//            /**
//             * here we get the file content as uri data in result var
//             */
//
//        }
//    });

}
