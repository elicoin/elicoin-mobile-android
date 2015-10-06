package com.breadwallet.presenter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.breadwallet.R;
import com.breadwallet.wallet.BRWalletManager;

public class IntroShowPhraseActivity extends Activity {
    Button remindMeLater;
    RelativeLayout writeDownLayout;
    ImageView checkBox;
    private TextView thePhrase;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_show_phrase);

        if (savedInstanceState != null) {
            return;
        }
        BRWalletManager m = BRWalletManager.getInstance();
        thePhrase = (TextView) findViewById(R.id.the_phrase_at_startup);
        remindMeLater = (Button) findViewById(R.id.remind_me_later_button);
        writeDownLayout = (RelativeLayout) findViewById(R.id.write_down_notice_layout);
        checkBox = (ImageView) findViewById(R.id.write_down_check_box);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckBoxImage();
            }
        });
        writeDownLayout.setVisibility(View.GONE);
        thePhrase.setText(m.getPhrase(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!IntroShowPhraseActivity.this.isDestroyed())
                    writeDownLayout.setVisibility(View.VISIBLE);
            }
        }, 10000);
        remindMeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void startMainActivity() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        if (!IntroShowPhraseActivity.this.isDestroyed()) {
            finish();
        }
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

    void setCheckBoxImage() {
        checkBox.setImageResource(!checked ? R.drawable.checkbox_checked : R.drawable.checkbox_empty);
        remindMeLater.setText(!checked ? getResources().getString(R.string.done) :
                getResources().getString(R.string.remind_me_later));
        checked = !checked;
    }
}
