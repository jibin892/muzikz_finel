package com.qboxus.musictok.Video_Recording;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.qboxus.musictok.R;
import com.qboxus.musictok.SimpleClasses.Variables;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AACTrackImpl;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by AQEEL on 2/15/2019.
 */

// this is the class which will add the selected soung to the created video
public class Merge_Video_Audio extends AsyncTask<String,String,String> {
    private BottomSheetDialog mBottomSheetDialog;

     @SuppressLint("StaticFieldLeak")
     Context context;

    String audio,video,output,draft_file;

    public Merge_Video_Audio(Context context){
        this.context=context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    public String doInBackground(String... strings) {
        try {
showprogress();
        }catch (Exception ignored){

        }
         audio=strings[0];
         video=strings[1];
         output=strings[2];
         if(strings.length==4){
             draft_file=strings[3];
         }

        Log.d("resp",audio+"----"+video+"-----"+output);

        Thread thread = new Thread(runnable);
        thread.start();

        return null;
    }

    private void showprogress() {


        @SuppressLint("InflateParams") final View view =  LayoutInflater.from(context).inflate(R.layout.block_user,null);




            mBottomSheetDialog = new BottomSheetDialog(context);
            mBottomSheetDialog.setContentView(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Objects.requireNonNull(mBottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            // set background transparent
            ((View) view.getParent()).setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

            mBottomSheetDialog.show();
            mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mBottomSheetDialog = null;
                }
            });



    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }


    public void Go_To_preview_Activity(){
        Intent intent =new Intent(context,Preview_Video_A.class);
        intent.putExtra("path", Variables.outputfile2);
        intent.putExtra("urls",draft_file);
        context.startActivity(intent);
    }



    public Track CropAudio(String videopath,Track fullAudio){
        try {

            IsoFile isoFile = new IsoFile(videopath);

            double lengthInSeconds = (double)
                    isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                    isoFile.getMovieBox().getMovieHeaderBox().getTimescale();


            double startTime1 = 0;
            double endTime1 = lengthInSeconds;


            long currentSample = 0;
            double currentTime = 0;
            double lastTime = -1;
            long startSample1 = -1;
            long endSample1 = -1;


            for (int i = 0; i < ((Track) fullAudio).getSampleDurations().length; i++) {
                long delta = ((Track) fullAudio).getSampleDurations()[i];


                if (currentTime > lastTime && currentTime <= startTime1) {
                    // current sample is still before the new starttime
                    startSample1 = currentSample;
                }
                if (currentTime > lastTime && currentTime <= endTime1) {
                    // current sample is after the new start time and still before the new endtime
                    endSample1 = currentSample;
                }

                lastTime = currentTime;
                currentTime += (double) delta / (double) ((Track) fullAudio).getTrackMetaData().getTimescale();
                currentSample++;
            }

            return new CroppedTrack(fullAudio, startSample1, endSample1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullAudio;
    }



   public Runnable runnable =new Runnable() {
        @Override
        public void run() {

            try {

                Movie m = MovieCreator.build(video);


                List nuTracks = new ArrayList<>();

                for (Track t : m.getTracks()) {
                    if (!"soun".equals(t.getHandler())) {
                        nuTracks.add(t);
                    }
                }

                 Track nuAudio = new AACTrackImpl(new FileDataSourceImpl(audio));
                 Track crop_track= CropAudio(video,nuAudio);
                 nuTracks.add(crop_track);
                 m.setTracks(nuTracks);
                Container mp4file = new DefaultMp4Builder().build(m);
                FileChannel fc = new FileOutputStream(new File(output)).getChannel();
                mp4file.writeContainer(fc);
                fc.close();
                try {
                    mBottomSheetDialog.dismiss();
                }catch (Exception e){
                    Log.d(Variables.tag,e.toString());

                }finally {
                    Go_To_preview_Activity();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(Variables.tag,e.toString());

            }

        }

    };




}
