package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AttendenceAdapter extends BaseAdapter {
    private Context context;
    public static List<String> stu_name;
    public static List<String> stu_id;
    public static List<Model> st_id;
    public static List<String> stu_img;
    public static List<String> stu_status;

    String desc;

    public AttendenceAdapter(Context context, List<String> stu_name, List<String> stu_id, List<Model> st_id, List<String> stu_img, List<String> stu_status) {
        this.context = context;
        this.stu_name = stu_name;
        this.stu_id = stu_id;
        this.st_id = st_id;
        this.stu_img = stu_img;
        this.stu_status = stu_status;


    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return stu_name.size();
    }

    @Override
    public Object getItem(int position) {
        return stu_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.attendance_layout, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.status_attendance);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.tv_stuname);
            holder.tv_stuid = (TextView) convertView.findViewById(R.id.tv_stuid);
            holder.imageView = (ImageView) convertView.findViewById(R.id.stu_image);
            holder.et_stuid = (TextView) convertView.findViewById(R.id.et_stuid);
            holder.tl_tableLayout = (TableLayout) convertView.findViewById(R.id.tl_tableLayout);
            holder.llout_att_desc=(LinearLayout) convertView.findViewById(R.id.llout_att_desc);
            holder.editText_att_desc=(EditText)convertView.findViewById(R.id.et_att_description);


            convertView.setTag(holder);

        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvAnimal.setText(stu_name.get(position));
        holder.tv_stuid.setText(stu_id.get(position));
        holder.et_stuid.setText(st_id.get(position).getStudentID());
        desc=holder.editText_att_desc.getText().toString().trim();
        holder.editText_att_desc.setText(""+desc);
        st_id.get(position).setAttDesc(holder.editText_att_desc.getText().toString().trim());
        Picasso.get().load(stu_img.get(position))
                .placeholder(R.drawable.ic_profile)
                .resize(11, 11)
                .into(holder.imageView);

//            if (stu_status.get(position).equals("p")) {
//                holder.checkBox.setChecked(true);
//            } else {
//                holder.checkBox.setChecked(false);
//            }
        holder.checkBox.setChecked(st_id.get(position).getSelected());
//        holder.editText_att_desc.setText("");
        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View tempview=(View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer) holder.checkBox.getTag();
                //    Toast.makeText(context, "Checkbox " + pos + " clicked!", Toast.LENGTH_SHORT).show();

                if (st_id.get(pos).getSelected()) {
                    st_id.get(pos).setSelected(false);
                    holder.checkBox.setChecked(false);
                    holder.llout_att_desc.setVisibility(View.VISIBLE);
                    try {
                        st_id.get(pos).setAttDesc(holder.editText_att_desc.getText().toString().trim());
//                        holder.editText_att_desc.setText(""+holder.editText_att_desc.getText().toString().trim());
                    }catch (Exception e){
                        Log.e("desc",""+e.getMessage());
                    }

//                    Toast.makeText(context, "Absent"+"", Toast.LENGTH_SHORT).show();
                } else {
                    st_id.get(pos).setSelected(true);
                    holder.checkBox.setChecked(true);
                    holder.llout_att_desc.setVisibility(View.GONE);
//                    Toast.makeText(context, "Present", Toast.LENGTH_SHORT).show();

                }

            }
        });


        holder.tl_tableLayout.setTag(R.integer.btnplusview, convertView);
        holder.tl_tableLayout.setTag(position);
        holder.tl_tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                View tempview = (View) holder.tl_tableLayout.getTag(R.integer.btnplusview);
                Integer pos = (Integer) holder.tl_tableLayout.getTag();


                if (st_id.get(pos).getSelected()) {
                    st_id.get(pos).setSelected(false);
                    holder.llout_att_desc.setVisibility(View.VISIBLE);
                    try {
                        st_id.get(pos).setAttDesc(holder.editText_att_desc.getText().toString().trim());
//                        holder.editText_att_desc.setText(""+holder.editText_att_desc.getText().toString().trim());
                    }catch (Exception e){
                        Log.e("desc",""+e.getMessage());
                    }
//                    Toast toast=  Toast.makeText(context, "Absent", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                    holder.checkBox.setChecked(false);
                } else {
                    st_id.get(pos).setSelected(true);
//                    Toast toast = Toast.makeText(context, "Present", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                    holder.checkBox.setChecked(true);
                }
            }
        });



        return convertView;
    }

    public class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvAnimal;
        TextView tv_stuid;
        ImageView imageView;
        TextView et_stuid;
        TableLayout tl_tableLayout;
        EditText editText_att_desc;
        LinearLayout llout_att_desc;


    }

}
