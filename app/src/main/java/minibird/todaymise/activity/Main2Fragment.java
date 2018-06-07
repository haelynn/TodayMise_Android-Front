package minibird.todaymise.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import minibird.todaymise.R;

public class Main2Fragment extends Fragment{

    private Button buyBtn;
    private TextView compareTxt, compList1, compList2, compList3, compList4, compList5, compList6;
    private ImageView compImg1, compImg2, compImg3, compImg4, compImg5, compImg6;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ConstraintLayout constraintLayout = (ConstraintLayout)inflater.inflate(R.layout.main2, container, false);

        // findView
        buyBtn = (Button)constraintLayout.findViewById(R.id.main_buy_btn);
        compareTxt = (TextView)constraintLayout.findViewById(R.id.main_compare2_iv);
        compList1 = (TextView)constraintLayout.findViewById(R.id.main_comp_tv1_2);
        compList2 = (TextView)constraintLayout.findViewById(R.id.main_comp_tv2_2);
        compList3 = (TextView)constraintLayout.findViewById(R.id.main_comp_tv3_2);
        compList4 = (TextView)constraintLayout.findViewById(R.id.main_comp_tv4_2);
        compList5 = (TextView)constraintLayout.findViewById(R.id.main_comp_tv5_2);
        compList6 = (TextView)constraintLayout.findViewById(R.id.main_comp_tv6_2);
        compImg1 = (ImageView)constraintLayout.findViewById(R.id.main_comp_iv1);
        compImg2 = (ImageView)constraintLayout.findViewById(R.id.main_comp_iv2);
        compImg3 = (ImageView)constraintLayout.findViewById(R.id.main_comp_iv3);
        compImg4 = (ImageView)constraintLayout.findViewById(R.id.main_comp_iv4);
        compImg5 = (ImageView)constraintLayout.findViewById(R.id.main_comp_iv5);
        compImg6 = (ImageView)constraintLayout.findViewById(R.id.main_comp_iv6);


        buyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(constraintLayout.getContext(), "준비 중인 서비스입니다", Toast.LENGTH_SHORT).show();
            }
        });

        return constraintLayout;
    }

}
