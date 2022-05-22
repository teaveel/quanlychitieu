package com.example.quanlychitieu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartOutcome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartOutcome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChartOutcome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartOutcome.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartOutcome newInstance(String param1, String param2) {
        ChartOutcome fragment = new ChartOutcome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart_outcome, container, false);
        init(view);
        return view;
    }
    AnyChartView chartOutcome;
    private void findViews(View view)
    {
        chartOutcome = view.findViewById(R.id.chartOutcome);
        chartOutcome.setProgressBar(view.findViewById(R.id.progress_bar));
    }
    private void init(View view)
    {
        findViews(view);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Thu nhập", 6371664));
        data.add(new ValueDataEntry("Làm thêm", 789622));
        data.add(new ValueDataEntry("Quà", 7216301));
        data.add(new ValueDataEntry("Đầu tư", 1486621));
        data.add(new ValueDataEntry("Khác", 1200000));
        data.add(new ValueDataEntry("Hư", 1200000));
        pie.data(data);

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Chi tiêu")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        chartOutcome.setChart(pie);
    }
}