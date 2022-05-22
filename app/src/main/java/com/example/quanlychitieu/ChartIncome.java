package com.example.quanlychitieu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.text.SimpleDateFormat;
import java.util.*;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import android.widget.Button;
import android.widget.Toast;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartIncome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartIncome extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Income> listIncome = new ArrayList<>();

    public ChartIncome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartIncome.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartIncome newInstance(String param1, String param2) {
        ChartIncome fragment = new ChartIncome();
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
        View view =  inflater.inflate(R.layout.fragment_chart_income, container, false);
        init(view);
        return view;
    }
    AnyChartView chartIncome;
    TextView txtTest;
    private void findViews(View view)
    {
        txtTest = view.findViewById(R.id.txtTest);
        chartIncome = view.findViewById(R.id.chartIncome);
        chartIncome.setProgressBar(view.findViewById(R.id.progress_bar));
    }
    private boolean hasIncome = false;
    private void init(View view)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        findViews(view);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
        String strDate = formatter.format(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentMonth = cal.get(Calendar.MONTH);

        Pie incomePie = AnyChart.pie();
        List<DataEntry> incomeData = new ArrayList<>();

        listIncome.clear();
        db.collection("income").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            hasIncome = true;
                            Income income = document.toObject(Income.class);
//                            String email = document.getString(("email"));
//                            float amount = (float)document.get("amount");
//                            int type = (int)document.get("type");
//                            String note = document.getString("note");
//                            Income ic = new Income(email, type, amount, note);

                            Date dd = document.getDate("date");
                            SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
                            String strDate = formatter.format(income.getDate());
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(income.getDate());
                            int month = cal.get(Calendar.MONTH);
                            if(income.getEmail().equals(currentUser.getEmail()) && month == currentMonth)
                            {
                                listIncome.add(income);
                            }
                        }
                    }
                    Log.d("READ_DATA", list.toString());
                } else {
                    Log.d("READ_DATA", "Error getting documents: ", task.getException());
                }
            }
        });

        if(listIncome.size() == 0)
        {
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
                    .text(listIncome.size() + " " + hasIncome)
                    .padding(0d, 0d, 10d, 0d);

            pie.legend()
                    .position("center-bottom")
                    .itemsLayout(LegendLayout.HORIZONTAL)
                    .align(Align.CENTER);

            chartIncome.setChart(pie);
            return;
        }

        Float[] typeIncome = new Float[6];
        for(int i =0; i < typeIncome.length;i++)
        {
            typeIncome[i]=0f;
        }
        for(int i = 0; i < listIncome.size(); i++)
        {
            Income income = listIncome.get(i);
            typeIncome[income.getType()] += income.getAmount();
        }


        for(int i = 0; i < typeIncome.length; i++)
        {
            switch (i)
            {
                case 0:
                    incomeData.add(new ValueDataEntry("Tiết kiệm", typeIncome[i]));
                    break;
                case 1:
                    incomeData.add(new ValueDataEntry("Lương", typeIncome[i]));
                    break;
                case 2:
                    incomeData.add(new ValueDataEntry("Làm thêm", typeIncome[i]));
                    break;
                case 3:
                    incomeData.add(new ValueDataEntry("Quà", typeIncome[i]));
                    break;
                case 4:
                    incomeData.add(new ValueDataEntry("Đầu tư", typeIncome[i]));
                    break;
                case 5:
                    incomeData.add(new ValueDataEntry("Khác", typeIncome[i]));
                    break;
            }
        }

        incomePie.data(incomeData);

        AnyChartView chartIncome = (AnyChartView) view.findViewById(R.id.chartIncome);
        chartIncome.setChart(incomePie);

        chartIncome.setChart(incomePie);

        incomePie.labels().position("outside");

        incomePie.legend().title().enabled(true);
        incomePie.legend().title()
                .text(listIncome.size() + " " + hasIncome)
                .padding(0d, 0d, 10d, 0d);

        incomePie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);



    }
}