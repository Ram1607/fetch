package com.project.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataFetcher.DataFetchCallback {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DataFetcher dataFetcher = new DataFetcher(this);
        dataFetcher.fetchData();
    }

    @Override
    public void onDataFetched(List<Item> items) {
        ItemAdapter adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(Throwable t) {
        // Handle the error
        t.printStackTrace();
    }
}
