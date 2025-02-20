package com.saptrishi.outomateshikshateachersapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.saptrishi.outomateshikshateachersapp.R;

import java.util.List;

public class ShowMsgAdapter extends BaseAdapter {

        List<String> Querycreatedbyid;
        List<String> ChatQry;
        List<String> Createdate;
        List<String> flag;
        Context context;


    public ShowMsgAdapter(Context context,List<String> flag,List<String> Querycreatedbyid, List<String> ChatQry,List<String> Createdate){

        this.context=context;
        this.Querycreatedbyid=Querycreatedbyid;
        this.ChatQry=ChatQry;
        this.Createdate=Createdate;
        this.flag=flag;
    }

    @Override
    public int getCount() {
        return (ChatQry.size());
    }

    @Override
    public Object getItem(int position) {
        return (ChatQry.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view==null){
            LayoutInflater vi = LayoutInflater.from(context);
            view = vi.inflate(R.layout.showtext, null);
        }

        LinearLayout msg_layout=view.findViewById(R.id.layout_msg);
        TextView tv_createdbyid=view.findViewById(R.id.createdbyid);
        TextView tv_chatmsg=view.findViewById(R.id.chatmsg);
        TextView tv_qrydate=view.findViewById(R.id.qrydate);
        LinearLayout msg_layout_tech=view.findViewById(R.id.layout_tech);
        TextView tv_createdbyid_tech=view.findViewById(R.id.createdbyid_tech);
        TextView tv_chatmsg_tech=view.findViewById(R.id.chatmsg_tech);
        TextView tv_qrydate_tech=view.findViewById(R.id.qrydate_tech);

        if(flag.get(position).equalsIgnoreCase("Tech")){
            //Right margin
            msg_layout_tech.setVisibility(View.GONE);
            msg_layout.setVisibility(View.VISIBLE);
            tv_createdbyid.setText(Querycreatedbyid.get(position).trim());
            tv_chatmsg.setText(ChatQry.get(position).trim());
            tv_qrydate.setText(Createdate.get(position).trim());
        }
        else
        {
            msg_layout.setVisibility(View.GONE);
            msg_layout_tech.setVisibility(View.VISIBLE);
            tv_createdbyid_tech.setText(Querycreatedbyid.get(position).trim());
            tv_chatmsg_tech.setText(ChatQry.get(position).trim());
            tv_qrydate_tech.setText(Createdate.get(position).trim());

            // Left margin
        }
        return view;
    }
}
