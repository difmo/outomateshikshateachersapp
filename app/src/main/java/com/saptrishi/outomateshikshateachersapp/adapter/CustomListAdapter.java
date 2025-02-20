package com.saptrishi.outomateshikshateachersapp.adapter;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.utils.MsgList;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class CustomListAdapter extends SimpleAdapter {

    private Context context;
    //private final String[] itemMsg;
    //private final int[] itemDate;
    private List<MsgList> DataList;
    private LayoutInflater inflater;



    public CustomListAdapter(Context context, List<Map<String, String>> itemObj, int resource, String[] from, int[] to, List<MsgList> items) {
        super(context, itemObj,resource, from, to);
        this.context=context;
        //this.itemMsg=itemname;
        //this.itemDate=imgid;
        this.DataList=items;


    }

    @SuppressWarnings("deprecation")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);}
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.noticedisplaypattern, null);
            holder = new ViewHolder();//TextView
            holder.imageViewLogo=(ImageView)convertView.findViewById(R.id.logoof_college);
            holder.tv_Msg=(ExpandableTextView) convertView.findViewById(R.id.noticeContent);
            holder.tv_Datemsg=(TextView) convertView.findViewById(R.id.noticeDate);
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.studentlistlayout, parent, false);hidTxtview
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        MsgList m = DataList.get(position);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            holder.tv_Msg.setText(Html.fromHtml(m.getmsgTxt()));
        }else {
            holder.tv_Msg.setText(Html.fromHtml(m.getmsgTxt(), Html.FROM_HTML_MODE_LEGACY));
        }
//        holder.tv_Msg.setAutoLinkMask(Linkify.WEB_URLS); //
        holder.tv_Datemsg.setText(m.getmsgDate());
       SharedPreferences sf= context.getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        Picasso.get().load(sf.getString("schoolurl", ""))
                .placeholder(R.drawable.noticeimg)
                .error(R.drawable.noticeimg)
                .into(holder.imageViewLogo);

//        holder.imageViewLogo.setImageResource(R.drawable.arrow_back);
        //txtDate.setText(itemDate[position]);
        return convertView;

    }

    public class ViewHolder {
        TextView tv_Datemsg;
        ExpandableTextView tv_Msg;
        ImageView imageViewLogo;

    }
}
