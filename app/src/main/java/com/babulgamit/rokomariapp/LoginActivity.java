package com.babulgamit.rokomariapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final String EMAIL = "email";
    private LoginButton loginButtonFB;
    CallbackManager callbackManager;
    TextView nameTV;
    private ImageView imageView;
    private EditText numberin,passwodin;
    private Button loginButton;
    private TextView gotosingup,forgetpassword;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String name,email,number,password,repassword;
    SignInButton signInButtonGle;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        loginButtonFB = (LoginButton) findViewById(R.id.login_button);
        View child = getLayoutInflater().inflate(R.layout.nav_header, null);
        nameTV=child.findViewById(R.id.nav_headername_id);
        imageView=child.findViewById(R.id.nav_picture_id);

        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                nameTV.setText("User Id"+loginResult.getAccessToken().getUserId());
                String imageUrl= "https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                Picasso.get().load(imageUrl).into(imageView);

            }
            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButtonGle =(SignInButton)findViewById(R.id.sign_in_button);
        signInButtonGle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });


        if(FirebaseAuth.getInstance().getUid()!=null){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


        numberin=findViewById(R.id.email_edittext_id);
        passwodin=findViewById(R.id.password_edittext_id);
        loginButton=findViewById(R.id.button_sign_in_id);
        gotosingup=findViewById(R.id.goto_signUP_textview_id);
        forgetpassword=findViewById(R.id.forgetButton_id);


        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });
        gotosingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Registration_Activity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Please Filip this From", Toast.LENGTH_SHORT).show();
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgetActivity.class));
                Toast.makeText(getApplicationContext(), "Forget Password", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Login");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            gotoProfile();
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

    private void gotoProfile(){
        Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void loginUserAccount()
    {
        // show the visibility of progress bar to show loading
        email = numberin.getText().toString();
        password = passwodin.getText().toString();

        // validations for input email and password

        if (email.isEmpty()) {
            numberin.setError("Please enter Email !!");
            numberin.requestFocus();
            return;
        }
        else
        if (email.length() < 11) {
            numberin.setError("Enter a valid email Or Number ");
            numberin.requestFocus();
            return;

        }else
        if (password.isEmpty()) {
            passwodin.setError("Please enter password !!");
            passwodin.requestFocus();
            return;
        }
        else
        if (password.length() < 6) {
            passwodin.setError("Enter a valid password ");
            passwodin.requestFocus();
            return;

        }
        String key= databaseReference.push().getKey();
        UserRegistration users=new UserRegistration(name,email,number,password,repassword);
        databaseReference.child(key).setValue(users);

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                    numberin.requestFocus();
                                }
                            }
                        });

    }
}