package com.example.quanlychitieu;

import android.graphics.Color;
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
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    List<Outcome> listOutcome = new ArrayList<>();
    List<Income> listIncome = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button tabIncome, tabOutcome;
    Button btnPrevMonth, btnNextMonth;
    TextView txtDate;
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
        init(view);

        return view;
    }
    private void findViews(View view)
    {
        tabIncome = view.findViewById(R.id.tabIncome);
        tabOutcome = view.findViewById(R.id.tabOutcome);
        txtDate = view.findViewById(R.id.txtDate);
        btnPrevMonth = view.findViewById(R.id.btnPrevMonth);
        btnNextMonth = view.findViewById(R.id.btnNextMonth);
    }
    private void initDateUI()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
        String strDate = formatter.format(date);
        txtDate.setText(strDate);
//                            LocalDate localDate = test.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                            int month = localDate.getMonthValue();
    }
    private void addEvents(View view)
    {
        /**
         * ADD EVENTS
         */
//        setChildrenOnClickListener();
        tabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabIncome.setBackgroundColor(Color.parseColor("#1C8CC9"));
                tabOutcome.setBackgroundColor(Color.parseColor("#B0BBC1"));
                changeTab(new ChartIncome());
            }
        });
        tabOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabOutcome.setBackgroundColor(Color.parseColor("#1C8CC9"));
                tabIncome.setBackgroundColor(Color.parseColor("#B0BBC1"));
                changeTab(new ChartOutcome());
            }
        });
    }
    private void init(View view)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        findViews(view);
        addEvents(view);
        initDateUI();
        changeTab(new ChartIncome());
    }

    private void changeTab(Fragment fragment)
    {
        FragmentManager fragmentManager =  getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.chartContainer, fragment);
        fragmentTransaction.commit();
    }
}