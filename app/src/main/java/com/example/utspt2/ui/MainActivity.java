package com.example.utspt2.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utspt2.R;
import com.example.utspt2.api.ApiConfig;
import com.example.utspt2.api.ApiService;
import com.example.utspt2.data.SearchUserResponse;
import com.example.utspt2.data.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvUser;
    private ProgressBar progressBar;
    private GithubUserAdapter adapter;
    private List<User> userList = new ArrayList<>();

    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUser = findViewById(R.id.rvUser);
        progressBar = findViewById(R.id.progressBar);

        apiService = ApiConfig.getApiService();

        searchUsers("DIKI-WAHYUDI-2");
    }

    private void searchUsers(String query) {
        progressBar.setVisibility(View.VISIBLE);

        Call<SearchUserResponse> call = apiService.searchUsers(query);

        call.enqueue(new Callback<SearchUserResponse>() {
            @Override
            public void onResponse(Call<SearchUserResponse> call, Response<SearchUserResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    userList.addAll(response.body().getUsers());
                    showRecyclerView();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchUserResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRecyclerView() {
        if (adapter == null) {
            adapter = new GithubUserAdapter(userList);
            rvUser.setAdapter(adapter);
            rvUser.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}