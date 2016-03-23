
/**
 * Created by javerbukh on 3/21/16 Using code from EnvatoTuts coding tutorials
 */

package com.example.javerbukh.myfirstapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.app.Dialog;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MyActivity extends AppCompatActivity implements OnClickListener {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    private DoodleView doodleView;
    private ImageButton currPaint;
    private Button drawBtn, clearBtn, backgroundBtn, opacityBtn;
    private float smallBrush, mediumBrush, largeBrush;

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doodleView = (DoodleView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        drawBtn = (Button)findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        doodleView.setBrushSize(mediumBrush);

        clearBtn = (Button)findViewById(R.id.clear_btn);
        clearBtn.setOnClickListener(this);

        backgroundBtn = (Button)findViewById(R.id.background_btn);
        backgroundBtn.setOnClickListener(this);

        opacityBtn = (Button)findViewById(R.id.opacity_btn);
        opacityBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    public void paintClicked(View view){
        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            doodleView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.draw_btn) {
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");
            brushDialog.setContentView(R.layout.brush_chooser);
            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doodleView.setBrushSize(smallBrush);
                    doodleView.setLastBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doodleView.setBrushSize(mediumBrush);
                    doodleView.setLastBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });

            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doodleView.setBrushSize(largeBrush);
                    doodleView.setLastBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        }
        else if(view.getId()==R.id.clear_btn){
            doodleView.startNew();
        }
        else if(view.getId()==R.id.background_btn){
            final Dialog backDialog = new Dialog(this);
            backDialog.setTitle("Background color:");
            backDialog.setContentView(R.layout.background_chooser);
            ImageButton blackBtn = (ImageButton) backDialog.findViewById(R.id.black_back);
            blackBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doodleView.changeBackground(Color.BLACK);
                    backDialog.dismiss();
                }
            });
            ImageButton grayBtn = (ImageButton) backDialog.findViewById(R.id.gray_back);
            grayBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doodleView.changeBackground(Color.GRAY);
                    backDialog.dismiss();
                }
            });
            ImageButton whiteBtn = (ImageButton) backDialog.findViewById(R.id.white_back);
            whiteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doodleView.changeBackground(Color.WHITE);
                    backDialog.dismiss();
                }
            });
            backDialog.show();
        }
        else if(view.getId()==R.id.opacity_btn){
            final Dialog seekDialog = new Dialog(this);
            seekDialog.setTitle("Opacity level:");
            seekDialog.setContentView(R.layout.opacity_chooser);
            final TextView seekTxt = (TextView)seekDialog.findViewById(R.id.opq_txt);
            final SeekBar seekOpq = (SeekBar)seekDialog.findViewById(R.id.opacity_seek);
            seekOpq.setMax(100);
            int currLevel = doodleView.getPaintAlpha();
            seekTxt.setText(currLevel+"%");
            seekOpq.setProgress(currLevel);
            seekOpq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekTxt.setText(Integer.toString(progress) + "%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            Button opqBtn = (Button)seekDialog.findViewById(R.id.opq_ok);
            opqBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    doodleView.setPaintAlpha(seekOpq.getProgress());
                    seekDialog.dismiss();
                }
            });
            seekDialog.show();
        }
    }
}
