package com.xiamubobby.justlauncher.test;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiamubobby.justlauncher.R;
import com.xiamubobby.justlauncher.utility.RawJsonReader;
import com.xiamubobby.justlauncher.view.JustCauseBase;
import com.xiamubobby.justlauncher.view.JustCauseBean;
import com.xiamubobby.justlauncher.view.JustCauseFactory;
import com.xiamubobby.justlauncher.view.RawJustCauseJsonReader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static android.view.View.*;

public class JLTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jltest);
        TextView Logger = ((TextView) findViewById(R.id.textView));
        JustCauseBean bean = null;
        try {
            bean  = RawJustCauseJsonReader.create(this).consume(R.raw.test_json).productAs(JustCauseBean.class);
            Logger.setText(bean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        JustCauseBase bb = JustCauseFactory.create(this).makeFrom(bean);
        bb.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 300);
        params.addRule(RelativeLayout.BELOW, R.id.view2);
        bb.setLayoutParams(params);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        rl.addView(bb);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jltest, menu);
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
