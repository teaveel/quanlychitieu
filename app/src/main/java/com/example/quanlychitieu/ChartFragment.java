package com.example.quanlychitieu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<Outcome> listOutcome = new ArrayList<>();
    List<Income> listIncome = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
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
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        Pie incomePie = AnyChart.pie();
        Pie outcomePie = AnyChart.pie();

        List<DataEntry> incomeData = new ArrayList<>();
        List<DataEntry> outcomeData = new ArrayList<>();

        db.collection("outcome").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            // convert document to POJO
                            Outcome outcome = document.toObject(Outcome.class);
                            listOutcome.add(outcome);
                        }
                    }
                    Log.d("READ_DATA", list.toString());
                } else {
                    Log.d("READ_DATA", "Error getting documents: ", task.getException());
                }
            }
        });
//        db.collection("income").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<String> list = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if (document.exists()) {
//                            // convert document to POJO
//                            Income income = document.toObject(Income.class);
//                            listIncome.add(income);
//                        }
//                    }
//                    Log.d("READ_DATA", list.toString());
//                } else {
//                    Log.d("READ_DATA", "Error getting documents: ", task.getException());
//                }
//            }
//        });
//        Float[] typeIncome = new Float[8];
        Float[] typeOutcome = new Float[9];
//        for(int i =0; i < typeIncome.length;i++)
//        {
//            typeIncome[i]=0f;
//        }
        for(int i =0; i < typeOutcome.length;i++)
        {
            typeOutcome[i]=0f;
        }
//        for(int i = 0; i < listIncome.size(); i++)
//        {
//            Income income = listIncome.get(i);
//            typeIncome[income.getType()] += income.getAmount();
//        }
        for(int i = 0; i < listOutcome.size(); i++)
        {
            //TODO: PARSE STRING TO INCOME
            Outcome outcome = listOutcome.get(i);
            typeOutcome[outcome.getType()] += outcome.getAmount();
        }

//        for(int i = 0; i < typeIncome.length; i++)
//        {
//            switch (i)
//            {
//                case 0:
//                    incomeData.add(new ValueDataEntry("Thức ăn", typeIncome[i]));
//                    break;
//                case 1:
//                    incomeData.add(new ValueDataEntry("Lương", typeIncome[i]));
//                    break;
//                case 2:
//                    incomeData.add(new ValueDataEntry("Làm thêm", typeIncome[i]));
//                    break;
//                case 3:
//                    incomeData.add(new ValueDataEntry("Quà", typeIncome[i]));
//                    break;
//                case 4:
//                    incomeData.add(new ValueDataEntry("Đầu tư", typeIncome[i]));
//                    break;
//                case 5:
//                    incomeData.add(new ValueDataEntry("Khác", typeIncome[i]));
//                    break;
//            }
//        }

        for(int i = 0; i < typeOutcome.length; i++)
        {
            switch (i)
            {
                case 0:
                    outcomeData.add(new ValueDataEntry("Thức ăn", typeOutcome[i]));
                    break;
                case 1:
                    outcomeData.add(new ValueDataEntry("Grab", typeOutcome[i]));
                    break;
                case 2:
                    outcomeData.add(new ValueDataEntry("Mua sắm", typeOutcome[i]));
                    break;
                case 3:
                    outcomeData.add(new ValueDataEntry("Quà", typeOutcome[i]));
                    break;
                case 4:
                    outcomeData.add(new ValueDataEntry("Giáo dục", typeOutcome[i]));
                    break;
                case 5:
                    outcomeData.add(new ValueDataEntry("Y tế", typeOutcome[i]));
                    break;
                case 6:
                    outcomeData.add(new ValueDataEntry("Hóa đơn", typeOutcome[i]));
                    break;
                case 7:
                    outcomeData.add(new ValueDataEntry("Khác", typeOutcome[i]));
                    break;
            }
        }

        incomePie.data(incomeData);
        outcomePie.data(outcomeData);

//        AnyChartView chartIncome = (AnyChartView) view.findViewById(R.id.chartIncome);
//        chartIncome.setChart(incomePie);

        AnyChartView chartOutcome = (AnyChartView) view.findViewById(R.id.chartOutcome);
        chartOutcome.setChart(outcomePie);

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
//                Toast.makeText(PieChartActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Apples", 6371664));
        data.add(new ValueDataEntry("Pears", 789622));
        data.add(new ValueDataEntry("Bananas", 7216301));
        data.add(new ValueDataEntry("Grapes", 1486621));
        data.add(new ValueDataEntry("Oranges", 1200000));

        pie.data(data);

        pie.title("Fruits imported in 2015 (in kg)");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
        return view;
    }
}