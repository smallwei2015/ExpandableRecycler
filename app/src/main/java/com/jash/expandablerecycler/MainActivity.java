package com.jash.expandablerecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setAdapter(new TreeAdapter(this, list));
    }
}
