package com.babulgamit.rokomariapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration_Activity extends AppCompatActivity  implements View.OnClickListener {

    private Button singUpbuttonR;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private TextInputEditText nameETR, emailETR,numberETR;
    private EditText passwordETR,repasswordETR;
    private TextView gotologin;
    private ProgressBar progressBar;
    String name,email,number,password,repassword,key;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button datebutton;
    private TextView dateresulttextview;
    private DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        nameETR = findViewById(R.id.nameETR_id);
        emailETR = findViewById(R.id.gmailETR_id);
        singUpbuttonR = findViewById(R.id.sigupBR_id);
        numberETR=findViewById(R.id.numberETR_id);
        repasswordETR=findViewById(R.id.repasswordR_id);
        passwordETR=findViewById(R.id.passwordR_id);
        gotologin=findViewById(R.id.gotologinTVR_id);


        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        gotologin.setOnClickListener(this);
        singUpbuttonR.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sigupBR_id:

                registerNewUser();

                break;

            case R.id.gotologinTVR_id:

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;


        }

    }

    private void registerNewUser()
    {

        name = nameETR.getText().toString().trim();
        email = emailETR.getText().toString().trim();
        number = numberETR.getText().toString().trim();
        password = passwordETR.getText().toString().trim();
        repassword = passwordETR.getText().toString().trim();

        // Validations for input details
        if (name.isEmpty()) {
            nameETR.setError("Please enter Name!!");
            nameETR.requestFocus();
            return;
        }
        else
        if (email.isEmpty()) {
            emailETR.setError("Please enter Email!!");
            emailETR.requestFocus();
            return;
        }
        else

        if (number.length()<11) {
            numberETR.setError("Please enter valid Number!!");
            numberETR.requestFocus();
            return;
        }
        else
        if (password.length() < 6) {

            passwordETR.setError("Please enter password");
            passwordETR.requestFocus();
            return;

        }
        else
        if (!repassword.equals(password)) {
            repasswordETR.setError("Please enter Same password!!");
            repasswordETR.requestFocus();
            return;
        }
        else



            // create new user or register new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {

                            if (task.isSuccessful()) {
                                key= databaseReference.push().getKey();
                                UserRegistration users=new UserRegistration(name,email,number,password,repassword);
                                databaseReference.child(key).setValue(users);

                                Toast.makeText(getApplicationContext(),
                                        "Registration successful!",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar
                                progressbar.setVisibility(View.GONE);

                                // if the user created intent to login activity
                                Intent intent =getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                                if (intent !=null)
                                {
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"Register is Successful",Toast.LENGTH_SHORT).show();


                                }else
                                {

                                    gotoUrl("https://play.google.com/store/apps/details?id=com.facebook.katana&hl=en&gl=US");
                                    Toast.makeText(getApplicationContext(), "Please Install This Apps", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {

                                // Registration failed
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Registration failed!!"
                                                + " Please try again later",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar
                                progressbar.setVisibility(View.GONE);
                            }
                        }
                    });
    }

    private void gotoUrl(String s) {

        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}
