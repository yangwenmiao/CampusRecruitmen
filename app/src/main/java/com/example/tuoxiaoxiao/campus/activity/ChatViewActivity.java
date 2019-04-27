package com.example.tuoxiaoxiao.campus.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.uitest.administrator.shoulderby.R;
import com.uitest.administrator.shoulderby.adapter.MsgAdapter;
import com.uitest.administrator.shoulderby.bean.Msg;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
    聊天界面
 */

public class ChatViewActivity extends AppCompatActivity {

    @Bind(R.id.list_chatview)
    ListView listChatview;
    @Bind(R.id.et_chatview_message)
    EditText etChatviewMessage;
    @Bind(R.id.btn_chatview_send)
    Button btnChatviewSend;

    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_chat_view);
        ButterKnife.bind(this);

        initMsgs();
        adapter = new MsgAdapter(ChatViewActivity.this, R.layout.msg_item, msgList);
        listChatview.setAdapter(adapter);
        btnChatviewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etChatviewMessage.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    listChatview.setSelection(msgList.size());
                    etChatviewMessage.setText("");
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg("您好！", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        //Msg msg2 = new Msg("Fine, thank you, and you?", Msg.TYPE_SEND);
        //msgList.add(msg2);
        Msg msg3 = new Msg("欢迎您使用兼并兼", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

}
