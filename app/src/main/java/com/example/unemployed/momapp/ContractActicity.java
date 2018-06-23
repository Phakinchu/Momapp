package com.example.unemployed.momapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Pattern;

public class ContractActicity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_activity);
        Utils.getDatabase();
        TextView information = findViewById(R.id.information);
        String text = "          หากมีปัญหาหรือต้องการคำปรึกษาคุณแม่สามารถมาพบแพทย์ได้ที่ห้องคลอด รพ.จุฬาลงกรณ์ สภากาชาดไทยอาคารภูมิสิริมังคลานุสรณ์ชั้น 9 โซนเอ หรือ โทร 02-2564000 ต่อ 80921,80922 และคลินิกฝากครรภ์ ภปร.ชั้น 8 รพ.จุฬาลงกรณ์ สภากาชาดไทย หรือโทร  02-256-5282 เวลา 8.00-16.00 น.";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan highlight = new ForegroundColorSpan(0xFA8072);
        ClickableSpan clickableSpan =  new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                callphone("022564000");
            }
        };
        ClickableSpan clickableSpan2 =new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                callphone("022565282");
            }
        };
        ss.setSpan(highlight,143,157, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(highlight,233,250, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan,143,157,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2,233,250,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        information.setText(ss);
        information.setMovementMethod(LinkMovementMethod.getInstance());
//        TextView contr ;
//        contr = findViewById(R.id.information);
//        contr.setMovementMethod(LinkMovementMethod.getInstance());
//        Linkify.addLinks(contr, Linkify.PHONE_NUMBERS);
    }
    public void callphone(String phonenumber){
        startActivity(new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",phonenumber,null)));

    }

}
