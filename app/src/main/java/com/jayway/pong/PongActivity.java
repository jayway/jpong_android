package com.jayway.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class PongActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MyView(this));
    }

    public class MyView extends View {
        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            // Draw the flag of Japan!
            // You might want to replace this with some paddles and a ball for pong...
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = (int) (Math.min(canvas.getWidth(), canvas.getHeight()) * (3f / 5f)) / 2;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            paint.setColor(0xffc70025);
            canvas.drawCircle(x / 2, y / 2, radius, paint);
        }
    }

}
