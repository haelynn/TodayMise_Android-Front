package minibird.todaymise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import minibird.todaymise.R;
import minibird.todaymise.model.Main2Result;
import minibird.todaymise.network.ApplicationController;
import minibird.todaymise.model.Main2Result;
import minibird.todaymise.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main2Fragment extends Fragment{

    NetworkService service;
    private String userLocation, longtitude, latitude;
    private Button buyBtn;
    private TextView compareTxt, compList1, compList2, compList3, compList4, compList5, compList6, update;
    private ImageView compImg1, compImg2, compImg3, compImg4, compImg5, compImg6;

    public static Main2Fragment newInstance(String loc, String longtitude, String latitude){
        Main2Fragment frag = new Main2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("location", loc);
        bundle.putString("longtitude", longtitude);
        bundle.putString("latitude", latitude);
        Log.e("text", "위치 : "  + loc + " " + longtitude + " " + latitude);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocation = getArguments().getString("location");
        longtitude = getArguments().getString("longtitude");
        latitude = getArguments().getString("latitude");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ConstraintLayout constraintLayout = (ConstraintLayout)inflater.inflate(R.layout.main2, container, false);

        service = ApplicationController.getInstance().getNetworkService();
        getResult(longtitude, latitude);

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
        update = (TextView)constraintLayout.findViewById(R.id.main_update_tv);


        buyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), minibird.todaymise.activity.MaskActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        return constraintLayout;
    }

    // main2
    public void getResult(String XVal, String YVal){
        Call<Main2Result> requestMain2Result = service.getMain2Result(XVal, YVal);

        requestMain2Result.enqueue(new Callback<Main2Result>() {
            @Override
            public void onResponse(Call<Main2Result> call, Response<Main2Result> response) {
                //Toast.makeText(getActivity(), response.body().getClass().getFields().toString(), Toast.LENGTH_LONG).show();

                if(response.isSuccessful()){
                    compareTxt.setText(response.body().tabbaco + "개피와 같아요");
                    update.setText("업데이트 시간 " + response.body().dataTime);
                }
            }

            @Override
            public void onFailure(Call<Main2Result> call, Throwable t) {
                Log.e("test", "callback error");
            }
        });
    }

}
