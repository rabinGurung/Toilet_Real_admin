package com.example.toilet_real_admin;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.toilet_real_admin.Connection.API_CALL;
import com.example.toilet_real_admin.Interface.Visitors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Dashboard extends AppCompatActivity {
    private int jan_count = 0, feb_count = 0, dec_count = 0;
    private Retrofit retrofit;
    private Visitors visitors;
    List<DataEntry> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        retrofit = API_CALL.getAPI_Instance().getRetrofit();
        visitors = retrofit.create(Visitors.class);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            getData(anyChartView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Count of Public toilet used through app by month");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Month");
        cartesian.yAxis(0).title("Count");

        anyChartView.setChart(cartesian);

    }

    private void getData(AnyChartView anyChartView ) throws IOException {
        Call<ResponseBody> callDec = visitors.getDec();
        Response<ResponseBody> response2 = callDec.execute();
        String str = response2.body().string();
        String str1 = str.substring(1,str.length()-1);
        int dec_count1 = Integer.parseInt(str1);
        data.add(new ValueDataEntry("Dec", dec_count1));

        Call<ResponseBody> callJan = visitors.getJan();
        Response<ResponseBody> response = callJan.execute();
        String jan = response.body().string();
        String jan1 = jan.substring(1,jan.length()-1);
        int jan_count1 = Integer.parseInt(jan1);
        data.add(new ValueDataEntry("Jan", jan_count1));

        Call<ResponseBody> callFeb = visitors.getJan();
        Response<ResponseBody> response1 = callFeb.execute();
        String feb = response1.body().string();
        String feb1 = feb.substring(1,feb.length()-1);
        int feb_count1 = Integer.parseInt(str1);
        data.add(new ValueDataEntry("Feb", feb_count1));

    }
}
