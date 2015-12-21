package com.example.valera.jshelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Button button = (Button) findViewById(R.id.button7);
        final TextView textView =(TextView) findViewById(R.id.textView);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(image.this,MainActivity.class);
                finishActivity(0);
                startActivity(intent);
            }
        };
        button.setOnClickListener(onClickListener);
        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        textView.setText("Loading...");
        final RequestQueue requestAdministrator = Volley.newRequestQueue((Context) this);
        final StringBuilder[] url = {new StringBuilder("http://46.101.205.23:4444/test_db/" + this.getIntent().getStringExtra("id") + "/")};
        String string2 = "http://46.101.205.23:4444/test_db/" + this.getIntent().getStringExtra("id");
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject jobject) {
                try {
                    JSONObject jo = (JSONObject) jobject.get("_attachments");
                    Iterator<String> it = jo.keys();
                    String str = it.next();
                    url[0].append(str);
                    ImageRequest ir = new ImageRequest(url[0].toString(), new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                           iv.setImageBitmap(response);
                            textView.setText("");
                        }
                    }, 0, 0, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast toast = Toast.makeText((Context) getApplicationContext(), "Invalid ID", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                    requestAdministrator.add(ir);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            public void onErrorResponse(VolleyError volleyError) {
                Toast toast = Toast.makeText((Context) image.this.getApplicationContext(), "Invalid ID", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent((Context) image.this, (Class) MainActivity.class);
                finishActivity(0);
                startActivity(intent);
            }
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, string2, null, (Response.Listener) listener, errorListener);
        requestAdministrator.add((Request) jsonObjectRequest);

    }

}
