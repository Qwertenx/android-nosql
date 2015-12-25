package com.example.valera.jshelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by Valera_alt on 19-Dec-15.
 */
public class pimage extends Activity {
    private static final int SELECT_PHOTO = 100;
    private static final int SELECT_PHOTO_A = 200;
    ImageView iv;
    EditText post;
    EditText attach;
    EditText name;
    TextView text;
    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pimage_activity);
        Button button1 = (Button) findViewById(R.id.button8);
        Button button2 = (Button) findViewById(R.id.button10);
        Button button3 = (Button) findViewById(R.id.button9);
        post = (EditText) findViewById(R.id.editText8);
        attach = (EditText) findViewById(R.id.editText9);
        name = (EditText) findViewById(R.id.editText10);
        text = (TextView) findViewById(R.id.textView2);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button8:
                        Intent intent = new Intent(pimage.this, MainActivity.class);
                        finishActivity(0);
                        startActivity(intent);
                        break;
                    case R.id.button10:
                        if(name.getText().toString().equals("")){
                            Toast toast = Toast.makeText(getApplicationContext(),"You should fill name",Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        else {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                            break;
                        }
                    case R.id.button9:
                        if(name.getText().toString().equals("")){
                            Toast toast = Toast.makeText(getApplicationContext(),"You should fill name",Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        else if(attach.getText().toString().equals("")){
                            Toast toast = Toast.makeText(getApplicationContext(),"You should fill ID",Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        else {
                            Intent photoPickerIntent2 = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent2.setType("image/*");
                            startActivityForResult(photoPickerIntent2, SELECT_PHOTO_A);
                            break;
                        }
                }
            }
        };
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final StringBuilder URL = new StringBuilder("http://46.101.205.23:4444/test_db/");
        text.setText("Loading...");
        if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO ) {
            Uri fullPhotoUri = data.getData();

            try {

                InputStream iStream = getContentResolver().openInputStream(fullPhotoUri);
                final byte[] photoBytes = getBytes(iStream);
                final JSONObject jsonObject = new JSONObject();
                if(post.getText().toString().equals("")==false)
                jsonObject.put("_id",post.getText().toString());
                final RequestQueue requestQueue = Volley.newRequestQueue(this);
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,"http://46.101.205.23:4444/test_db", jsonObject, (Response.Listener) new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject jSONObject) {
                        Toast toast = Toast.makeText(getApplicationContext(), jSONObject.toString(), Toast.LENGTH_LONG);
                        toast.show();
                        String rev = "";
                        String id = "";
                        try {
                            id = jSONObject.getString("id");
                            rev = jSONObject.getString("rev");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        URL.append(id);
                        URL.append("/");
                        URL.append(name.getText().toString());
                        URL.append(".png?rev=");
                        URL.append(rev);
                        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL.toString(),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        text.setText("");
                                        Toast toast = Toast.makeText(getApplicationContext(), "Response is: " + response, Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        text.setText("");
                                        if(error.toString().equals("com.android.volley.NoConnectionError: java.net.SocketException: sendto failed: EPIPE(Broken pipe)"))
                                        {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Image successful uploaded", Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                        else {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    }
                                })
                        {
                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                return photoBytes;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }, new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError volleyError) {
                        Toast toast = Toast.makeText(getApplicationContext(),volleyError.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                requestQueue.add((Request) jsonObjectRequest);
            }
            catch (IOException e) {
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO_A ){
            Uri fullPhotoUri = data.getData();
            try{
                InputStream iStream = getContentResolver().openInputStream(fullPhotoUri);
                final byte[] photoBytes = getBytes(iStream);
                String id = attach.getText().toString();
                final RequestQueue requestQueue = Volley.newRequestQueue(this);
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject jobject) {
                        String rev = "";
                        String id = "";
                        try {
                                id = jobject.getString("_id");
                                rev = jobject.getString("_rev");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            URL.append(id);
                            URL.append("/");
                            URL.append(name.getText().toString());
                            URL.append(".png?rev=");
                            URL.append(rev);
                            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL.toString(),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            text.setText("");
                                            Toast toast = Toast.makeText(getApplicationContext(), "Response is: " + response, Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            text.setText("");
                                            if(error.toString().equals("com.android.volley.NoConnectionError: java.net.SocketException: sendto failed: EPIPE (Broken pipe)"))
                                            {
                                                Toast toast = Toast.makeText(getApplicationContext(), "Image successful uploaded", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                            else{
                                                Toast toast = Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        }
                                    })
                            {
                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    return photoBytes;
                                }
                            };
                            requestQueue.add(stringRequest);
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError volleyError) {
                        text.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid ID", Toast.LENGTH_LONG);
                        toast.show();
                    }
                };
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL.toString()+id  ,null, (Response.Listener) listener, errorListener);
                requestQueue.add((Request) jsonObjectRequest);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
