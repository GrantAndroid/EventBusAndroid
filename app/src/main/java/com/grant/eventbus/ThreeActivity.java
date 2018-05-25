package com.grant.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        // 注册订阅者
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        Log.i("ThreeActivity", "message is " + event.getMessage());
        // 更新界面
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(event);

        /**
         * 根据需求来确定
         */
        // 移除指定的粘性事件
//        EventBus.getDefault().removeStickyEvent(Object event);

        // 移除指定类型的粘性事件
//        EventBus.getDefault().removeStickyEvent(Class<T> eventType);

        // 移除所有的粘性事件
//        EventBus.getDefault().removeAllStickyEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }
}
