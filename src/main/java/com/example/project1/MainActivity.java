package com.example.project1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
   private ArrayList<Integer> flowers= new ArrayList<>();
   private int count=5;
   private boolean gameOver=true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //game setup
        ImageView restart = findViewById(R.id.reset);
        restart.setVisibility(View.INVISIBLE);
        final ImageView go =  findViewById(R.id.go);
        go.setClickable(true);
        go.bringToFront();

        final ImageView reset = findViewById(R.id.reset);
        reset.setClickable(true);
        reset.bringToFront();


        // reset button handler
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });
        //GO button click handler
        go.setOnClickListener(new View.OnClickListener() {
              @Override
            public void onClick(View v) {
                      ImageView f1 = (ImageView) findViewById(R.id.slot1);
                      ImageView f2 = (ImageView) findViewById(R.id.slot2);
                      ImageView f3 = (ImageView) findViewById(R.id.slot3);
                      f1.setImageResource(R.drawable.tmp);
                      f2.setImageResource(R.drawable.tmp);
                      f3.setImageResource(R.drawable.tmp);

                      spin();
                      updateCoins();
            }
        });
    }

    //Used to updateThe player coin amount
    private void updateCoins(){
         TextView coins = findViewById(R.id.Coins);
        String strI = String.valueOf(count);
        coins.setText(strI);
    }

    // Handles spin animations
    private void spin(){
        ImageView f1 =  findViewById(R.id.slot1);
        ImageView f2 = findViewById(R.id.slot2);
        ImageView f3 = findViewById(R.id.slot3);
        Animation rotation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotation);
        f1.startAnimation(rotation);
        f2.startAnimation(rotation);
        f3.setAnimation(rotation);
         final ImageView go = findViewById(R.id.go);


        rotation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation){
                go.setClickable(false);
            }
            @Override
            public void onAnimationRepeat(Animation animation){}

            @Override
            public void onAnimationEnd(Animation animation){
                //viewToAnimate.setVisibility(View.GONE);
                randomizer();
                updateCoins();
                go.setClickable(true);
            }
        });
    }

    //chooses the next set of random flowers
    private void randomizer(){

        int f1 = R.drawable.f1;
        int f2 = R.drawable.f2;
        int f3 = R.drawable.f3;
        randHelper(f1);
        randHelper(f2);
        randHelper(f3);


        Random r = new Random();
        int slot1 = r.nextInt(3 - 0) + 0;
        int slot2 = r.nextInt(3 - 0) + 0;
        int slot3 = r.nextInt(3 - 0) + 0;

        ImageView result1 = findViewById(R.id.slot1);
        ImageView result2 = findViewById(R.id.slot2);
        ImageView result3 = findViewById(R.id.slot3);
        ImageView go = findViewById(R.id.go);
        result1.setImageResource(flowers.get(slot1));
        result2.setImageResource(flowers.get(slot2));
        result3.setImageResource(flowers.get(slot3));

        //Checks to see if there are any matches
        if(flowers.get(slot1)!= flowers.get(slot2) && flowers.get(slot2)==flowers.get(slot3)){
            count+=3;
        }else if(flowers.get(slot1)== flowers.get(slot2)||flowers.get(slot2)==flowers.get(slot3)||flowers.get(slot1)==flowers.get(slot3)){
            count+=2;
        }else{
            count--;
            if(count==0){
                go.setVisibility(View.INVISIBLE);
                gameOver=true;
                updateCoins();
                ImageView reset = findViewById(R.id.reset);
                reset.setVisibility(View.VISIBLE);

            }

        }

    }

    //adds flowers to ArrayList
    private void randHelper(int flower){
            flowers.add(flower);
    }

    //Resets the game back to Game start settings
    private void endGame(){
        if(gameOver){
            count = CONSTANTS.STARTUP_CASH;
            gameOver =false;
            ImageView go = findViewById(R.id.go);
            go.setVisibility(View.VISIBLE);
            ImageView reset = findViewById(R.id.reset);
            reset.setVisibility(View.INVISIBLE);
            updateCoins();

        }
    }

}
