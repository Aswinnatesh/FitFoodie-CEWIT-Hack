package com.example.reddy.fitnessapp;
import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class SplashScreen extends AppCompatActivity {
    ProgressBar sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        /*sp = (ProgressBar) findViewById(R.id.pb);
        sp.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
*/
        //StartAnimations();
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        final int n = sharedPreferences.getInt("autologin", 0);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SplashScreen.this, "Please grant all permissions first!", Toast.LENGTH_LONG).show();

            } else {
                try {
                    File f = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "Download/serverip.txt");
                    System.out.println("here.."+Environment.getExternalStorageDirectory().getPath() + File.separator + "Download/serverip.txt");
                    if (!f.exists()) {
                        Log.d("MYMSG", "File does not exist");
                    } else {
                        try {
                            FileReader fr = new FileReader(f);
                            BufferedReader br = new BufferedReader(fr);
                            GlobalIp.ip = br.readLine();
                            //Log.d("MYMSG", "Ip is :" + GlobalIp.ip);
                            Toast.makeText(this, "Server IP : " + GlobalIp.ip, Toast.LENGTH_SHORT).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(4000);
                                        finish();
                                        if (n == 1) {
                                            Intent it = new Intent(getBaseContext(), DataActivity.class);
                                            startActivity(it);
                                        } else {
                                            Intent it = new Intent(getBaseContext(), LoginActivity.class);
                                            startActivity(it);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                File f
                        = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "Download/serverip.txt");
                System.out.println(Environment.getExternalStorageDirectory().getPath() + File.separator + "Download/serverip.txt");
                if (!f.exists()) {
                    Log.d("MYMSG", "File does not exist");
                } else {
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);
                    GlobalIp.ip = br.readLine();
                    Log.d("MYMSG", "Ip is :" + GlobalIp.ip);
                    Toast.makeText(this, "Server IP : " + GlobalIp.ip, Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(4000);
                                finish();
                                if (n == 1) {
                                    Intent it = new Intent(getBaseContext(), DataActivity.class);
                                    startActivity(it);
                                } else {
                                    Intent it = new Intent(getBaseContext(), LoginActivity.class);
                                    startActivity(it);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }


                        }
                    }).start();


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }



}