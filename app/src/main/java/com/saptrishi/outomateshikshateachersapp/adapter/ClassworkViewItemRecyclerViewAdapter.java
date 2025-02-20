package com.saptrishi.outomateshikshateachersapp.adapter;

import android.content.Context;
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
import com.saptrishi.outomateshikshateachersapp.model.ClassWorkViewItemData;

import java.util.List;

public class ClassworkViewItemRecyclerViewAdapter extends RecyclerView.Adapter<ClassworkViewItemRecyclerViewAdapter.MyViewHolder> {
Context context;
List<ClassWorkViewItemData> classWorkViewItemDataList;

public ClassworkViewItemRecyclerViewAdapter(Context context,List<ClassWorkViewItemData> classWorkViewItemDataList){
    this.context=context;
    this.classWorkViewItemDataList=classWorkViewItemDataList;
}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.classwork_view_item_list_layout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClassWorkViewItemData classWorkViewItemData=classWorkViewItemDataList.get(position);
    holder.classworkViewVideoLinkTopicName.setText(classWorkViewItemData.getClassworkviewVideLinkTopicName());
    holder.classworkViewImageLinkTopicName.setText(classWorkViewItemData.getClassworkviewImageLinkTopicName());
    holder.classworkViewFileLinkTopicName.setText(classWorkViewItemData.getClassworkviewFileLinkTopicName());



        holder.classworkViewVideoLinkImageOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                        showVideoPopupMenu(view);

            }
        });


        holder.classworkViewImageLinkImageOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                showImagePopupMenu(view);

            }
        });

        holder.classworkViewFileLinkImageOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                showFilePopupMenu(view);

            }
        });
    }

    private void showVideoPopupMenu(final View view) {                 //show video link popup
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_view_classwork_popup_video_link, popupMenu.getMenu()); // menu classwork
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

//                switch (menuItem.getItemId())
//                {
//                    case R.id.cw_menu_videoLink:
//                        Toast.makeText(context, "video link", Toast.LENGTH_SHORT).show();
//                       break;
//                }

                return true;
            }
        });

    }


    private void showImagePopupMenu(final View view) {                 //show image  link popup
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_view_classwork_popup_image_link, popupMenu.getMenu()); // menu classwork
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

//                switch (menuItem.getItemId())
//                {
//                    case R.id.cw_menu_imageLink:
//                        Toast.makeText(context, "image link", Toast.LENGTH_SHORT).show();
//                        break;
//                }

                return true;
            }
        });

    }

    private void showFilePopupMenu(final View view) {                 //show file link popup
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_view_classwork_popup_file_link, popupMenu.getMenu()); // menu classwork
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem){

                switch(menuItem.getItemId())
                {
//                    case R.id.cw_menu_fileLink:
//                        Toast.makeText(context, "file link", Toast.LENGTH_SHORT).show();
//                        break;
                }
                return true;
            }
        });

    }



    @Override
    public int getItemCount() {
        return classWorkViewItemDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView classworkViewVideoLinkImageOption,classworkViewImageLinkImageOption,classworkViewFileLinkImageOption;
        TextView classworkViewVideoLinkTopicName,classworkViewImageLinkTopicName,classworkViewFileLinkTopicName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classworkViewFileLinkImageOption=(ImageView)itemView.findViewById(R.id.class_workviewFileLinkImageOption);
            classworkViewImageLinkImageOption=(ImageView)itemView.findViewById(R.id.class_workviewImageLinkImageOption);
            classworkViewVideoLinkImageOption=(ImageView)itemView.findViewById(R.id.class_workviewVideoLinkImageOption);
            classworkViewFileLinkTopicName=(TextView) itemView.findViewById(R.id.classwork_viewFileLinkTopinName);
            classworkViewImageLinkTopicName=(TextView)itemView.findViewById(R.id.classwork_viewImageLinkTopinName);
            classworkViewVideoLinkTopicName=(TextView)itemView.findViewById(R.id.classwork_viewVideoLinkTopinName);
        }
    }
}
