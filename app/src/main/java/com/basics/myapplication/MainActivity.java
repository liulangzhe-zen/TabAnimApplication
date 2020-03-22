package com.basics.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.basics.myapplication.widget.LottieTabView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LottieTabView mLottieMainTab;
    private LottieTabView mLottieMsgTab;
    private LottieTabView mLottieDealTab;
    private LottieTabView mLottieMineTab;
    private LottieTabView tab_view_min1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        mLottieMainTab = findViewById(R.id.tab_view_main);
        mLottieMsgTab = findViewById(R.id.tab_view_msg);
        mLottieDealTab = findViewById(R.id.tab_view_deal);
        mLottieMineTab = findViewById(R.id.tab_view_mine);
        tab_view_min1 = findViewById(R.id.tab_view_min1);


        mLottieMainTab.setOnClickListener(this);
        mLottieMsgTab.setOnClickListener(this);
        mLottieDealTab.setOnClickListener(this);
        mLottieMineTab.setOnClickListener(this);
        tab_view_min1.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_view_main:
                mLottieMainTab.selected();
                mLottieMsgTab.unSelected();
                mLottieDealTab.unSelected();
                mLottieMineTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_msg:

                mLottieMsgTab.selected();
                mLottieDealTab.unSelected();
                mLottieMineTab.unSelected();
                mLottieMainTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_deal:

                mLottieDealTab.selected();
                mLottieMsgTab.unSelected();
                mLottieMineTab.unSelected();
                mLottieMainTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_mine:

                mLottieMineTab.selected();
                mLottieMsgTab.unSelected();
                mLottieDealTab.unSelected();
                mLottieMainTab.unSelected();
                tab_view_min1.unSelected();
                break;
            case R.id.tab_view_min1:

                tab_view_min1.selected();

                mLottieMsgTab.unSelected();
                mLottieMineTab.unSelected();
                mLottieDealTab.unSelected();
                mLottieMainTab.unSelected();
                break;
                default:
                    break;
        }
    }
}
