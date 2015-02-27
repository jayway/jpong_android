package com.jayway.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.jayway.pong.model.Step;
import com.jayway.pong.server.PongServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
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
                radius = currentStep.ball.radius;
                paint.setColor(0xffc70025);
                canvas.drawCircle(currentStep.ball.x, currentStep.ball.y, radius, paint);

                canvas.drawRect(currentStep.playerPaddle.x, currentStep.playerPaddle.y,
                        currentStep.playerPaddle.x + currentStep.playerPaddle.width,
                        currentStep.playerPaddle.y + currentStep.playerPaddle.height,
                        paint);
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
