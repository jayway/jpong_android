package com.jayway.pong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.jayway.pong.model.Message;
import com.jayway.pong.model.Step;
import com.jayway.pong.model.Winning;
import com.jayway.pong.server.PongServer;

import java.util.List;

public class PongActivity extends ActionBarActivity implements PongServer.PongListener {

    private static final String TAG = PongActivity.class.getCanonicalName();
    private PongServer pongServer;
    private MyView view;

    private Step currentStep;
    private boolean isWinning = false;


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
    public void onMessage(Message message) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPlayers(List<String> players) {

    }


    public void onWinning(Winning winning) {
        isWinning = true;
        Log.d(TAG, "onWinning");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to play again?")
                .setTitle(winning.equals(pongServer.getUserName()) ? "We won!" : "We lost :(")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onStep(Step step) {
        if (!isWinning) {
            view.postInvalidate();
            currentStep = step;
            int x = -(currentStep.playerPaddle.x - currentStep.ball.x);
            //Log.d("TAG", "move x: "+x);
            pongServer.move(x);
        }
    }

    public class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            if (currentStep != null) {
                int x = getWidth();
                int y = getHeight();

                float scaleX = (float) x / currentStep.bounds.width;
                float scaleY = (float) y / currentStep.bounds.height;
                canvas.scale(scaleX, scaleY);


                int radius = currentStep.ball.radius;
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


                paint.setColor(Color.BLACK);
                paint.setTextSize(12);

                canvas.drawText(currentStep.players.player1.name + " : " + currentStep.players.player1.score,
                        20,
                        200,
                        paint);
                canvas.drawText(currentStep.players.player2.name + " : " + currentStep.players.player2.score,
                        20,
                        220,
                        paint);

            }
        }
    }
}
