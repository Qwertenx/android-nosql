package com.example.valera.jshelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
/**
 * Created by Valera_alt on 05-Dec-15.
 */
public class activity extends Activity {
    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity);
        final EditText editText = (EditText)this.findViewById(R.id.editText2);
        final EditText editText2 = (EditText)this.findViewById(R.id.editText3);
        final EditText editText3 = (EditText)this.findViewById(R.id.editText4);
        final EditText editText4 = (EditText)this.findViewById(R.id.editText5);
        final EditText editText5 = (EditText)this.findViewById(R.id.editText6);
        final EditText editText6 = (EditText)this.findViewById(R.id.editText7);
        Button button = (Button)this.findViewById(R.id.button4);
        Button button2 = (Button)this.findViewById(R.id.button3);
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
                            if (editText.getText().toString().equals("") || editText2.getText().toString().equals("")) {
                                Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) "You should fill at least Key1 and Value1", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else {
                                try {
                                    jSONObject.put(editText.getText().toString(), editText2.getText().toString());
                                    if (!editText3.getText().toString().equals("") && !editText4.getText().toString().equals("")) {
                                        jSONObject.put(editText3.getText().toString(),editText4.getText().toString());
                                    }
                                    if (!editText5.getText().toString().equals("") && !editText6.getText().toString().equals("")) {
                                        jSONObject.put(editText5.getText().toString(), editText6.getText().toString());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,"http://46.101.205.23:4444/test_db", jSONObject, (Response.Listener) new Response.Listener<JSONObject>() {

                                    public void onResponse(JSONObject jSONObject) {
                                        Toast toast = Toast.makeText(getApplicationContext(),jSONObject.toString(), Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                }, new Response.ErrorListener() {

                                    public void onErrorResponse(VolleyError volleyError) {
                                        Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(),volleyError.getMessage(), Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                });
                                requestQueue.add((Request) jsonObjectRequest);
                                Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                                activity.this.finishActivity(0);
                                activity.this.startActivity(intent);
                            }
                            break;
                        case R.id.button3:
                            Intent intent2 = new Intent((Context)activity.this, (Class)MainActivity.class);
                            activity.this.finishActivity(0);
                            activity.this.startActivity(intent2);
                            break;
                    }
                }
            };
            button.setOnClickListener((View.OnClickListener) onClickListener);
            button2.setOnClickListener((View.OnClickListener) onClickListener);

        } else {
            final String[] arrstring = new String[2];
            String string2 = "http://46.101.205.23:4444/test_db/" + this.getIntent().getStringExtra("id");
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject var1_1) {

                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {

                public void onErrorResponse(VolleyError volleyError) {
                    Toast toast = Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) "Invalid ID", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                    activity.this.finishActivity(0);
                    activity.this.startActivity(intent);
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, string2, null, (Response.Listener) listener, errorListener);
            requestQueue.add((Request) jsonObjectRequest);
            View.OnClickListener onClickListener = new View.OnClickListener() {

                /*
                 * Unable to fully structure code
                 * Enabled aggressive exception aggregation
                 */
                public void onClick(View var1_1) {
                    switch (var1_1.getId()) {
                        case 2131492973: {
                            JSONObject jsonObject = null;
                            if (!editText.getText().toString().equals((Object) "") && !editText2.getText().toString().equals((Object) ""))
                                break;
                            Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) "You should fill at least Key1 and Value1",Toast.LENGTH_LONG).show();
                        }
                        case 2131492974: {
                            Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                            activity.this.finishActivity(0);
                            activity.this.startActivity(intent);
                            return;
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("_id", (Object) arrstring[0]);
                        jsonObject.put("_rev", (Object) arrstring[1]);
                        jsonObject.put(editText.getText().toString(), (Object) editText2.getText().toString());
                        if (!editText3.getText().toString().equals((Object) "") && !editText4.getText().toString().equals((Object) "")) {
                            jsonObject.put(editText3.getText().toString(), (Object) editText4.getText().toString());
                        }
                        if (!editText5.getText().toString().equals((Object) "") && !editText6.getText().toString().equals((Object) "")) {
                            jsonObject.put(editText5.getText().toString(), (Object) editText6.getText().toString());
                        }
                    } catch (Exception var5_9) {
                    }
                    lbl28:
                    // 2 sources:
                    do {
                        JsonObjectRequest json = new JsonObjectRequest(1, "http://46.101.205.23:4444/test_db", jsonObject, (Response.Listener) new Response.Listener<JSONObject>() {

                            public void onResponse(JSONObject jSONObject) {
                                Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) jSONObject.toString(), Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {

                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText((Context) activity.this.getApplicationContext(), (CharSequence) "Error", Toast.LENGTH_LONG).show();
                            }
                        });
                        requestQueue.add((Request) json);
                        Intent intent = new Intent((Context) activity.this, (Class) MainActivity.class);
                        activity.this.finishActivity(0);
                        activity.this.startActivity(intent);
                        break;
                    } while (true);
                }

            };
            button.setOnClickListener((View.OnClickListener) onClickListener);
            button2.setOnClickListener((View.OnClickListener) onClickListener);
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

