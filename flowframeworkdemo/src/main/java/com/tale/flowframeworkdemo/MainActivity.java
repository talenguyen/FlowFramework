package com.tale.flowframeworkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tale.flowframework.FlowService;
import com.tale.flowframework.Result;
import com.tale.flowframework.ResultHandler;
import com.tale.flowframeworkdemo.flow.MessageFlow;
import com.tale.flowframeworkdemo.model.Message;


public class MainActivity extends AppCompatActivity implements ResultHandler {

    private TextView tvMessage;
    private MessageFlow flow;
    private boolean isServiceRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flow = ((DemoApp) getApplication()).getMessageFlow();
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
        flow.connect(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        flow.disconnect();
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

    @Override
    public void onResult(Result<?> result) {
        isServiceRunning = false;
        if (result.success) {
            tvMessage.setText(((Message) result.data).message);
        }
    }
}
