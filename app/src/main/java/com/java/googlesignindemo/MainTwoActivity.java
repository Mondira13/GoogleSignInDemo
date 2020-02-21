package com.java.googlesignindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainTwoActivity extends AppCompatActivity {

    ImageView profImage;
    TextView profName;
    TextView profEmail;
    Button logoutButton;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        initializeView();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personImage = account.getPhotoUrl();

            profName.setText(personName);
            profEmail.setText(personEmail);
            if (personImage != null) {
                Glide.with(this).load(personImage).into(profImage);
            }else{
                profImage.setImageResource(R.drawable.ic_android);
            }

        }

        onClickListener();

    }

    private void initializeView() {
        profImage = findViewById(R.id.profImage);
        profName = findViewById(R.id.profName);
        profEmail = findViewById(R.id.profEmail);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void onClickListener() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainTwoActivity.this, "Signout successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainTwoActivity.this, MainActivity.class));
                finish();
            }
        });
    }

}
