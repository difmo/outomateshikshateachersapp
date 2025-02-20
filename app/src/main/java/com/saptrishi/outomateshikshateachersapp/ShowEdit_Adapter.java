package com.saptrishi.outomateshikshateachersapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowEdit_Adapter extends ArrayAdapter<ShowEditHW_Datamodel> implements View.OnClickListener {

    private ArrayList<ShowEditHW_Datamodel> dataSet;
    Context mContext;
//    ImageView viewImage;


    // View lookup cache
    private static class ViewHolder {

        ImageView viewImage;
        TextView tv_submissionDate;
        ImageButton but_hw;
//


    }


    public ShowEdit_Adapter(ArrayList<ShowEditHW_Datamodel> data, Context context) {
        super(context, R.layout.show_hw_ad, data);
        this.dataSet = data;
        this.mContext = context;


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }


    @Override
    public void onClick(View v) {


        int position = (Integer) v.getTag();
        Object object = getItem(position);
        ShowEditHW_Datamodel dataModel = (ShowEditHW_Datamodel) object;


        switch (v.getId()) {

//            case R.id.item_info:

//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();

//                break;


        }

    }


   // private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ShowEditHW_Datamodel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_hw_ad, parent, false);
            viewHolder.viewImage = (ImageView) convertView.findViewById(R.id.photohw);
            viewHolder.but_hw = (ImageButton) convertView.findViewById(R.id.del_hw);
//            viewHolder.but_hw.setText("Delete");


            viewHolder.but_hw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sf = mContext.getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                    String teacherid = sf.getString("empid", "");

//                Toast.makeText(mContext, dataModel.gethwurl()+" : "+dataModel.gethwid(), Toast.LENGTH_SHORT).show();

                    deleteHomework(teacherid, dataModel.gethwid(), dataModel);

                }
            });

            viewHolder.tv_submissionDate = (TextView) convertView.findViewById(R.id.dateOfSubmission);

            viewHolder.tv_submissionDate.setText(dataModel.getDateOfSubmission());

            Date con_date = null;
            Date to = null;

            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            Calendar cal = Calendar.getInstance();

            String today = sdf.format(cal.getTime());


            try {
                con_date = sdf.parse(dataModel.refgetdateofHomework());
                to = sdf.parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (con_date.before(to)) {
                viewHolder.but_hw.setVisibility(View.GONE);
                Log.e("dateiftru", " : selecteddate " + con_date + " today " + today);
            } else {
                Log.e("dateelsetru", " : " + con_date + " " + today);
            }


            result = convertView;
            String url = dataModel.gethwurl();
            Log.e("url12", url);
            //url = url.replace(" ", "%20");
            Log.e("url123", url);


            Picasso.get().load(url).fit().centerCrop()
                    .placeholder(R.drawable.pdf)
                    .error(R.drawable.pdf)
                    .into(viewHolder.viewImage);

//            new ShowEdit_Adapter.DownloadImageFromInternet(viewHolder.viewImage)
//                    .execute(url);
//            convertView.setTag(viewHolder);
        } else {
           // viewHolder = (ViewHolder) convertView.getTag();
           // result = convertView;
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_hw_ad, parent, false);
            viewHolder.viewImage = (ImageView) convertView.findViewById(R.id.photohw);
            viewHolder.but_hw = (ImageButton) convertView.findViewById(R.id.del_hw);
//            viewHolder.but_hw.setText("Delete");


            viewHolder.but_hw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sf = mContext.getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                    String teacherid = sf.getString("empid", "");

//                Toast.makeText(mContext, dataModel.gethwurl()+" : "+dataModel.gethwid(), Toast.LENGTH_SHORT).show();

                    deleteHomework(teacherid, dataModel.gethwid(), dataModel);

                }
            });

            viewHolder.tv_submissionDate = (TextView) convertView.findViewById(R.id.dateOfSubmission);

            viewHolder.tv_submissionDate.setText(dataModel.getDateOfSubmission());

            Date con_date = null;
            Date to = null;

            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            Calendar cal = Calendar.getInstance();

            String today = sdf.format(cal.getTime());


            try {
                con_date = sdf.parse(dataModel.refgetdateofHomework());
                to = sdf.parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (con_date.before(to)) {
                viewHolder.but_hw.setVisibility(View.GONE);
                Log.e("dateiftru", " : selecteddate " + con_date + " today " + today);
            } else {
                Log.e("dateelsetru", " : " + con_date + " " + today);
            }


            result = convertView;
            String url = dataModel.gethwurl();
            Log.e("url12", url);
            //url = url.replace(" ", "%20");
            Log.e("url123", url);


            Picasso.get().load(url).fit().centerCrop()
                    .placeholder(R.drawable.pdf)
                    .error(R.drawable.pdf)
                    .into(viewHolder.viewImage);
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        //lastPosition = position;

//        viewHolder.txtName.setText(dataModel.gethw_img());
//        viewHolder.txtType.setText(dataModel.gethwid());
//        viewHolder.txtVersion.setText("");
//        viewHolder.viewImage.setImageBitmap(dataModel.gethw_img());


//         Return the completed view to render on scree
//        viewHolder.option.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (view.getId()) {
//                    case R.id.option:
//                        showPopupMenu(view, position,dataModel.getempid(),dataModel.getSubjectid(),dataModel.getClassid(),dataModel.getFinaldaytimetableid(),dataModel.getDaateofHomework());
//                        break;
//                }
//            }
//        });
//
//        viewHolder.iconSelectPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (view.getId()) {
//                    case R.id.iconSelectPhoto:
//                        showPopupMenu1(view, position,dataModel.getempid(),dataModel.getSubjectid(),dataModel.getClassid(),dataModel.getFinaldaytimetableid());
//                        break;
//                }
//            }
//        });
        return convertView;
    }

    private void deleteHomework(String teacherid, String gethwid, final ShowEditHW_Datamodel dataModel) {


        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(mContext);
            //this is the url where you want to send the request
            String url = Url_Symbol.url + "/HomeworkDelete";
            Log.e("url", url);


            final ProgressDialog pDialog = new ProgressDialog(mContext);

            pDialog.setTitle("Deleting...");
            pDialog.setMessage("Uploading your file");
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("HWid", gethwid);
            jsonBody.put("teacherid", teacherid);

            final String requestBody = jsonBody.toString();
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.

                    pDialog.dismiss();

                    if (response.equals("200")) {
//                               Log.e("ResponseinElse", response);
                        //Log.e("ResponseSuccess", response);


                        Intent intent = new Intent(mContext, EditHomeWorkActivity.class);
                        intent.putExtra("getempid", dataModel.refgetemp());
                        intent.putExtra("subjectid", dataModel.refgetsubjectid());
                        intent.putExtra("classid", dataModel.refgetclasid());
                        intent.putExtra("finaldaytimetableid", dataModel.refgetfinaldaytimeTableid());
                        intent.putExtra("dateodhomework", dataModel.refgetdateofHomework());
                        mContext.startActivity(intent);

                        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e("ResponseinElse", response);
//
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    pDialog.dismiss();

                    VolleyLog.d("", "Error...." + error);
                    Log.e("VolleyError", error + "");
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }


                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            // Add the request to the RequestQueue.
//                    queue.add(stringRequest);

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            // Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {

            Log.e("imageresult", result + "");
            if ((result != null))
                imageView.setImageBitmap(result);
        }
    }

}

