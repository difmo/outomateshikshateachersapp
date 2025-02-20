package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView tv_versionnames;
    private String firstTime;

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        tv_versionnames = findViewById(R.id.tv_versionname);
        tv_versionnames.setText(BuildConfig.VERSION_NAME);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("ShikshaContainer1", MODE_PRIVATE);
        firstTime = sharedPreferences.getString("firstTime", "0");
        Log.e("firstTime", firstTime);

        // Check for storage permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, STORAGE_PERMISSION_CODE);
        }

        startAppLogic();
    }

    private void startAppLogic() {
        Log.e("Anand", "Granted");

        if (!Connectivity.isConnected(getApplicationContext()) && firstTime.equals("0")) {
            showNoInternetDialog();
        } else {
            checkValues();
        }
    }

    private void showNoInternetDialog() {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, "For first-time login, you must be connected to the Internet", Toast.LENGTH_LONG).show();
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.connection_dialog);
            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
            dialogButton.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void checkValues() {
        new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (firstTime.equals("0")) {
                    if (!Connectivity.isConnected(getApplicationContext())) {
                        showNoInternetDialog();
                    } else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        }).start();
    }

    public void sendTokenToServer(final String token, String empid, String iemino, final Context ct) {
        try {
            RequestQueue queue = Volley.newRequestQueue(ct);
            String url = Url_Symbol.url + "TokenAppMember";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("TokenId", token);
            jsonBody.put("MmobileIEMI", iemino);
            jsonBody.put("Stuid", empid);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                if (response.equals("200")) {
                    SharedPreferences.Editor editor = getSharedPreferences("ShikshaContainer1", MODE_PRIVATE).edit();
                    editor.putString("firstTime", "0");
                    editor.apply();
                    Log.e("Token Status", "Token sent successfully");
                } else {
                    Log.e("Token Response", response);
                    Toast.makeText(ct, "Token not sent", Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                VolleyLog.d("", "Error: " + error);
                Toast.makeText(ct, "Token not sent: " + error, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Encoding error", uee);
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    return Response.success(response != null ? String.valueOf(response.statusCode) : "", HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            queue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
