package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

    public class CameraActivity extends ListActivity {
        private WebView mwebView;
        private WebSettings mwebSettings;
        public Button call, sms;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.cameraactivity_main);
            String phoneNumber,Message;
            mwebView = (WebView) findViewById(R.id.webView);

            mwebView.setWebViewClient(new WebViewClient());
            mwebSettings = mwebView.getSettings(); //세부 세팅 등록
            mwebSettings.setJavaScriptEnabled(true); //웹페이지 자바스크립트 허용 여부
            mwebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
            mwebSettings.setJavaScriptCanOpenWindowsAutomatically(false); //자바스크립트 멸티뷰 혀용 여부
            mwebSettings.setLoadWithOverviewMode(true); //메타 테그 허용 여부
            mwebSettings.setUseWideViewPort(true); // 화면 사이즈 최적화 허용 여부
            mwebSettings.setSupportZoom(false); // 화면 줌 허용 여부
            mwebSettings.setBuiltInZoomControls(false); //화면 확대 축소 허용 여부
            mwebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //컨텐츠 사이즈 최적화
            mwebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
            mwebSettings.setDomStorageEnabled(true); //로컬저장소 허용 여부

            mwebView.loadUrl("http://192.168.137.84:8080/?action=stream"); //라즈베리파이 주소
            call = (Button) findViewById(R.id.button);
            sms = (Button) findViewById(R.id.button3);


            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNo="tel:"+"01099066798";
                    Intent tt=new Intent("android.intent.action.CALL", Uri.parse(phoneNo));
                    startActivity(tt);
                }
            });
            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNo = "01099066798";
                    String text = ((SettingActivity)SettingActivity.context_main).result;

                    try{
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo,null,text,null,null);
                        Toast.makeText(getApplicationContext(),"전송완료",Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"SMS 전송 실패",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }

            });
        }
    }
