package com.example.maskon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class GameView extends View {

    Bitmap bg,f,m;
    Rect rectbg, rectf;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS =30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float mX, mY;
    float oldX;
    float OldmX;
    ArrayList<Cov>covs;
    ArrayList<Explosion>explosions;

    public GameView(Context context) {
        super(context);
        this.context = context;
        bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroung);
        f = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor);
        m = BitmapFactory.decodeResource(context.getResources(), R.drawable.mh);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectbg = new Rect(0 ,0,dWidth, dHeight);
        rectf = new Rect(0,dHeight - f.getHeight(),dWidth,dHeight);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.dogicabold));
        healthPaint.setColor(Color.CYAN);
        random = new Random();
        mX = dWidth / 2 - m.getWidth() / 2;
        mY = dHeight - f.getHeight() - m.getHeight();
        covs = new ArrayList<>();
        explosions = new ArrayList<>();
        for (int i = 0; i<3; i++){
            Cov cov = new Cov(context);
            covs.add(cov);
        }
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(bg, null, rectbg, null);
        canvas.drawBitmap(f, null, rectf, null);
        canvas.drawBitmap(m, mX, mY, null);
        for (int i=0; i<covs.size(); i++){
            canvas.drawBitmap(covs.get(i).getCov(covs.get(i).covFrame), covs.get(i).covX, covs.get(i).covY, null);
            if (covs.get(i).covFrame > 2){
                covs.get(i).covFrame = 0;
            }
            covs.get(i).covY += covs.get(i).covVel;
            if (covs.get(i).covY + covs.get(i).getcovHeight() >= dHeight - f.getHeight()){
               points += 10;
               Explosion expo = new Explosion(context);
               expo.expoX = covs.get(i).covX;
               expo.expoY = covs.get(i).covY;
               explosions.add(expo);
               covs.get(i).resetPosition();
            }
        }

        for (int i=0; i < covs.size(); i++){
            if (covs.get(i).covX + covs.get(i).getcovWidth() >= mX
            && covs.get(i).covX <= mX + m.getWidth()
            && covs.get(i).covY + covs.get(i).getcovWidth() >= mY
            && covs.get(i).covY + covs.get(i).getcovWidth() <= mY + m.getHeight()){
                life--;
                covs.get(i).resetPosition();
                if (life == 0){
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("points", points);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
        }

        for (int i=0; i<explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExpo(explosions.get(i).expoFrame), explosions.get(i).expoX,
                    explosions.get(i).expoY, null);
            explosions.get(i).expoFrame++;
            if (explosions.get(i).expoFrame > 3){
                explosions.remove(i);
            }
        }

        if (life == 2){
            healthPaint.setColor(Color.YELLOW);
        }
        else if (life == 1){
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth-200,30, dWidth-200+60*life, 80,healthPaint);
        canvas.drawText("" + points,20, TEXT_SIZE, textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float tx = event.getX();
        float ty = event.getY();
        if (ty >= mY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                OldmX = mX;
            }
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - tx;
                float NewmX = OldmX - shift;
                if (NewmX <= 0)
                    mX = 0;
                else if(NewmX >= dWidth - m.getWidth())
                    mX = dWidth - m.getWidth();
                else
                    mX = NewmX;
            }
        }
        return true;
    }
}
