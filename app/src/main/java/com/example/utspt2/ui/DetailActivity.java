package com.example.utspt2.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utspt2.R;
import com.example.utspt2.api.ApiConfig;
import com.example.utspt2.api.ApiService;
import com.example.utspt2.data.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ProgressBar progressBarDetail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBarDetail = findViewById(R.id.progressBarDetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String username = extras.getString("usernameDetail");
            ApiService apiService = ApiConfig.getApiService();
            Call<User> userCall = apiService.getUser(username);

            TextView nameDetail = findViewById(R.id.nameDetail);
            TextView usernameDetail = findViewById(R.id.usernameDetail);
            TextView bioDetail = findViewById(R.id.bioDetail);
            ImageView avatarDetail = findViewById(R.id.avatarDetail);

            showLoading(true);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()){
                        showLoading(false);
                        User user = response.body();
                        if (user != null){
                            String usernames = "Username : " + user.getUsername();
                            String name = "Name : " + user.getName();
                            String bio = "Bio : " + user.getBio();
                            String avatar = user.getAvatarUrl();
                            usernameDetail.setText(usernames);
                            nameDetail.setText(name);
                            bioDetail.setText(bio);
                            Picasso.get().load(avatar).into(avatarDetail);
                        }else {
                            Toast.makeText(DetailActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBarDetail.setVisibility(View.VISIBLE);
        } else {
            progressBarDetail.setVisibility(View.GONE);
        }
    }
}