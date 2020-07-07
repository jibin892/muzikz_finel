package com.qboxus.musictok.Profile;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qboxus.musictok.Settings.Setting_F;
import com.qboxus.musictok.SimpleClasses.ApiRequest;
import com.qboxus.musictok.SimpleClasses.Callback;
import com.qboxus.musictok.SimpleClasses.Fragment_Callback;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.qboxus.musictok.Following.Following_F;
import com.qboxus.musictok.Main_Menu.MainMenuActivity;
import com.qboxus.musictok.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.qboxus.musictok.Profile.Liked_Videos.Liked_Video_F;
import com.qboxus.musictok.Profile.UserVideos.UserVideo_F;
import com.qboxus.musictok.R;
import com.qboxus.musictok.See_Full_Image_F;
import com.qboxus.musictok.SimpleClasses.Functions;
import com.qboxus.musictok.SimpleClasses.Variables;
import com.qboxus.musictok.SimpleClasses.Webview_F;
import com.qboxus.musictok.Video_Recording.GalleryVideos.GalleryVideos_A;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.arthenica.mobileffmpeg.Config.getPackageName;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Tab_F extends RootFragment implements View.OnClickListener  {
    View view;
    Context context;
    final int RequestPermissionCode=1;
    Snackbar snackbar;
    DrawerLayout drawer;
    LinearLayout editprofile,userverification,privecypolicy,settings,logout,shareapp,reportbug,rateus;
ImageView imagenavigation;
    LinearLayout facebooklink,instagaralink;
    TextView navigationemail,navigationname;


    public  TextView username,username2_txt,video_count_txt;
    public  ImageView imageView;
    public  TextView follow_count_txt,fans_count_txt,heart_count_txt,draft_count_txt;

    ImageView setting_btn;



    protected TabLayout tabLayout;

    protected ViewPager pager;

    private ViewPagerAdapter adapter;

    public boolean isdataload=false;


    RelativeLayout tabs_main_layout;

    LinearLayout top_layout;



    public  static String pic_url;


    public  LinearLayout create_popup_layout;

    public Profile_Tab_F() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.navigation, container, false);
        context=getContext();

        drawer = view.findViewById(R.id.drawer_layout);
        imagenavigation = view.findViewById(R.id.imagenavigation);
        navigationname =view.findViewById(R.id.navigationname);
        navigationemail =view.findViewById(R.id.navigationemail);

        setting_btn=view.findViewById(R.id.setting_btn);

        shareapp = view.findViewById(R.id.shareapp);


        editprofile = view.findViewById(R.id.editprofile);
        reportbug=view.findViewById(R.id.reportbug);
         privecypolicy = view.findViewById(R.id.privecypolicy);
        settings = view.findViewById(R.id.settings);
        logout = view.findViewById(R.id.logout);
        facebooklink = view.findViewById(R.id.facebooklink);
        instagaralink = view.findViewById(R.id.instagaralink);

        rateus=view.findViewById(R.id.rateus);



            Picasso.get().load(pic_url).resize(200, 200).placeholder(R.drawable.profile_image_placeholder).centerCrop().into(imagenavigation);






        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Open_Edit_profile();           }
        });


        privecypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Open_Privacy_url();           }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Open_setting();           }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Logout();           }
        });

        facebooklink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String facebookUrl = "https://www.facebook.com/facebook.com/Muzika-111829883923996";
                String facebookID = "111829883923996";

                try {
                    int versionCode = getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;

                    if (!facebookID.isEmpty()) {
                        // open the Facebook app using facebookID (fb://profile/facebookID or fb://page/facebookID)
                        Uri uri = Uri.parse("fb://page/" + facebookID);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } else if (versionCode >= 3002850 && !facebookUrl.isEmpty()) {
                        // open Facebook app using facebook url
                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } else {
                        // Facebook is not installed. Open the browser
                        Uri uri = Uri.parse(facebookUrl);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    // Facebook is not installed. Open the browser
                    Uri uri = Uri.parse(facebookUrl);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }



            }
        });



        instagaralink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://instagram.com/_muzika_short_video_app/?hl=en");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://instagram.com/_muzika_short_video_app/?hl=en")));
                }             }

        });

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

