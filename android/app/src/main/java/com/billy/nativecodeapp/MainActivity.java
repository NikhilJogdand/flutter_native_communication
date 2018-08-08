package com.billy.nativecodeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

  private static final String CHANNEL = "com.test/testNativeCalls";
  private MethodChannel mChannel;
  private String TAG = "TEST";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    mChannel = new MethodChannel(getFlutterView(), CHANNEL);
    mChannel.setMethodCallHandler(
            new MethodChannel.MethodCallHandler() {
              @Override
              public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                if (call.method.equals("launchActivity")) {
                  String input = call.argument("input");
                  Log.v(TAG, input);
                  launchActivity();
                  result.success(true);
                } else {
                  result.notImplemented();
                }
              }
            });
  }

  private void launchActivity() {
    Intent intent = new Intent(this, NewActivity.class);
    startActivityForResult(intent, 1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String result = (String) data.getSerializableExtra("RESULTS");
        mChannel.invokeMethod("activityResult", result);
      }
    }
  }
}
