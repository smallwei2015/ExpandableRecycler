package com.jash.expandablerecycler;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TreeAdapter adapter;
    private TreeAdapter.TreeViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Tree<?>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tree<String> tree = new Tree<>(String.format("第%02d组", i));
            for (int j = 0; j < 10; j++) {
                tree.addChild(new Tree<>(String.format("第%02d组 第%02d条", i, j)));
            }
            list.add(tree);
        }
        FrameLayout frame = (FrameLayout) findViewById(R.id.main_layout);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new TreeAdapter(this, list);
        recycler.setAdapter(adapter);
        holder = adapter.onCreateViewHolder(recycler, 0);
        holder.itemView.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(this);
        frame.addView(holder.itemView);
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                View child = recyclerView.getChildAt(0);
                int position = recyclerView.getChildAdapterPosition(child);
                for (int i = position; i >= 0 ; i--) {
                    if (adapter.getItemViewType(i) == 0) {
                        holder.itemView.setVisibility(View.VISIBLE);
                        adapter.onBindViewHolder(holder, i);
                        break;
                    }
                }
                for (int i = 1; i < recyclerView.getChildCount(); i++) {
                    if (ViewCompat.getY(recyclerView.getChildAt(i)) > holder.itemView.getHeight()) {
                        ViewCompat.setTranslationY(holder.itemView, 0);
                        break;
                    }
                    if (adapter.getItemViewType(position + i) == 0) {
                        View view = holder.itemView;
                        ViewCompat.setTranslationY(view, ViewCompat.getY(recyclerView.getChildAt(i)) - view.getHeight());
                        break;
                    }
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, RefreshActivity.class));
    }
}
