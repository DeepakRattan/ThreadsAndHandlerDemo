package com.example.deepakrattan.threadsandhandlerdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnClickMe;
    private TextView txtData;
    private long endTime;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //txtData.setText("Button Pressed");
            Bundle bundle = msg.getData();
            String data = bundle.getString("data");
            txtData.setText(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClickMe = (Button) findViewById(R.id.btnClickMe);
        txtData = (TextView) findViewById(R.id.txtData);

     /*   btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime = System.currentTimeMillis() + 20000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }//endofWhile
                    //After 20 seconds TextView will be updated
                    txtData.setText("Button Pressed");
                }
            }
        });*/


        //Performing Computation on separate thres
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create separate thread
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        endTime = System.currentTimeMillis() + 20000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }//endof while
                            //handler.sendEmptyMessage(0);

                            Message message = handler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", "Message from Separate thread");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                };//end of runnable
                Thread thread = new Thread(runnable);
                thread.start();

            }
        });

    }
}
