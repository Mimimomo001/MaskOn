package com.example.maskon;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Cov {
    Bitmap cov[] = new Bitmap[3];
    int covFrame = 0;
    int covX, covY, covVel;
    Random random;

    public Cov(Context context){
        cov[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cov);
        cov[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cov);
        cov[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cov);
        random = new Random();
        resetPosition();
    }
    public Bitmap getCov(int covFrame){
        return cov[covFrame];
    }
    public int getcovWidth(){
        return cov[0].getWidth();
    }

    public int getcovHeight(){
        return cov[0].getHeight();
    }
    public void resetPosition(){
       covX = random.nextInt(GameView.dWidth - getcovWidth());
       covY = -200 + random.nextInt(600)* -1;
       covVel = 35 + random.nextInt(16);
    }
}
