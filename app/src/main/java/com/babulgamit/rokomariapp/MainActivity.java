package com.babulgamit.rokomariapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    Button button;

    Button logoutBtn;
    TextView userName,userEmail,userId,showmenu;
    ImageView profileImage;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView=findViewById(R.id.navigation_id);
        drawerLayout=findViewById(R.id.drawer_layout_id);
        Toolbar toolbar=findViewById(R.id.toolbar_id);
        showmenu=findViewById(R.id.showmenu_id);
        setSupportActionBar(toolbar);


        button=findViewById(R.id.nav_login_id);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        logoutBtn=(Button)findViewById(R.id.logoutBtn);
        userName=(TextView)findViewById(R.id.name);
        userEmail=(TextView)findViewById(R.id.email);
        userId=(TextView)findViewById(R.id.userId);
        profileImage=(ImageView)findViewById(R.id.profileImage);


        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                userEmail.setText("");
                userId.setText("");
                userName.setText("");
                profileImage.setImageURI(null);
            }
        });

        View header=navigationView.getHeaderView(0);
        Button buttonLogin=header.findViewById(R.id.nav_login_id);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "You are now Login Activity", Toast.LENGTH_SHORT).show();

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home_id:
                        Toast.makeText(MainActivity.this, "You are now Homepage", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setVisibility(View.GONE);
                        userName.setVisibility(View.VISIBLE);
                        profileImage.setVisibility(View.VISIBLE);
                        userEmail.setVisibility(View.VISIBLE);
                        userId.setVisibility(View.VISIBLE);
                        logoutBtn.setVisibility(View.VISIBLE);

                        break;
                    case R.id.me_subject_id:

                        Toast.makeText(MainActivity.this, "You are now Subject", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setText("বিষয় Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;

                    case R.id.me_lekkhok_id:
                        Toast.makeText(MainActivity.this, "You are now Lokhok Page", Toast.LENGTH_SHORT).show();
                        showmenu.setText("লিখক Page Visible");
                        showmenu.setVisibility(View.VISIBLE);

                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ma_boimela_id:
                        Toast.makeText(MainActivity.this, "You are now Boimela Page", Toast.LENGTH_SHORT).show();
                        showmenu.setText("বইমেলা 2021 Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);

                        break;


                    case R.id.me_islamic_id:
                        Toast.makeText(MainActivity.this, "You are now Islamic Page", Toast.LENGTH_SHORT).show();
                        showmenu.setText("ইসলামি বই Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);

                        break;


                    case R.id.me_bestsaller_id:
                        Toast.makeText(MainActivity.this, "You are now Best Saller", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setText("বেস্টসেলার বই Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);

                        break;


                    case R.id.me_rokomaripackge_id:
                        Toast.makeText(MainActivity.this, "You are now Rokomari Page", Toast.LENGTH_SHORT).show();
                        showmenu.setText("রকমারি প্যাকেজে Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;


                    case R.id.me_begganbaxx_id:
                        Toast.makeText(MainActivity.this, "You are now BigganBox Page", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setText("বিজ্ঞানবাক্স Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;


                    case R.id.me_otirektochar_id:
                        Toast.makeText(MainActivity.this, "You are now extra Discount", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setText("অতিরিক্ত  ছাড়ের  বই Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;

                    case R.id.me_poschimbongo_id:
                        Toast.makeText(MainActivity.this, "You are now Poschimbongo Page", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setText("পশ্চিমবঙ্গের বই Page Visible");
                        showmenu.setVisibility(View.VISIBLE);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;


                    case R.id.me_aboutUs_id:
                        Toast.makeText(MainActivity.this, "You are now About Us", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setText("আমরা যেমন page visible");
                        showmenu.setVisibility(View.VISIBLE);

                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;

                    case R.id.me_feedback_id:
                        Toast.makeText(MainActivity.this, "You are now Feedback", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showmenu.setText("মতামত/অভিযোগ page visible");
                        showmenu.setVisibility(View.VISIBLE);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;

                    case R.id.me_support_id:
                        Toast.makeText(MainActivity.this, "You are now Support", Toast.LENGTH_SHORT).show();
                        showmenu.setText("সাপোর্ট page visible");
                        showmenu.setVisibility(View.VISIBLE);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        userName.setVisibility(View.GONE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;

                    case R.id.me_privacy_id:
                        Toast.makeText(MainActivity.this, "You are now privacy Policy", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        userName.setVisibility(View.GONE);
                        showmenu.setText("পলিসি page visible");
                        showmenu.setVisibility(View.VISIBLE);
                        profileImage.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        userId.setVisibility(View.GONE);
                        logoutBtn.setVisibility(View.GONE);
                        break;


                }

                return true;
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            userName.setText(account.getDisplayName());
            userEmail.setText(account.getEmail());
            userId.setText(account.getId());
            try{
                Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        }else{
            gotoMainActivity();
        }
    }

    private void gotoMainActivity(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}