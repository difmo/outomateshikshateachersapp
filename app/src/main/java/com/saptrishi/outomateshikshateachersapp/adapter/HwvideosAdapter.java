package com.saptrishi.outomateshikshateachersapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.View.Activity.HwShowVideoActivity;
import com.saptrishi.outomateshikshateachersapp.model.HwVideos;

import java.util.List;

public class HwvideosAdapter extends RecyclerView.Adapter<HwvideosAdapter.MyViewHolder> {

    List<HwVideos> hwVideosList;
    Context context;

    public HwvideosAdapter(Context context, List<HwVideos> hwVideosList) {
        this.hwVideosList = hwVideosList;
        this.context = context;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hwvideos_list_item, parent, false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HwVideos hwVideos = hwVideosList.get(position);
        holder.textView_stu_name.setText(hwVideos.getStudentname());
        holder.textView_topic_name.setText(hwVideos.getTopicname());
        holder.textView_vid_date.setText(hwVideos.getVidDate());
        holder.textView_vid_date.setText(hwVideos.getVidUrl());


        holder.imageViewVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StudentName = "" + hwVideosList.get(position).getStudentname();
                String TopicName = "" + hwVideosList.get(position).getTopicname();
                String VideoDate = "" + hwVideosList.get(position).getVidDate();
                String VideoUrl = "" + hwVideosList.get(position).getVidUrl();
                String uri = VideoUrl.replace("https://youtu.be/", "");
                Log.e("replace", uri);

                final String vid_url = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + uri + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
//                         final String vid_url="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+uri+"\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("please wait\n video loading...");
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                                intent.putExtra("vid_url",vid_url);
//                                Log.e("AfterlinkAfterSend",vid_url);
//                                intent.putExtra("VideoUrl",VideoUrl);
//                                intent.putExtra("uri",uri);
                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(VideoUrl));
                            context.startActivity(i);
                        } catch (Exception e) {
                            Toast.makeText(context, "invalid url", Toast.LENGTH_SHORT).show();
                        }


                        progressDialog.dismiss();
                    }
                }, 2000);
                progressDialog.show();
//                intent.putExtra("studentname",StudentName);
//                intent.putExtra("topicname",TopicName);
//                intent.putExtra("viddate",VideoDate);
//                intent.putExtra("vid_url",vid_url);
//                intent.putExtra("VideoUrl",VideoUrl);
//                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return hwVideosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_stu_name, textView_topic_name, textView_vid_date;
        ImageView imageViewVid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_stu_name = itemView.findViewById(R.id.stu_name);
            textView_topic_name = itemView.findViewById(R.id.vid_topic);
            textView_vid_date = itemView.findViewById(R.id.vid_date);
            imageViewVid = itemView.findViewById(R.id.image_vid);
        }
    }

}
