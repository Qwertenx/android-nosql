package com.example.valera.jshelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Valera_alt on 05-Dec-15.
 */
public class activity extends Activity {
    int i = 3;
    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity);
        final ViewGroup layout = (ViewGroup) findViewById(R.id.linear);
        final ArrayList<EditText> al = new ArrayList<EditText>();
        al.add((EditText)this.findViewById(R.id.editText2));
        al.add((EditText)this.findViewById(R.id.editText3));
        al.add((EditText)this.findViewById(R.id.editText4));
        al.add((EditText)this.findViewById(R.id.editText5));
        al.add((EditText)this.findViewById(R.id.editText6));
        al.add((EditText)this.findViewById(R.id.editText7));
        final TextView textView = (TextView) findViewById(R.id.textView3);
        Button button = (Button)this.findViewById(R.id.button4);
        Button button2 = (Button)this.findViewById(R.id.button3);
        Button button3 = (Button)this.findViewById(R.id.button5);
        final RequestQueue requestQueue = Volley.newRequestQueue((Context)this);
        if (this.getIntent().getStringExtra("id").equals((Object)"")) {
            View.OnClickListener onClickListener = new View.OnClickListener(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.button4:
                            JSONObject jSONObject = new JSONObject();
                            if (al.get(0).getText().toString().equals("") || al.get(1).getText().toString().equals("")) {
                                Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) "You should fill at least Key1 and Value1", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else {
                                textView.setText("Loading...");
                                try {
                                    int j=0;
                                    while(j<al.size()-1) {
                                        if(!al.get(j).toString().equals("") && !al.get(j+1).toString().equals(""))
                                        jSONObject.put(al.get(j).getText().toString(), al.get(j+1).getText().toString());
                                        j += 2;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,"http://46.101.205.23:4444/test_db", jSONObject, (Response.Listener) new Response.Listener<JSONObject>() {

                                    public void onResponse(JSONObject jSONObject) {
                                        textView.setText("");
                                        Toast toast = Toast.makeText(getApplicationContext(),jSONObject.toString(), Toast.LENGTH_LONG);
                                        toast.show();
                                        Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                                        activity.this.finishActivity(0);
                                        activity.this.startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {

                                    public void onErrorResponse(VolleyError volleyError) {
                                        textView.setText("");
                                        Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(),"Error:"+volleyError.toString(), Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                });
                                requestQueue.add((Request) jsonObjectRequest);
                            }
                            break;
                        case R.id.button3:
                            Intent intent2 = new Intent((Context)activity.this, (Class)MainActivity.class);
                            activity.this.finishActivity(0);
                            activity.this.startActivity(intent2);
                            break;
                        case R.id.button5:
                            i++;
                            EditText et = new EditText(getApplicationContext());
                            et.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            et.setHint("Key " + i);
                            et.setTextColor(Color.BLACK);
                            layout.addView(et);
                            al.add(et);
                            EditText et2 = new EditText(getApplicationContext());
                            et2.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            et2.setHint("Value " + i);
                            et2.setTextColor(Color.BLACK);
                            layout.addView(et2);
                            al.add(et2);
                            break;
                    }
                }
            };
            button.setOnClickListener((View.OnClickListener) onClickListener);
            button2.setOnClickListener((View.OnClickListener) onClickListener);
            button3.setOnClickListener(onClickListener);

        } else {
            textView.setText("Loading...");
            String string2 = "http://46.101.205.23:4444/test_db/" + this.getIntent().getStringExtra("id");
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jobject) {
                    try {
                        int j=0;
                        Iterator<String> iterator = jobject.keys();
                        while(iterator.hasNext()){
                            if(j>al.size()-1){
                                i++;
                                EditText et = new EditText(getApplicationContext());
                                et.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                et.setHint("Key " + i);
                                et.setTextColor(Color.BLACK);
                                layout.addView(et);
                                al.add(et);
                                EditText et2 = new EditText(getApplicationContext());
                                et2.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                et2.setHint("Value " + i);
                                et2.setTextColor(Color.BLACK);
                                layout.addView(et2);
                                al.add(et2);
                            }
                            String key = iterator.next();
                            al.get(j).setText(key);
                            String value = jobject.getString(key);
                            al.get(j+1).setText(value);
                            j+=2;
                        }
                        textView.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {

                public void onErrorResponse(VolleyError volleyError) {
                    textView.setText("");
                    Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(), "Error:"+volleyError.toString(), Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                    activity.this.finishActivity(0);
                    activity.this.startActivity(intent);
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, string2, null, (Response.Listener) listener, errorListener);
            requestQueue.add((Request) jsonObjectRequest);
            View.OnClickListener onClickListener = new View.OnClickListener() {

                public void onClick(View var1_1) {
                    switch (var1_1.getId()) {
                        case R.id.button4: {
                            textView.setText("Loading...");
                            if (!al.get(0).getText().toString().equals((Object) "") && !al.get(1).getText().toString().equals((Object) "")) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    int j=0;
                                    while(j<al.size()-1) {
                                        if(!al.get(j).toString().equals("") && !al.get(j+1).toString().equals(""))
                                            jsonObject.put(al.get(j).getText().toString(), al.get(j+1).getText().toString());
                                        j += 2;
                                    }
                                } catch (Exception var5_9) {
                                }
                                    JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST, "http://46.101.205.23:4444/test_db", jsonObject, (Response.Listener) new Response.Listener<JSONObject>() {

                                        public void onResponse(JSONObject jSONObject) {
                                            textView.setText("");
                                            Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) jSONObject.toString(), Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    }, new Response.ErrorListener() {

                                        public void onErrorResponse(VolleyError volleyError) {
                                            textView.setText("");
                                            Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) "Error:"+volleyError.toString(), Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    });
                                    requestQueue.add((Request) json);
                                    Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                                    activity.this.finishActivity(0);
                                    activity.this.startActivity(intent);
                            }
                            else {
                                Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) "You should fill at least Key1 and Value1", Toast.LENGTH_LONG).show();
                            }
                        }
                        case R.id.button3: {
                            Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                            activity.this.finishActivity(0);
                            activity.this.startActivity(intent);
                            return;
                        }
                        case R.id.button5:
                            i++;
                            EditText et = new EditText(getApplicationContext());
                            et.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            et.setHint("Key " + i);
                            et.setTextColor(Color.BLACK);
                            layout.addView(et);
                            al.add(et);
                            EditText et2 = new EditText(getApplicationContext());
                            et2.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            et2.setHint("Value " + i);
                            et2.setTextColor(Color.BLACK);
                            layout.addView(et2);
                            al.add(et2);
                            break;
                    }

                    }
            };
            button.setOnClickListener((View.OnClickListener) onClickListener);
            button2.setOnClickListener((View.OnClickListener) onClickListener);
            button3.setOnClickListener(onClickListener);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu2);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2131492995) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}

