package minibird.todaymise.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import minibird.todaymise.R;

public class MaskActivity extends AppCompatActivity {

    private ImageButton homeBtn;
    private ImageButton maskBtn;
    private ImageButton infoBtn;
    private ImageButton settingBtn;
    private TextView title;
    private Intent intent;
    private Button buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask);

        FindView();
        homeBtn.setImageResource(R.drawable.menu_home_off);
        maskBtn.setImageResource(R.drawable.menu_mask_on);
        title.setPaintFlags(title.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        // 페이지 이동
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.MainActivity.class);
                maskBtn.setImageResource(R.drawable.menu_mask_off);
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
        infoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), minibird.todaymise.activity.InformationActivity.class);
                maskBtn.setImageResource(R.drawable.menu_mask_off);
                startActivity(intent);
                finish();
            }
        });
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://msearch.shopping.naver.com/search/all.nhn?query=kf%EB%A7%88%EC%8A%A4%ED%81%AC&frm=NVSCPRO"));
                startActivity(intent);
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
        title = (TextView)findViewById(R.id.mask_title_tv);
        buyBtn = (Button)findViewById(R.id.mask_btn);
    }
}
