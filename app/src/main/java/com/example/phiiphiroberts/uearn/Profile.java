package com.example.phiiphiroberts.uearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ArrayList<RecylerItem> recyclerList = new ArrayList<>();
        recyclerList.add( new RecylerItem(R.drawable.account_outline,"View your profile"));
        recyclerList.add( new RecylerItem(R.drawable.home,"Home"));
        recyclerList.add( new RecylerItem(R.drawable.basket," View Shopping items"));
        recyclerList.add( new RecylerItem(R.drawable.chart_areaspline1," Check downliners"));
        recyclerList.add( new RecylerItem(R.drawable.baseline_work_outline_white_24dp," My transactions"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecylerAdapter(recyclerList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