addreview();

            }

            private void addreview() {


                final Dialog rating = new Dialog(getActivity());
                rating.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                rating.setContentView(R.layout.rateus);
                rating.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(rating.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                final ImageView ratusimage = (ImageView) rating.findViewById(R.id.ratusimage);
                final TextView rateusname = (TextView) rating.findViewById(R.id.rateusname);

                rateusname.setText(Variables.sharedPreferences.getString(Variables.f_name, "") + " " + Variables.sharedPreferences.getString(Variables.l_name, ""));
                Picasso.get().load(pic_url).resize(200, 200).placeholder(R.drawable.profile_image_placeholder).centerCrop().into(ratusimage);

                ((Button) rating.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rating.dismiss();
                    }
                });

                ((Button) rating.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                        }


                        rating.dismiss();
                    }
                });

                rating.show();
                rating.getWindow().setAttributes(lp);

            }
        });





        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s);
                    String imgBitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), imgBitmap, "MUZIKA", null);
                    Uri imgBitmapUri = Uri.parse(imgBitmapPath);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
                    shareIntent.setType("*/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Muzika Official App From Play Store:https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Muzika");
                    startActivity(Intent.createChooser(shareIntent, "Share this"));

            }
        });

        reportbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportbugs();

             }

            private void reportbugs() {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.report_bug);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
