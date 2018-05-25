package com.grant.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private Button mTowButton;
    private Button mThreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.main);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OneActivity.class);
                startActivity(intent);
            }
        });

        mTextView = (TextView) findViewById(R.id.text_one);

        mTowButton = (Button) findViewById(R.id.tow_btn);
        mTowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TowActivity.class);
                startActivity(intent);
            }
        });

        mThreeButton = (Button) findViewById(R.id.three_btn);
        mThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发布粘性事件
                EventBus.getDefault().postSticky(new MessageEvent("EventBus 粘性事件!"));
                Intent intent = new Intent(MainActivity.this, ThreeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            //注册
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusFrist event) {
        String msg = "收到消息" + event.getMsg();
        Log.e("msg",msg);
        mTextView.setText(msg);
    }


    /**
     *  事件的优先级
     * 订阅者方法的事件传递优先级 数值越大优先级越高 3>2>1
     */

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void onMessageEvent1(MessageEvent event) {
        Log.e("MainActivity", "onMessageEvent1, message is " + event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onMessageEvent2(MessageEvent event) {
        Log.e("MainActivity", "onMessageEvent2, message is " + event.getMessage());
        /**
         * 取消事件
         * 高优先级的订阅者方法接收到事件之后取消事件的传递。此时，低优先级的订阅者方法将不会接收到该事件
         * 此时 onMessageEvent1就接接受不到
         */

        EventBus.getDefault().cancelEventDelivery(event);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 3)
    public void onMessageEvent3(MessageEvent event) {
        Log.e("MainActivity", "onMessageEvent3, message is " + event.getMessage());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }
}
