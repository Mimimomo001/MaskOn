package com.example.maskon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Explosion {
    Bitmap expo[] = new Bitmap[4];
    int expoFrame = 0;
    int expoX, expoY;
    Random random;

    public Explosion(Context context){
        expo[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.e0);
        expo[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.e1);
        expo[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.e2);
        expo[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.e3);
    }

    public Bitmap getExpo(int expoFrame){
        return expo[expoFrame];
    }
}
