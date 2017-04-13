package com.ucsmy.pos.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ucsmy.pos.R;
import com.ucsmy.pos.entity.Card;
import com.ucsmy.pos.entity.CardInfo;
import com.ucsmy.pos.http.FosBusinessAgent;
import com.ucsmy.pos.utils.CpuInfo;
import com.ucsmy.pos.utils.ToastUtil;

import java.util.List;

import rx.Subscriber;

/**
 * Created by ucs_zhangjiaheng on 2017/3/9.
 */

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private TextView mTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mTextView = (TextView) findViewById(R.id.cpu);
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.calCpuRatioInfo();
//        mTextView.setText(cpuInfo.getProcessCpuRatio()+"/"+cpuInfo.getProcessCpuRate());
    }



    public void openCamera(View v){
        FosBusinessAgent agent = new FosBusinessAgent();
        agent.cardDetect().subscribe(new Subscriber<CardInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showLongToast(mContext,"error");
            }

            @Override
            public void onNext(CardInfo cardInfo) {
                if(cardInfo!=null){
                    List<Card> card = cardInfo.getCards();
                    ToastUtil.showLongToast(mContext,card.get(0).getId_card_number());
                }
            }
        });
    }
}
