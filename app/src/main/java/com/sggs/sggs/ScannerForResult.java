package com.sggs.sggs;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScannerForResult extends AppCompatActivity {
        Button scan, back;
        private AdView mAdView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scanner_for_result);
            scan = findViewById(R.id.button11);
            back = findViewById(R.id.back);
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            scan.setOnClickListener(V->{
                scanCode();
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        private void scanCode() {
            ScanOptions options = new ScanOptions();
            options.setPrompt("        Scanning.....\n\n\n\n\n\n\n\nVolume up to flash on\n\n");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureAct.class);
            barLaucher.launch(options);
        }

        ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
        {

            if (result.getContents() != null) {

                String data = result.getContents();

                String mobileNo = "";
                int j = 0;
                for (int i = 0; i < data.length(); i++) {
                    if (data.charAt(i) == '0' ||
                            data.charAt(i) == '1' ||
                            data.charAt(i) == '2' ||
                            data.charAt(i) == '3' ||
                            data.charAt(i) == '4' ||
                            data.charAt(i) == '5' ||
                            data.charAt(i) == '6' ||
                            data.charAt(i) == '7' ||
                            data.charAt(i) == '8' ||
                            data.charAt(i) == '9') {
                        mobileNo = mobileNo + data.charAt(i);
                        j++;
                    }
                    if (j == 10) {
                        break;
                    }
                }

                if (mobileNo.length() == 10) {
                    Intent intent = new Intent(ScannerForResult.this, StudentResult.class);
                    intent.putExtra("dataname", data);
                    startActivity(intent);
                } else {
                    Toast.makeText(ScannerForResult.this, "PLEASE SCAN SGGS ID ONLY", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }

        });
    }