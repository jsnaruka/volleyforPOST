package com.darkbears.volleylib.Activities;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.darkbears.volleylib.Classes.AppController;
import com.darkbears.volleylib.Classes.CustomRequest;
import com.darkbears.volleylib.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {MaterialEditText name,email,phone,password;
    Button signup;
    TextView login;
    RelativeLayout background;
    String sname,semail,sphone,spassword;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        initonclicklistner();

    }

    private void initonclicklistner() {

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    login.setTextColor(Color.RED);


                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    login.setTextColor(Color.WHITE);

                }
                return true;
            }
        });


        signup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        signup.setBackgroundResource(R.drawable.greenbt);
                        sname=name.getText().toString().trim();
                        semail=email.getText().toString().trim();
                        sphone=phone.getText().toString().trim();
                        spassword=password.getText().toString().trim();

                    }else {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            signup.setBackgroundResource(R.drawable.smallbt);

                            if(semail.length()==0||sname.length()==0||sphone.length()==0||spassword.length()==0){

                                if(sname.length()==0)
                                    name.setError("Please Enter Your Name");
                            }
                            if (spassword.length() < 4) {
                                password.setError("Please Enter More Than 3 Characters");
                            }
                            if(semail.length()==0)
                                email.setError("Please Enter Email Address");
                            else  if (!isValidEmail(semail)) {

                                email.setError("Email Address Is Not Valid");
                            }

                            if(sphone.length()==0)
                                phone.setError("Please Enter Mobile Number");

                            else if (sphone.length() < 10) {
                                phone.setError("Invalid Mobile Number");



                            }else {

                                if (!isValidEmail(semail)) {

                                    email.setError("Email Address Is Not Valid");
                                }  else if (sphone.length() < 10) {
                                    phone.setError("Invalid Mobile Number");
                                } else if (spassword.length() < 4) {
                                    password.setError("Please Enter More Than 3 Characters");
                                } else {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(password.getWindowToken(), 0);

                                    webservicecall();
                                }
                            }
                        }
                    }

                return true;
            }
        });
    }

    public  void webservicecall(){


        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("email",semail );
        params.put("name",sname);
        params.put("phone_no",sphone);
        params.put("password",spassword);
        params.put("user_type","customer");

        CustomRequest jsonArrayRequest = new CustomRequest("http://dbwsweb.com/projectdemo/conwash_test/web_apis/Ws_controller/signup",
                params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        Log.d("ServerResponse", response.toString());
                        Log.d("ServerResponse1", params+"");

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("ServerResponse2", error+"");
            }
        });
        Log.d("response", jsonArrayRequest.toString());
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    private void initialize() {

        name=(MaterialEditText)findViewById(R.id.editText);
        email=(MaterialEditText)findViewById(R.id.editText2);
        phone=(MaterialEditText)findViewById(R.id.editText3);
        password=(MaterialEditText)findViewById(R.id.editText4);
        signup=(Button)findViewById(R.id.button);
        login=(TextView)findViewById(R.id.textView2);
        background=(RelativeLayout)findViewById(R.id.background);


    }
}
