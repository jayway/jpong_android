package com.jayway.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.jayway.pong.model.Step;
import com.jayway.pong.server.PongServer;

import java.util.List;


public class PongActivity extends ActionBarActivity implements PongServer.PongListener {

    private static final String TAG = PongActivity.class.getCanonicalName();
    private PongServer pongServer;
    private MyView view;

    private Step currentStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pongServer = ((PongApplication) getApplication()).getPongServer();
        pongServer.addPongListener(this);
        view = new MyView(this);
        setContentView(view);

        pongServer.ready(); //TODO: handle error if not connected
    }


    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onPlayers(List<String> players) {

    }

    @Override
    public void onStep(Step step) {

        view.postInvalidate();
        currentStep = step;

        int x = -((currentStep.playerPaddle.x + currentStep.playerPaddle.width / 2) -
                (currentStep.ball.x + currentStep.ball.radius / 2));
        Log.d("TAG", "move x: " + x);

        pongServer.move(x);
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

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            if (currentStep != null) {

                float scaleX = (float) x / currentStep.bounds.width;
                float scaleY = (float) y / currentStep.bounds.height;
                canvas.scale(scaleX, scaleY);

                radius = currentStep.ball.radius;
                paint.setColor(0xffff0000);
                canvas.drawCircle(currentStep.ball.x, currentStep.ball.y, radius, paint);
                paint.setColor(0xff00ff00);
                canvas.drawRect(currentStep.playerPaddle.x, currentStep.playerPaddle.y,
                        currentStep.playerPaddle.x + currentStep.playerPaddle.width,
                        currentStep.playerPaddle.y + currentStep.playerPaddle.height,
                        paint);
                paint.setColor(0xff0000ff);
                canvas.drawRect(currentStep.remotePlayerPaddle.x, currentStep.remotePlayerPaddle.y,
                        currentStep.remotePlayerPaddle.x + currentStep.remotePlayerPaddle.width,
                        currentStep.remotePlayerPaddle.y + currentStep.remotePlayerPaddle.height,
                        paint);

            }

        }
    }


     /*
    {
        ball: {x:10, y: 300, x_speed: 3, y_speed: 5, radius:5},
        playerPaddle: {x:106, y: 400, width:50, height:10},
        remotePlayerPaddle: {x:100, y: 0,width:50, height:10},
        players: {
            player1: {name:"Albin", score:3},
            player2: {name:"Christian", score:1}
        },
        bounds: {
            width: 400,
                    height: 600
        }
    }
    */


}
