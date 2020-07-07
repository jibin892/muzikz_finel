package com.qboxus.musictok.Video_Recording;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.qboxus.musictok.Main_Menu.MainMenuActivity;
import com.qboxus.musictok.R;
import com.qboxus.musictok.Services.ServiceCallback;
import com.qboxus.musictok.Services.Upload_Service;
import com.qboxus.musictok.SimpleClasses.Functions;
import com.qboxus.musictok.SimpleClasses.Variables;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Post_Video_A extends AppCompatActivity implements ServiceCallback,View.OnClickListener {


    ImageView video_thumbnail;
    String video_path;
     ServiceCallback serviceCallback;
    EditText description_edit;
    private BottomSheetDialog mBottomSheetDialog,mBottomSheetDialog1;
    public  static String pic_url;
    String draft_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);

        Intent intent=getIntent();
        if(intent!=null){
            draft_file=intent.getStringExtra("draft_file");
        }


        video_path = Variables.output_filter_file;
        video_thumbnail = findViewById(R.id.video_thumbnail);


        description_edit=findViewById(R.id.description_edit);

        // this will get the thumbnail of video and show them in imageview
        Bitmap bmThumbnail;
        bmThumbnail = ThumbnailUtils.createVideoThumbnail(video_path,
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

        if (bmThumbnail != null) {
            video_thumbnail.setImageBitmap(bmThumbnail);
        } else {
        }









      findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v){
            onBackPressed();
        }
    });


     findViewById(R.id.post_btn).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v){
            progressDialog();

             Start_Service();

        }

         private void progressDialog() {





                 final View view = getLayoutInflater().inflate(R.layout.block_user, null);

                 mBottomSheetDialog = new BottomSheetDialog(Post_Video_A.this);
                 mBottomSheetDialog.setContentView(view);
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                 }

                 // set background transparent
                 ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

                 mBottomSheetDialog.show();
             mBottomSheetDialog.setCancelable(false);
             mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                     @Override
                     public void onDismiss(DialogInterface dialog) {
                         mBottomSheetDialog = null;
                     }
                 });

             }



     });


     findViewById(R.id.save_draft_btn).setOnClickListener(this);

}


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_draft_btn:
                Save_file_in_draft();
                break;
        }
    }


// this will start the service for uploading the video into database
    public void Start_Service(){

        serviceCallback=this;

        Upload_Service mService = new Upload_Service(serviceCallback);
        if (!Functions.isMyServiceRunning(this,mService.getClass())) {
            Intent mServiceIntent = new Intent(this.getApplicationContext(), mService.getClass());
            mServiceIntent.setAction("startservice");
            mServiceIntent.putExtra("uri",""+ Uri.fromFile(new File(video_path)));
            mServiceIntent.putExtra("desc",""+description_edit.getText().toString());
            startService(mServiceIntent);


            Intent intent = new Intent(this, Upload_Service.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        }
        else {
            Toast.makeText(this, "Please wait video already in uploading progress", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        Stop_Service();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }




    // when the video is uploading successfully it will restart the appliaction
    @Override
    public void ShowResponce(final String responce) {

        if(mConnection!=null)
        unbindService(mConnection);


        if(responce.equalsIgnoreCase("Your Video is uploaded Successfully")) {
             Delete_draft_file();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

//                    String a="Your Video is uploaded Successfully";
                     Toast.makeText(Post_Video_A.this, responce, Toast.LENGTH_LONG).show();
                    showsucseuss();
              //      mBottomSheetDialog.dismiss();

                    startActivity(new Intent(Post_Video_A.this, MainMenuActivity.class));

                }

                private void showsucseuss() {

                    final View view = getLayoutInflater().inflate(R.layout.sucsseus, null);
                     ((TextView) view.findViewById(R.id.submit_rating)).setText(responce.toUpperCase());
                  TextView  namesuxxsuss =view.findViewById(R.id.namesuxxsuss);

                    ImageView  susimg =view.findViewById(R.id.susimg);

                    namesuxxsuss.setText(Variables.sharedPreferences.getString(Variables.f_name, "") + " " + Variables.sharedPreferences.getString(Variables.l_name, ""));

                    pic_url = Variables.sharedPreferences.getString(Variables.u_pic, "null");

                    Picasso.get().load(pic_url).resize(200, 200).placeholder(R.drawable.profile_image_placeholder).centerCrop().into(susimg);

                    mBottomSheetDialog1 = new BottomSheetDialog(Post_Video_A.this);
                    mBottomSheetDialog1.setContentView(view);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mBottomSheetDialog1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }

                    // set background transparent
                    ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    mBottomSheetDialog1.show();
                    mBottomSheetDialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mBottomSheetDialog1 = null;
                        }
                    });




                }
            },5500);



        }
        else {
            Toast.makeText(Post_Video_A.this, responce, Toast.LENGTH_LONG).show();
            mBottomSheetDialog.dismiss();
        }
    }


    // this is importance for binding the service to the activity
    Upload_Service mService;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

           Upload_Service.LocalBinder binder = (Upload_Service.LocalBinder) service;
            mService = binder.getService();

            mService.setCallbacks(Post_Video_A.this);



        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // this function will stop the the ruuning service
    public void Stop_Service(){

        serviceCallback=this;

        Upload_Service mService = new Upload_Service(serviceCallback);

        if (Functions.isMyServiceRunning(this,mService.getClass())) {
            Intent mServiceIntent = new Intent(this.getApplicationContext(), mService.getClass());
            mServiceIntent.setAction("stopservice");
            startService(mServiceIntent);

        }


    }



    public void Save_file_in_draft(){
       File source = new File(video_path);
       File destination = new File(Variables.draft_app_folder+Functions.getRandomString()+".mp4");
        try
        {
            if(source.exists()){

                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(destination);

                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
                Crouton.makeText((Activity) Post_Video_A.this,"File saved in Draft", Style.CONFIRM).show();

                 startActivity(new Intent(Post_Video_A.this, MainMenuActivity.class));

            }else{
                 Crouton.makeText((Activity) Post_Video_A.this,"File failed to saved in Draft", Style.ALERT).show();

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public void Delete_draft_file(){
        try {
            if(draft_file!=null) {
                File file = new File(draft_file);
                file.delete();
            }
        }catch (Exception e){

        }


    }

}
