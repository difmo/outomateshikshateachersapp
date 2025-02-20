package com.saptrishi.outomateshikshateachersapp.adapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.model.ClassworkviewList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Classworkviewadapter extends RecyclerView.Adapter<Classworkviewadapter.ViewHolder> {

    DownloadManager downloadManager;
    Context context;
    List<ClassworkviewList> jsonObject;
    ProgressDialog progressDialog;
    String result = "";

    public Classworkviewadapter(Context applicationContext, List<ClassworkviewList> jsonObject_) {
        context = applicationContext;
        jsonObject = jsonObject_;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.viewimageclass_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            //holder.date.setText(jsonObject.get(position).getContytypID());
            if (jsonObject.get(position).getTopicName() != null && !jsonObject.get(position).getTopicName().equalsIgnoreCase("")) {
                holder.name.setText(jsonObject.get(position).getTopicName());
            } else {
                holder.name.setText("You Tube");
            }

            if (jsonObject.get(position).getContytypID().equalsIgnoreCase("1")) {
                // youtube
                holder.CW_Upload_path_img.setBackgroundResource(R.drawable.ic_menu_slideshow);
            }else  if (jsonObject.get(position).getContytypID().equalsIgnoreCase("2")){
                // JPG
                holder.CW_Upload_path_img.setBackgroundResource(R.drawable.image);//img
            } else  if (jsonObject.get(position).getContytypID().equalsIgnoreCase("3")){
                //PDF
                holder.CW_Upload_path_img.setBackgroundResource(R.drawable.ic_baseline_insert_doc_file_24);
            }
            /*holder.CW_Upload_path_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DownloadFile().execute(*//*"http://maven.apache.org/maven-1.x/maven.pdf"*//*jsonObject.get(position).getFilename(), jsonObject.get(position).getTopicName() + ".pdf");

                }
            });*/

            holder.option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.e("viewid", view.getId() + " " + userInfos.get(i).getName().toString() + " " + name.getText());

//                    switch (view.getId()) {
//                        case R.id.option:
//                            showPopupMenu(view, position);
//                            break;
//                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonObject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, name;
        ImageView CW_Upload_path_img, option;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.name);
            CW_Upload_path_img = itemView.findViewById(R.id.CW_Upload_path_img);
            option = itemView.findViewById(R.id.option);

        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Downloading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();//getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File folder = new File(extStorageDirectory, "PDF");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
            result = FileDownloader.downloadFile(fileUrl, pdfFile);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);//new
            Uri uri = Uri.parse(fileUrl);
            Log.e("if_msgurl", uri + "");
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Long aLong = downloadManager.enqueue(request);// new
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null)
                progressDialog.dismiss();

            if (result != null && result.equalsIgnoreCase("200")) {
                Toast.makeText(context, "File Downloaded Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "File Downloaded Failed!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // getting the popup menu
    private void showPopupMenu(final View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());
        try {
            for (int i = 0; i < jsonObject.get(pos).getFilelistcw().size(); i++) {
                if (jsonObject.get(pos).getFilelistcw().get(i).getFilename() != null) {
                    if (jsonObject.get(pos).getFilelistcw().get(i).getContytypID().equalsIgnoreCase("1")) {
                        popupMenu.getMenu().add(jsonObject.get(pos).getFilelistcw().get(i).getVideoLink());
                    } else {
                        popupMenu.getMenu().add(jsonObject.get(pos).getFilelistcw().get(i).getFilename());
                    }

                } else {
                    popupMenu.getMenu().add("n/a");
                }

            }

        } catch (Exception e) {

            Toast.makeText(context, "Some Error Occured!", Toast.LENGTH_SHORT).show();
        }

//        popupMenu.getMenu().add("AGIL");
//        popupMenu.getMenu().add("AGILarasan");
//        popupMenu.getMenu().add("Arasan");

        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                try {
                    String url = "";//"http://teacherappservice.outomate.com/ClassWork_file/br10_Class2-A_Physics_19-Nov-2020/Physics_19-Nov-2020_07_09_45.jpg";

                    String conID = jsonObject.get(pos).getContytypID();

                    if (menuItem.getTitle().toString().equalsIgnoreCase("Click to download file")) {
                        return false;
                    }

                    if (conID.equalsIgnoreCase("1")) {
                        //utube Link
                        url = menuItem.getTitle().toString();//jsonObject.get(pos).getFilelistcw().get(0).getVideoLink();
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        try {
                            context.startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            context.startActivity(webIntent);
                        }

                    } else if (conID.equalsIgnoreCase("2")) {
                        //jpg
                        //url = jsonObject.get(pos).getFilelistcw().get(0).getCW_Upload_path_img();
                        for (int k = 0; k < jsonObject.get(pos).getFilelistcw().size(); k++) {
                            if (jsonObject.get(pos).getFilelistcw().get(k).getCW_Upload_path_img().contains(menuItem.getTitle().toString())) {
                                url = jsonObject.get(pos).getFilelistcw().get(k).getCW_Upload_path_img();
                            }
                        }
                        Log.e("if_msgurl", url);
                        url = url.replace(" ", "%20");
                        Log.e("if_msgurl", url);
                        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(url);
                        Log.e("if_msgurl", uri + "");
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        Long aLong = downloadManager.enqueue(request);

                    } else if (conID.equalsIgnoreCase("3")) {
                        //pdf
                        //url = jsonObject.get(pos).getFilelistcw().get(0).getCW_Upload_path_img();
                        for (int k = 0; k < jsonObject.get(pos).getFilelistcw().size(); k++) {
                            if (jsonObject.get(pos).getFilelistcw().get(k).getCW_Upload_path_img().contains(menuItem.getTitle().toString())) {
                                url = jsonObject.get(pos).getFilelistcw().get(k).getCW_Upload_path_img();
                            }
                        }
                        Log.e("if_msgurl", url);
                        url = url.replace(" ", "%20");
                        Log.e("if_msgurl", url);
                        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(url);
                        Log.e("if_msgurl", uri + "");
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        Long aLong = downloadManager.enqueue(request);
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "File Downloading failed!", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

}