ImageView repotubugimage=dialog.findViewById(R.id.repotubugimage);

                final EditText et_post=dialog.findViewById(R.id.et_post);

                TextView repotubugname=dialog.findViewById(R.id.repotubugname);
                ImageView btclosereportbug=dialog.findViewById(R.id.btclosereportbug);
                Button bt_submitrepotbug=dialog.findViewById(R.id.bt_submitrepotbug);

                repotubugname.setText(Variables.sharedPreferences.getString(Variables.f_name, "") + " " + Variables.sharedPreferences.getString(Variables.l_name, ""));
                Picasso.get().load(pic_url).resize(200, 200).placeholder(R.drawable.profile_image_placeholder).centerCrop().into(repotubugimage);

                bt_submitrepotbug.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       uploadds();


                    }

                    private void uploadds() {

                        final ProgressDialog progress = new ProgressDialog(getActivity());
                        progress.setMessage("Thank you Plase Wait a moment........");
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        //  progress.setIndeterminate(true);
                        progress.show();
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        DatabaseReference object = FirebaseDatabase.getInstance().getReference();
                        final DatabaseReference namesRef = object.child("bug").push();
                        Map<String, Object> map = new HashMap<>();
                        map.put("personName",Variables.sharedPreferences.getString(Variables.f_name, "") + " " + Variables.sharedPreferences.getString(Variables.l_name, ""));
                        map.put("personPhoto",pic_url );
                        map.put("bug",et_post.getText().toString());
                        String mGroupId = object.push().getKey();
                        map.put("personId", mGroupId);
                        namesRef.updateChildren(map);
                        object.child("bug");



                        object.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                progress.dismiss();

                                dialog.dismiss();


                                loadshett();

                            }

                            @SuppressLint("WrongConstant")
                            private void loadshett() {


                                View layout = MainMenuActivity.mainMenuActivity.getLayoutInflater().inflate(R.layout.bugreport_sucsuss,null);
                                ImageView user_imagebugsucsess= layout.findViewById(R.id.user_imagebugsucsess);
                                TextView messagedebugs=layout.findViewById(R.id.messagedebugs);

                                messagedebugs.setText(Variables.sharedPreferences.getString(Variables.f_name, "") + " " + Variables.sharedPreferences.getString(Variables.l_name, ""));
                                Picasso.get().load(pic_url).resize(200, 200).placeholder(R.drawable.profile_image_placeholder).centerCrop().into(user_imagebugsucsess);


                                snackbar = Snackbar.make(MainMenuActivity.mainMenuActivity.findViewById(R.id.container), "", Snackbar.LENGTH_LONG);

                                Snackbar.SnackbarLayout snackbarLayout= (Snackbar.SnackbarLayout) snackbar.getView();
                                TextView textView = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);
                                textView.setVisibility(View.INVISIBLE);

                                final ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
                                if (params instanceof CoordinatorLayout.LayoutParams) {
                                    ((CoordinatorLayout.LayoutParams) params).gravity = Gravity.TOP;
                                } else {
                                    ((FrameLayout.LayoutParams) params).gravity = Gravity.TOP;
                                }

                                snackbarLayout.setPadding(0,0,0,0);
                                snackbarLayout.addView(layout, 0);


                                snackbar.getView().setVisibility(View.INVISIBLE);

                                snackbar.setCallback(new Snackbar.Callback(){
                                    @Override
                                    public void onShown(Snackbar sb) {
                                        super.onShown(sb);
                                        snackbar.getView().setVisibility(View.VISIBLE);
                                    }

                                });





                                snackbar.setDuration(Snackbar.LENGTH_LONG);
                                snackbar.show();

                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });








                    }
                });
                btclosereportbug.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                     }
                });



                dialog.show();
                dialog.getWindow().setAttributes(lp);

            }
        });

        NavigationView navigationView = (NavigationView)view. findViewById(R.id.nav_view_home);
        View headerView = navigationView.getHeaderView(0);

        setting_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.RIGHT);

            }
        });

        return init();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_image:
                OpenfullsizeImage(pic_url);
                break;

            case R.id.following_layout:
                Open_Following();
                break;

            case R.id.fans_layout:
                Open_Followers();
                break;

            case R.id.draft_btn:
                Intent upload_intent=new Intent(getActivity(), GalleryVideos_A.class);
                startActivity(upload_intent);
                getActivity().overridePendingTransition(R.anim.in_from_bottom,R.anim.out_to_top);
                break;

        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if((view!=null && isVisibleToUser) && !isdataload){
            if(Variables.sharedPreferences.getBoolean(Variables.islogin,false))
                init();
        }
        if((view!=null && isVisibleToUser) && isdataload){
            Call_Api_For_get_Allvideos();
        }





    }

    @Override
    public void onResume() {
        super.onResume();
        Show_draft_count();
    }

    public View init(){

        username=view.findViewById(R.id.username);
        username2_txt=view.findViewById(R.id.username2_txt);
        imageView=view.findViewById(R.id.user_image);
        imageView.setOnClickListener(this);

        video_count_txt=view.findViewById(R.id.video_count_txt);

        follow_count_txt=view.findViewById(R.id.follow_count_txt);
        fans_count_txt=view.findViewById(R.id.fan_count_txt);
        heart_count_txt=view.findViewById(R.id.heart_count_txt);
        draft_count_txt=view.findViewById(R.id.draft_count_txt);

        Show_draft_count();

      //  setting_btn.setOnClickListener(this);


        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        pager = view.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        adapter = new ViewPagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        setupTabIcons();


        tabs_main_layout=view.findViewById(R.id.tabs_main_layout);
        top_layout=view.findViewById(R.id.top_layout);




        ViewTreeObserver observer = top_layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                final int height=top_layout.getMeasuredHeight();

                top_layout.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                ViewTreeObserver observer = tabs_main_layout.getViewTreeObserver();
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) tabs_main_layout.getLayoutParams();
                        params.height= (int) (tabs_main_layout.getMeasuredHeight()+ height);
                        tabs_main_layout.setLayoutParams(params);
                        tabs_main_layout.getViewTreeObserver().removeGlobalOnLayoutListener(
                                this);

                    }
                });

            }
        });


        create_popup_layout=view.findViewById(R.id.create_popup_layout);


        view.findViewById(R.id.following_layout).setOnClickListener(this);
        view.findViewById(R.id.fans_layout).setOnClickListener(this);

        isdataload=true;


        update_profile();

        Call_Api_For_get_Allvideos();

        return view;
    }

    public void Show_draft_count(){
        try {

            String path = Variables.draft_app_folder;
            File directory = new File(path);
            File[] files = directory.listFiles();
            draft_count_txt.setText("" + files.length);
            if (files.length <= 0) {
                view.findViewById(R.id.draft_btn).setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.draft_btn).setVisibility(View.VISIBLE);
                view.findViewById(R.id.draft_btn).setOnClickListener(this);
            }
        }catch (Exception e){
            view.findViewById(R.id.draft_btn).setVisibility(View.GONE);
        }
    }



    public void update_profile(){
        username2_txt.setText(Variables.sharedPreferences.getString(Variables.u_name,""));
        username.setText(Variables.sharedPreferences.getString(Variables.f_name, "") + " " + Variables.sharedPreferences.getString(Variables.l_name, ""));

        pic_url = Variables.sharedPreferences.getString(Variables.u_pic, "null");

        try {
            Picasso.get().load(pic_url)
                    .resize(200, 200)
                    .placeholder(R.drawable.profile_image_placeholder)
                    .centerCrop()
                    .into(imageView);

        } catch (Exception e) {

        }

    }

    private void setupTabIcons() {

        View view1 = LayoutInflater.from(context).inflate(R.layout.item_tabs_profile_menu, null);
        ImageView imageView1= view1.findViewById(R.id.image);
        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_video_color));
        tabLayout.getTabAt(0).setCustomView(view1);

        View view2 = LayoutInflater.from(context).inflate(R.layout.item_tabs_profile_menu, null);
        ImageView imageView2= view2.findViewById(R.id.image);
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_liked_video_gray));
        tabLayout.getTabAt(1).setCustomView(view2);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View v=tab.getCustomView();
                ImageView image=v.findViewById(R.id.image);

                switch (tab.getPosition()){
                    case 0:

                        if(UserVideo_F.myvideo_count>0){
                            create_popup_layout.setVisibility(View.GONE);
                        }else {
                            create_popup_layout.setVisibility(View.VISIBLE);
                            Animation aniRotate = AnimationUtils.loadAnimation(context,R.anim.up_and_down_animation);
                            create_popup_layout.startAnimation(aniRotate);
                        }

                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_video_color));
                        break;

                    case 1:
                        create_popup_layout.clearAnimation();
                        create_popup_layout.setVisibility(View.GONE);
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_liked_video_color));
                        break;
                }
                tab.setCustomView(v);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v=tab.getCustomView();
                ImageView image=v.findViewById(R.id.image);

                switch (tab.getPosition()){
                    case 0:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_video_gray));
                        break;
                    case 1:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_liked_video_gray));
                        break;
                }

                tab.setCustomView(v);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


    }




    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final Resources resources;

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


        public ViewPagerAdapter(final Resources resources, FragmentManager fm) {
            super(fm);
            this.resources = resources;
        }

        @Override
        public Fragment getItem(int position) {
            final Fragment result;
            switch (position) {
                case 0:
                    result = new UserVideo_F(Variables.sharedPreferences.getString(Variables.u_id,""));
                    break;
                case 1:
                    result = new Liked_Video_F(Variables.sharedPreferences.getString(Variables.u_id,""));
                    break;

                default:
                    result = null;
                    break;
            }

            return result;
        }

        @Override
        public int getCount() {
            return 2;
        }



        @Override
        public CharSequence getPageTitle(final int position) {
            return null;
        }



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }


        /**
         * Get the Fragment by position
         *
         * @param position tab position of the fragment
         * @return
         */
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }


    }



    //this will get the all videos data of user and then parse the data
    private void Call_Api_For_get_Allvideos() {

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("my_fb_id",Variables.sharedPreferences.getString(Variables.u_id,""));
            parameters.put("fb_id", Variables.sharedPreferences.getString(Variables.u_id,""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.Call_Api(context, Variables.showMyAllVideos, parameters, new Callback() {
            @Override
            public void Responce(String resp) {
                Parse_data(resp);
            }
        });



    }

    public void Parse_data(String responce){


        try {
            JSONObject jsonObject=new JSONObject(responce);
            String code=jsonObject.optString("code");
            if(code.equals("200")){
                JSONArray msgArray=jsonObject.getJSONArray("msg");

                JSONObject data=msgArray.getJSONObject(0);
                JSONObject user_info=data.optJSONObject("user_info");
                username2_txt.setText(user_info.optString("username"));
                username.setText(user_info.optString("first_name")+" "+user_info.optString("last_name"));

                Profile_F.pic_url=user_info.optString("profile_pic");
                Picasso.get()
                        .load(Profile_F.pic_url)
                        .placeholder(context.getResources().getDrawable(R.drawable.profile_image_placeholder))
                        .resize(200,200).centerCrop().into(imageView);

                Picasso.get()
                        .load(Profile_F.pic_url)
                        .placeholder(context.getResources().getDrawable(R.drawable.profile_image_placeholder))
                        .resize(200,200).centerCrop().into(imagenavigation);
              
                navigationname.setText(user_info.optString("username"));
                navigationemail.setText(user_info.optString("first_name")+" "+user_info.optString("last_name"));

                follow_count_txt.setText(data.optString("total_following"));
                fans_count_txt.setText(data.optString("total_fans"));
                heart_count_txt.setText(data.optString("total_heart"));



                JSONArray user_videos=data.getJSONArray("user_videos");
                if(!user_videos.toString().equals("["+"0"+"]")){
                    video_count_txt.setText(user_videos.length()+" Videos");
                    create_popup_layout.setVisibility(View.GONE);

                }
                else {

                    create_popup_layout.setVisibility(View.VISIBLE);
                    Animation aniRotate = AnimationUtils.loadAnimation(context,R.anim.up_and_down_animation);
                    create_popup_layout.startAnimation(aniRotate);

                }

                String verified=user_info.optString("verified");
                if(verified!=null && verified.equalsIgnoreCase("1")){
                    view.findViewById(R.id.varified_btn).setVisibility(View.VISIBLE);
                }


            }else {
               // Toast.makeText(context, ""+jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }









    public void Open_Edit_profile(){
        Edit_Profile_F edit_profile_f = new Edit_Profile_F(new Fragment_Callback() {
            @Override
            public void Responce(Bundle bundle) {

                update_profile();
            }
        });
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, edit_profile_f).commit();
    }

    public void Open_setting(){
        Setting_F setting_f = new Setting_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, setting_f).commit();
    }


    //this method will get the big size of profile image.
    public void OpenfullsizeImage(String url){
        See_Full_Image_F see_image_f = new See_Full_Image_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        Bundle args = new Bundle();
        args.putSerializable("image_url", url);
        see_image_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, see_image_f).commit();
    }
    public void Open_Privacy_url1(){
        Webview_F webview_f = new Webview_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle bundle=new Bundle();
        bundle.putString("url",Variables.privacy_policy);
        bundle.putString("title","Privacy Policy");
        webview_f.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.replace(R.id.Setting_F, webview_f).commit();
    }


    public void Open_Privacy_url(){
        Webview_F webview_f = new Webview_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle argss = new Bundle();
        argss.putString("url",Variables.privacy_policy);
        argss.putString("title","Privacy Policy");
         webview_f.setArguments(argss);        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, webview_f).commit();
    }



    public void Open_Following(){

        Following_F following_f = new Following_F(new Fragment_Callback() {
            @Override
            public void Responce(Bundle bundle) {

                Call_Api_For_get_Allvideos();

            }
        });
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        Bundle args = new Bundle();
        args.putString("id", Variables.sharedPreferences.getString(Variables.u_id,""));
        args.putString("from_where","following");
        following_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, following_f).commit();

    }


    public void Open_Followers(){
        Following_F following_f = new Following_F(new Fragment_Callback() {
            @Override
            public void Responce(Bundle bundle) {
                Call_Api_For_get_Allvideos();
            }
        });
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        Bundle args = new Bundle();
        args.putString("id", Variables.sharedPreferences.getString(Variables.u_id,""));
        args.putString("from_where","fan");
        following_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, following_f).commit();

    }

    // this will erase all the user info store in locally and logout the user
    public void Logout(){
        SharedPreferences.Editor editor= Variables.sharedPreferences.edit();
        editor.putString(Variables.u_id,"");
        editor.putString(Variables.u_name,"");
        editor.putString(Variables.u_pic,"");
        editor.putBoolean(Variables.islogin,false);
        editor.commit();
        getActivity().finish();
        startActivity(new Intent(getActivity(), MainMenuActivity.class));
    }



    @Override
    public void onDetach() {
        super.onDetach();
        Functions.deleteCache(context);
    }
   

}
