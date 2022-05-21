package com.example.quanlychitieu;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Test#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Test() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Test.
     */
    // TODO: Rename and change types and number of parameters
    public static Test newInstance(String param1, String param2) {
        Test fragment = new Test();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    Button tabIncome, tabOutcome;
    LinearLayout layoutIncome, layoutOutcome;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        /**
         * ADD EVENTS
         */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);;

        tabIncome = view.findViewById(R.id.tabIncome);
        tabOutcome = view.findViewById(R.id.tabOutcome);

        layoutIncome = view.findViewById(R.id.layoutIncome);
        layoutOutcome = view.findViewById(R.id.layoutOutcome);
        tabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "hahahah.", Toast.LENGTH_SHORT).show();
                tabIncome.setBackgroundColor(Color.parseColor("#1C8CC9"));

                tabOutcome.setBackgroundColor(Color.parseColor("#B0BBC1"));
                layoutOutcome.setVisibility(LinearLayout.INVISIBLE);
                layoutIncome.setVisibility(LinearLayout.VISIBLE);
//                tabIncome.setBackgroundColor(Color.);
            }
        });
        tabOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabOutcome.setBackgroundColor(Color.parseColor("#1C8CC9"));
                tabIncome.setBackgroundColor(Color.parseColor("#B0BBC1"));
                layoutIncome.setVisibility(LinearLayout.INVISIBLE);
                layoutOutcome.setVisibility(LinearLayout.VISIBLE);
            }
        });
        return view;

    }
}