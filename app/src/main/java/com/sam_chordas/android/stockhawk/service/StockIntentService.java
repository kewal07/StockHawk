package com.kewal_krishna.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;
import com.kewal_krishna.android.stockhawk.R;

/**
 * Created by kewal_krishna on 10/1/15.
 */
public class StockIntentService extends IntentService {

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
    StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    String tag = intent.getStringExtra(getResources().getString(R.string.string_tag));
    if (tag.equals("add")){
      args.putString("symbol", intent.getStringExtra("symbol"));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
//    stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
    Handler mHandler = new Handler(getMainLooper());

    if (stockTaskService.onRunTask(new TaskParams(tag, args)) == GcmNetworkManager.RESULT_FAILURE) {
      if(!(tag.equals("init") || tag.equals("periodic")))
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_no_symbol), Toast.LENGTH_LONG).show();
        }
      });
    } else {
      if (tag.equals("add"))
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.string_symbol_found), Toast.LENGTH_LONG).show();
          }
        });
    }
  }
}
