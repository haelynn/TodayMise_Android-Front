package minibird.todaymise.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import minibird.todaymise.R;

public class InformationActivity extends AppCompatActivity {

    private ImageButton homeBtn;
    private ImageButton maskBtn;
    private ImageButton infoBtn;
    private ImageButton settingBtn;
    private TextView title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        FindView();
        homeBtn.setImageResource(R.drawable.menu_home_off);
        infoBtn.setImageResource(R.drawable.menu_info_on);
        title.setPaintFlags(title.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        // 페이지 이동
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.MainActivity.class);
                infoBtn.setImageResource(R.drawable.menu_info_off);
                finish();
            }
        });
        maskBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.MaskActivity.class);
                infoBtn.setImageResource(R.drawable.menu_info_off);
                startActivity(intent);
                finish();
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.SettingActivity.class);
                infoBtn.setImageResource(R.drawable.menu_info_off);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    // findview
    private void FindView(){
        homeBtn = (ImageButton)findViewById(R.id.menu_home_btn);
        maskBtn = (ImageButton)findViewById(R.id.menu_mask_btn);
        infoBtn = (ImageButton)findViewById(R.id.menu_info_btn);
        settingBtn = (ImageButton)findViewById(R.id.menu_setting_btn);
        title = (TextView)findViewById(R.id.info_title_tv);
    }
}
