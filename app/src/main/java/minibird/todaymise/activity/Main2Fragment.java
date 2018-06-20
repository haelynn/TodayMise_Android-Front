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

        if(userLocation == null){
            compareTxt.setText("알 수 없어요ㅠ_ㅠ");
            compImg1.setImageResource(R.drawable.none_small);
            compImg2.setImageResource(R.drawable.none_small);
            compImg3.setImageResource(R.drawable.none_small);
            compImg4.setImageResource(R.drawable.none_small);
            compImg5.setImageResource(R.drawable.none_small);
            compImg6.setImageResource(R.drawable.none_small);
            compList1.setText("알 수 없음");
            compList2.setText("알 수 없음");
            compList3.setText("알 수 없음");
            compList4.setText("알 수 없음");
            compList5.setText("알 수 없음");
            compList6.setText("알 수 없음");
            update.setText("업데이트 시간 알 수 없음");
        }

        service = ApplicationController.getInstance().getNetworkService();
        getResult(longtitude, latitude);

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

                Log.e("main2", "2리스폰스 결과 " + response.isSuccessful());
                if(response.isSuccessful()){
                    compareTxt.setText(response.body().tabbaco.toString() + "개피와 같아요");
                    update.setText("업데이트 시간 " + response.body().dataTime);

                    if(response.body().pm10Value == "" || response.body().pm10Value == "-")
                        compList1.setText("0.001㎍/m");
                    else compList1.setText(response.body().pm10Value + "㎍/m");
                    switch(response.body().pm10Grade){
                        case "1":
                            compImg1.setImageResource(R.drawable.good_small);
                            break;
                        case "2":
                            compImg1.setImageResource(R.drawable.soso_small);
                            break;
                        case "3":
                            compImg1.setImageResource(R.drawable.bad_small);
                            break;
                        case "4":
                            compImg1.setImageResource(R.drawable.verybad_small);
                            break;
                    }


                    if(response.body().pm25Value == "" || response.body().pm25Value == "-")
                        compList2.setText("0.001㎍/m");
                    else compList2.setText(response.body().pm25Value + "㎍/m");
                    switch (response.body().pm25Grade){
                        case "1":
                            compImg2.setImageResource(R.drawable.good_small);
                            break;
                        case "2":
                            compImg2.setImageResource(R.drawable.soso_small);
                            break;
                        case "3":
                            compImg2.setImageResource(R.drawable.bad_small);
                            break;
                        case "4":
                            compImg2.setImageResource(R.drawable.verybad_small);
                            break;
                    }

                    if(response.body().o3Value == "" || response.body().o3Value == "-")
                        compList3.setText("0.001ppm");
                    else compList3.setText(response.body().o3Value.toString() + "ppm");
                    switch (response.body().o3Grade){
                        case "1":
                            compImg3.setImageResource(R.drawable.good_small);
                            break;
                        case "2":
                            compImg3.setImageResource(R.drawable.soso_small);
                            break;
                        case "3":
                            compImg3.setImageResource(R.drawable.bad_small);
                            break;
                        case "4":
                            compImg3.setImageResource(R.drawable.verybad_small);
                            break;
                    }


                    if(response.body().no2Value == "" || response.body().no2Value == "-")
                        compList4.setText("0.001ppm");
                    else compList4.setText(response.body().no2Value + "ppm");
                    switch (response.body().no2Grade){
                        case "1":
                            compImg4.setImageResource(R.drawable.good_small);
                            break;
                        case "2":
                            compImg4.setImageResource(R.drawable.soso_small);
                            break;
                        case "3":
                            compImg4.setImageResource(R.drawable.bad_small);
                            break;
                        case "4":
                            compImg4.setImageResource(R.drawable.verybad_small);
                            break;
                    }


                    if(response.body().coValue == "" || response.body().coValue == "-")
                        compList5.setText("0.001ppm");
                    else compList5.setText(response.body().coValue + "ppm");
                    switch (response.body().coGrade){
                        case "1":
                            compImg5.setImageResource(R.drawable.good_small);
                            break;
                        case "2":
                            compImg5.setImageResource(R.drawable.soso_small);
                            break;
                        case "3":
                            compImg5.setImageResource(R.drawable.bad_small);
                            break;
                        case "4":
                            compImg5.setImageResource(R.drawable.verybad_small);
                            break;
                    }


                    if(response.body().so2Value == "" || response.body().so2Value == "-")
                        compList6.setText("0.001ppm");
                    else compList6.setText(response.body().so2Value + "ppm");
                    switch (response.body().so2Grade){
                        case "1":
                            compImg6.setImageResource(R.drawable.good_small);
                            break;
                        case "2":
                            compImg6.setImageResource(R.drawable.soso_small);
                            break;
                        case "3":
                            compImg6.setImageResource(R.drawable.bad_small);
                            break;
                        case "4":
                            compImg6.setImageResource(R.drawable.verybad_small);
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<Main2Result> call, Throwable t) {
                Log.e("test", "callback error");
            }
        });
    }

}
