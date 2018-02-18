package com.example.reddy.fitnessapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.password;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    LinearLayout activity_login;
    EditText et_email,et_password;
    TextView tv_signup,tv_forgot;
    Button bt_login;
    String email,password;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity_login=(LinearLayout)findViewById(R.id.activity_login);
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);

        tv_signup=(TextView)findViewById(R.id.tv_signup);
        tv_forgot=(TextView)findViewById(R.id.tv_forgot);
        bt_login=(Button)findViewById(R.id.bt_login);
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if(new GlobalIp().isInternetOn(connec)!=1)
        {
            Snackbar.make(activity_login,"Uh Oh! Please check your network connection and try again!!",Snackbar.LENGTH_LONG).show();
        }


    }
    public void signup(View v)
    {
        Intent in=new Intent(this,SignupActivity.class);
        startActivity(in);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if(new GlobalIp().isInternetOn(connec)!=1)
        {
            Snackbar snackbar=Snackbar.make(activity_login,"Uh Oh! Please check your Internet connection and try again!!",Snackbar.LENGTH_LONG).setAction("Action",null);
            View sbView=snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.danger));
            snackbar.show();
        }
    }

    public void go_login(View v) {

        email = et_email.getText().toString().trim();
        if (email.isEmpty() || email.equals(null)) {
            Toast.makeText(LoginActivity.this, "Email cannot be left empty", Toast.LENGTH_SHORT).show();
        } else
        {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher((CharSequence) email);
            System.out.println(email + " : " + matcher.matches());
            if (!matcher.matches()) {
                Toast.makeText(LoginActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
            } else {
                password = et_password.getText().toString().trim();
                if (password.equals("") || password.isEmpty() || password.equals(null)) {
                    Toast.makeText(LoginActivity.this, "Password field cannot be left empty", Toast.LENGTH_SHORT).show();

                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SQLiteDatabase db;

                                    try {
                                        db = openOrCreateDatabase("MYDB1", MODE_PRIVATE, null);
                                        Cursor allrows = db.rawQuery("Select * from login_info", null);
                                        System.out.println("Count: " + allrows.getCount());
                                        if (allrows.moveToFirst()) {

                                                int id=allrows.getInt(0);
                                                String email2 = allrows.getString(1);
                                                String password2= allrows.getString(1);
                                                if(email2.equals(email) && password2.equals(password))
                                                {
                                                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                                                    new AlertDialog.Builder(LoginActivity.this)
                                                            .setTitle("Save Details Confirmation")
                                                            .setMessage("Save email and password ?")
                                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    editor.putString("email", email);
                                                                    editor.putString("password", password);
                                                                    editor.putInt("autologin", 1);
                                                                    editor.commit();
                                                                    Toast.makeText(LoginActivity.this, "Logging in..", Toast.LENGTH_SHORT).show();
                                                                    Intent in = new Intent(getBaseContext(), DataActivity.class);
                                                                    startActivity(in);

                                                                }
                                                            })
                                                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    editor.putString("email", email);
                                                                    editor.putString("password", password);
                                                                    editor.putInt("autologin", 2);
                                                                    editor.commit();
                                                                    Toast.makeText(LoginActivity.this, "Logging in..", Toast.LENGTH_SHORT).show();
                                                                    Intent in = new Intent(getBaseContext(), DataActivity.class);
                                                                    startActivity(in);

                                                                }
                                                            })
                                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                                            .create()
                                                            .show();
                                                }

                                        }


                                                else
                                    {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Snackbar.make(bt_login, "Invalid email/password!", Snackbar.LENGTH_SHORT).show();

                                            }
                                        });

                                    }

                                        db.close();
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }


                            }).start();}
                        }

                    }
                }


    }
