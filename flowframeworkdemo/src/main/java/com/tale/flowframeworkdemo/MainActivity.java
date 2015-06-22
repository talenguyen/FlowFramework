package com.tale.flowframeworkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;
import com.tale.flowframework.FlowService;
import com.tale.flowframework.Publisher;
import com.tale.flowframework.Result;
import com.tale.flowframeworkdemo.flow.MessageFlow;
import com.tale.flowframeworkdemo.model.Message;


public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private MessageFlow flow;
    private boolean isServiceRunning;

    private Bus bus = new Bus(ThreadEnforcer.MAIN);
    private Publisher<Message> publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flow = ((DemoApp) getApplication()).getMessageFlow();
        publisher = new Publisher<Message>() {
            @Override
            public void publish(Result<Message> result) {
                bus.post(result);
            }
        };
        flow.setPublisher(publisher);
        setContentView(R.layout.activity_main);
        tvMessage = ((TextView) findViewById(R.id.tvMessage));
        findViewById(R.id.btGetMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMessage.setText("Getting...");
                flow.start(null);
            }
        });
        findViewById(R.id.btGetMessageService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMessage.setText("Getting Service...");
                FlowService.start(MainActivity.this, DemoApp.MESSAGE_FLOW);
                isServiceRunning = true;
            }
        });
        if (flow.isRunning()) {
            tvMessage.setText("Getting...");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isServiceRunning) {

            flow.cancel(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onResult(Result<Message> result) {
        isServiceRunning = false;
        if (result.success) {
            tvMessage.setText(result.data.message);
        }
    }
}
