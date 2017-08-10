
package com.sage.demo0809.app;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sage.demo0809.R;

@Deprecated
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Tinker.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "i am on onCreate classloader:" + MainActivity.class.getClassLoader().toString());
        //test resource change
//        Log.e(TAG, "i am on onCreate string:" + getResources().getString(R.string.test_resource));
//        Log.e(TAG, "i am on patch onCreate");

//       Button loadPatchButton = (Button) findViewById(R.id.loadPatch);
//
//        loadPatchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), "/sdcard/zhibo8/patch_signed_7zip.apk");
//            }
//        });
//
//        Button loadLibraryButton = (Button) findViewById(R.id.loadLibrary);

//        loadLibraryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //for lib/armeabi, just use TinkerInstaller.loadLibrary
//                TinkerInstaller.loadArmLibrary(getApplicationContext(), "stlport_shared");
////                TinkerInstaller.loadLibraryFromTinker(getApplicationContext(), "assets/x86", "stlport_shared");
//            }
//        });
//
//        Button cleanPatchButton = (Button) findViewById(R.id.cleanPatch);
//
//        cleanPatchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tinker.with(getApplicationContext()).cleanPatch();
//            }
//        });

//        Button killSelfButton = (Button) findViewById(R.id.killSelf);
//
//        killSelfButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
//                android.os.Process.killProcess(android.os.Process.myPid());
//            }
//        });

//        Button buildInfoButton = (Button) findViewById(R.id.showInfo);
//
//        buildInfoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showInfo(MainActivity.this);
//            }
//        });
    }


}
