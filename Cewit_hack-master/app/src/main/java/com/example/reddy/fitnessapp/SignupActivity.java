package com.example.reddy.fitnessapp;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.net.ConnectivityManager;
        import android.support.design.widget.Snackbar;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.Toast;

        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import static android.R.attr.password;

public class SignupActivity extends AppCompatActivity {

    EditText et_email,et_password,et_username,et_answer;
    Spinner sp_ques,sp_gender;
    ArrayList<String>al1;
    ArrayList<String>al2;
    ArrayAdapter<String> ad1,ad2;
    String email,password,gender,question,answer,username;
    RelativeLayout activity_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        activity_signup=findViewById(R.id.activity_signup);

        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        et_username=(EditText)findViewById(R.id.et_username);
        et_answer=(EditText)findViewById(R.id.et_answer);
        sp_ques=(Spinner)findViewById(R.id.sp_question);
        sp_gender=(Spinner)findViewById(R.id.sp_gender);
        //et_age=(EditText)findViewById(R.id.et_age);
        al1=new ArrayList<>();
        al2=new ArrayList<>();
        al1.add("What is the name of your favorite author?");
        al1.add("What is the name of the last book you read?");
        al1.add("Where did you attend your high school?");
        al1.add("What is the name of your pet?");
        al1.add("What is your favorite vacation spot?");
        al1.add("What is the name of your hometown?");
        al2.add("Female");
        al2.add("Male");
        al2.add("Other");
        ad1=new ArrayAdapter<String>(getBaseContext(),R.layout.single_row,al1);
        ad2=new ArrayAdapter<String>(getBaseContext(),R.layout.single_row,al2);
        sp_ques.setAdapter(ad1);
        sp_gender.setAdapter(ad2);
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if(new GlobalIp().isInternetOn(connec)!=1)
        {
            Snackbar.make(activity_signup,"Uh Oh! Please check your network connection and try again!!",Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if(new GlobalIp().isInternetOn(connec)!=1)
        {
            Snackbar snackbar=Snackbar.make(activity_signup,"Uh Oh! Please check your Internet connection and try again!!",Snackbar.LENGTH_LONG).setAction("Action",null);
            View sbView=snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(SignupActivity.this, R.color.danger));
            snackbar.show();
        }
    }

    public void signup(View view)
    {
        email=et_email.getText().toString().trim();
        if(email.equals(null)||email.isEmpty())
        {
            Toast.makeText(SignupActivity.this,"Email field cannot be left empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher((CharSequence) email);
            System.out.println(email + " : " + matcher.matches());
            if (!matcher.matches()) {
                Toast.makeText(SignupActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
            }
            else
            {
                password=et_password.getText().toString().trim();
                username=et_username.getText().toString().trim();
                gender=sp_gender.getSelectedItem().toString().trim();
                question=sp_ques.getSelectedItem().toString().trim();
                answer=et_answer.getText().toString().trim();
               // age=et_age.getText().toString().trim();
                if(password.isEmpty()||username.isEmpty()||answer.isEmpty())
                {
                    Toast.makeText(SignupActivity.this, "You must fill all fields to sign up!", Toast.LENGTH_SHORT).show();
                }
                else {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {



                                    DatabaseClass androidOpenDbHelperObj = new DatabaseClass(SignupActivity.this);


                                    SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();

                                    // ContentValues class is used to store a set of values that the ContentResolver can process.
                                    ContentValues contentValues = new ContentValues();

                                    // Get values from the POJO class and passing them to the ContentValues class
                                    contentValues.put(DatabaseClass.COLUMN_NAME_EMAIL, email);
                                    contentValues.put(DatabaseClass.COLUMN_NAME_PASSWORD, password);

                                   // long affectedColumnId = sqLiteDatabase.insert(AndroidOpenDbHelper.login_info, null, contentValues);


                                    sqliteDatabase.close();

                                    System.out.println("saved details..");
                                    Intent in=new Intent(SignupActivity.this,DataActivity.class);
                                    startActivity(in);

                                }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }}).start();


                }}}}
}
