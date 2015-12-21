package com.example.valera.jshelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) this.findViewById(R.id.button1);
        Button button2 = (Button) this.findViewById(R.id.button2);
        Button button3 = (Button) this.findViewById(R.id.button);
        Button button4 = (Button) findViewById(R.id.button6);
        final EditText edit = (EditText) this.findViewById(R.id.editText);
        View.OnClickListener onClickListener = new View.OnClickListener() {

            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button: {
                        if (edit.getText().toString().equals("")) {

                            Toast toast = Toast.makeText(MainActivity.this.getApplicationContext(), "You should fill ID", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        Intent intent = new Intent((Context) MainActivity.this, (Class) image.class);
                        intent.putExtra("id", edit.getText().toString());
                        MainActivity.this.finishActivity(0);
                        MainActivity.this.startActivity(intent);
                        break;
                    }
                    case R.id.button1: {
                        Intent intent = new Intent((Context) MainActivity.this, (Class) activity.class);
                        intent.putExtra("id", "");
                        MainActivity.this.finishActivity(0);
                        MainActivity.this.startActivity(intent);
                        break;
                    }
                    case R.id.button2:

                        if (edit.getText().toString().equals("")) {

                            Toast toast = Toast.makeText(MainActivity.this.getApplicationContext(), "You should fill ID", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        Intent intent = new Intent((Context) MainActivity.this, (Class) activity.class);
                        intent.putExtra("id", edit.getText().toString());
                        MainActivity.this.finishActivity(0);
                        MainActivity.this.startActivity(intent);
                        break;
                    case R.id.button6:
                        Intent intent2 = new Intent((Context) MainActivity.this, (Class) pimage.class);
                        intent2.putExtra("id", edit.getText().toString());
                        MainActivity.this.finishActivity(0);
                        MainActivity.this.startActivity(intent2);
                        break;
                }

            }
        };
        button.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
