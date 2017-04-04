package com.group25.proj2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main2Activity extends AppCompatActivity {

    ArrayList<ScoreObject> scores = new ArrayList();
    private ListView mScoreListView;
    ScoreAdapter scoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mScoreListView = (ListView) findViewById(R.id.list_scores);
        scoreAdapter =  new ScoreAdapter(Main2Activity.this, scores);
        mScoreListView.setAdapter(scoreAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        DoneActivity.mChildReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot details : dataSnapshot.getChildren()){
                    ScoreObject  score = details.getValue(ScoreObject.class);
                    System.out.println("Date " + score.getDate());
                    System.out.println("Score " + score.getScore());
                    scores.add(score);

                }

                Collections.sort(scores, new Comparator<ScoreObject>() {
                    @Override
                    public int compare(ScoreObject score2, ScoreObject score1)
                    {

                        return  score1.score - score2.score;
                    }
                });
                scoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }

}