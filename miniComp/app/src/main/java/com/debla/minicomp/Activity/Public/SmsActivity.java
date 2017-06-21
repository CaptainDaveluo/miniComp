package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave-PC on 2017/2/21.
 */

public class SmsActivity extends Activity{
    private Button btn_send;
    private EditText et_reciver;
    private EditText et_content;
    private String str_reciver;
    private String str_content;
    private static SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";
    private ListView lv_sms;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_send);
        btn_send = (Button) findViewById(R.id.sms_send);
        et_content= (EditText) findViewById(R.id.sms_content);
        et_reciver = (EditText) findViewById(R.id.sms_reciver);
        player = SynthesizerPlayer.createSynthesizerPlayer(SmsActivity.this, APPID);
        player.setVoiceName("vivixiaoyan");
        lv_sms = (ListView) findViewById(R.id.sms_lv);
        list = new ArrayList<String>();
        final SmsAdapter adapter = new SmsAdapter();
        lv_sms.setAdapter(adapter);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    btn_send.setTextColor(Color.parseColor("#dddddd"));
                    btn_send.setEnabled(false);
                }else{
                    btn_send.setTextColor(Color.WHITE);
                    btn_send.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        et_content.addTextChangedListener(textWatcher);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_content = et_content.getText().toString().trim();
                str_reciver = et_reciver.getText().toString().trim();
                if(!"".equals(str_content)&&!"".equals((str_reciver))){
                    SmsManager manager = SmsManager.getDefault();
                    manager.sendTextMessage(str_reciver,null,str_content,null,null);
                    Toast.makeText(getApplicationContext(),"已发送短信",Toast.LENGTH_SHORT).show();
                    player.playText("已发送短信","ent=vivi21,bft=5",null);
                    et_content.setText("");
                    list.add(list.size(),str_content);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }


    class SmsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if(convertView==null){
                v=View.inflate(SmsActivity.this,R.layout.sms_item,null);
            }else{
                v=convertView;
            }
            TextView tv = (TextView) v.findViewById(R.id.sms_item_content);
            tv.setText(list.get(position));
            return v;
        }

    }
}

