package com.example.quanlychitieu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;

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
import java.util.*;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Outcome> listOutcome = new ArrayList<>();
    List<Income> listIncome = new ArrayList<>();

    TextView txtIncome1,txtIncome2,txtIncome3, txtOutcome1,txtOutcome2,txtOutcome3;
    TextView numberIncome1,numberIncome2,numberIncome3, numberOutcome1,numberOutcome2,numberOutcome3;
    TextView numberWallet, txtUsername;
    ImageView iconIncome1, iconIncome2, iconIncome3, iconOutcome1, iconOutcome2, iconOutcome3;
    Button btnLogout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    private FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        txtUsername = view.findViewById(R.id.txtUsername);
        numberWallet = view.findViewById(R.id.numberWallet);
        txtIncome1 = view.findViewById(R.id.txtIncome1);
        txtIncome2 = view.findViewById(R.id.txtIncome2);
        txtIncome3 = view.findViewById(R.id.txtIncome3);
        txtOutcome1 = view.findViewById(R.id.txtOutcome1);
        txtOutcome2 = view.findViewById(R.id.txtOutcome2);
        txtOutcome3 = view.findViewById(R.id.txtOutcome3);
        numberIncome1 = view.findViewById(R.id.numberIncome1);
        numberIncome2 = view.findViewById(R.id.numberIncome2);
        numberIncome3 = view.findViewById(R.id.numberIncome3);
        numberOutcome1 = view.findViewById(R.id.numberOutcome1);
        numberOutcome2 = view.findViewById(R.id.numberOutcome2);
        numberOutcome3 = view.findViewById(R.id.numberOutcome3);

        iconIncome1= view.findViewById(R.id.iconIncome1);
        iconIncome2= view.findViewById(R.id.iconIncome2);
        iconIncome3= view.findViewById(R.id.iconIncome3);
        iconOutcome1= view.findViewById(R.id.iconOutcome1);
        iconOutcome2= view.findViewById(R.id.iconOutcome2);
        iconOutcome3= view.findViewById(R.id.iconOutcome3);
        btnLogout = view.findViewById(R.id.btnLogout);
        init();
        return view;
    }

    private void init()
    {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent();
                intent.setClass( getContext(), SignInActivity.class);
                startActivity(intent);
            }
        });
        listIncome.clear();
        listOutcome.clear();
        db.collection("income").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            // convert document to POJO
                            Income income = document.toObject(Income.class);
                            if(income.getEmail().equals(currentUser.getEmail()))
                            {
                                listIncome.add(income);
//                                totalIncome += listIncome.get(i).getAmount();
                            }
                        }
                    }
                    Log.d("READ_DATA", list.toString());
                    loadTopIncome();
                } else {
                    Log.d("READ_DATA", "Error getting documents: ", task.getException());
                }
            }
        });
        db.collection("outcome").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Outcome outcome = document.toObject(Outcome.class);
                            if(outcome.getEmail().equals(currentUser.getEmail()))
                            {
                                listOutcome.add(outcome);
//                                totalIncome += listIncome.get(i).getAmount();
                            }
                        }
                    }
                    Log.d("READ_DATA", list.toString());
                    loadTopOutcome();
                } else {
                    Log.d("READ_DATA", "Error getting documents: ", task.getException());
                }
            }
        });
        //LAY TEN CUA USER TUONG UNG VOI EMAIL
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            User user = document.toObject(User.class);

                            if(currentUser.getEmail().equals(user.getEmail()))
                            {
                                txtUsername.setText(user.getName());
//                                txtUsername.setText(listOutcome.size() + " " + listIncome.size());
                                float totalIncome = 0f;
                                float totalOutcome = 0f;
                                for(int i = 0; i < listIncome.size(); i++)
                                {
//                                    if(listIncome.get(i).getEmail().equals(currentUser.getEmail()))
//                                    {
                                        totalIncome += listIncome.get(i).getAmount();
//                                    }
                                }
                                for(int i = 0; i < listOutcome.size(); i++)
                                {
//                                    if(listOutcome.get(i).getEmail().equals(currentUser.getEmail()))
//                                    {
                                        totalOutcome += listOutcome.get(i).getAmount();
//                                    }
                                }
                                numberWallet.setText((int)(totalIncome - totalOutcome)+"$");
//                                numberWallet.setText((totalIncome + " - " + totalOutcome));
                            }
                        }
                    }
                    Log.d("READ_DATA", list.toString());
                } else {
                    Log.d("READ_DATA", "Error getting documents: ", task.getException());
                }
            }
        });

    }
    private void loadTopIncome()
    {
        Hashtable<Integer, Float> incomes = new Hashtable<Integer, Float>();
        for(int i =0; i < listIncome.size(); i++)
        {
            if(!incomes.containsKey(listIncome.get(i).getType()))
            {
                incomes.put(listIncome.get(i).getType(), listIncome.get(i).getAmount());
            }
            else
            {
                incomes.put(listIncome.get(i).getType(), incomes.get(listIncome.get(i).getType()) + listIncome.get(i).getAmount());
            }
        }
        Pair<Integer, Float> topIncome1 = new Pair<>(0, 0f);
        Pair<Integer, Float> topIncome2 = new Pair<>(0, 0f);
        Pair<Integer, Float> topIncome3= new Pair<>(0, 0f);
        for(int key : incomes.keySet())
        {
            float val = incomes.get(key);
            if(val >= topIncome1.second)
            {
                topIncome3 = topIncome2;
                topIncome2 = topIncome1;
                topIncome1 = new Pair<>(key, val);
            }
        }
        numberIncome1.setText(Math.round(topIncome1.second) + "$");
        numberIncome2.setText(Math.round(topIncome2.second) + "$");
        numberIncome3.setText(Math.round(topIncome3.second) + "$");

        switch (topIncome1.first)
        {
            case 0:
                txtIncome1.setText("Thu nhập");
                iconIncome1.setImageResource(R.drawable.icon_wallet);
                iconIncome1.setBackgroundColor(Color.parseColor("#1C8CC9"));
                break;
            case 1:
                txtIncome1.setText("Lương");
                iconIncome1.setImageResource(R.drawable.icon_money);
                iconIncome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 2:
                txtIncome1.setText("Làm thêm");
                iconIncome1.setImageResource(R.drawable.icon_database);
                iconIncome1.setBackgroundColor(Color.parseColor("#1C8CC9"));


                break;
            case 3:
                txtIncome1.setText("Quà");
                iconIncome1.setImageResource(R.drawable.icon_gift);
                iconIncome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 4:
                txtIncome1.setText("Đầu tư");
                iconIncome1.setImageResource(R.drawable.icon_coin);
                iconIncome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 5:
                txtIncome1.setText("Khác");
                iconIncome1.setImageResource(R.drawable.icon_diff);
                iconIncome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
        }
        switch (topIncome2.first)
        {
            case 0:
                txtIncome2.setText("Thu nhập");
                iconIncome2.setImageResource(R.drawable.icon_wallet);
                iconIncome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 1:
                txtIncome2.setText("Lương");
                iconIncome2.setImageResource(R.drawable.icon_money);
                iconIncome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 2:
                txtIncome2.setText("Làm thêm");
                iconIncome2.setImageResource(R.drawable.icon_database);
                iconIncome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 3:
                txtIncome2.setText("Quà");
                iconIncome2.setImageResource(R.drawable.icon_gift);
                iconIncome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 4:
                txtIncome2.setText("Đầu tư");
                iconIncome2.setImageResource(R.drawable.icon_coin);
                iconIncome2.setBackgroundColor(Color.parseColor("#1C8CC9"));


                break;
            case 5:
                txtIncome2.setText("Khác");
                iconIncome2.setImageResource(R.drawable.icon_diff);
                iconIncome2.setBackgroundColor(Color.parseColor("#1C8CC9"));


                break;
        }
        switch (topIncome3.first)
        {
            case 0:
                txtIncome3.setText("Thu nhập");
                iconIncome3.setImageResource(R.drawable.icon_wallet);
                iconIncome3.setBackgroundColor(Color.parseColor("#1C8CC9"));


                break;
            case 1:
                txtIncome3.setText("Lương");
                iconIncome3.setImageResource(R.drawable.icon_money);
                iconIncome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 2:
                txtIncome3.setText("Làm thêm");
                iconIncome3.setImageResource(R.drawable.icon_database);
                iconIncome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 3:
                txtIncome3.setText("Quà");
                iconIncome3.setImageResource(R.drawable.icon_gift);
                iconIncome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 4:
                txtIncome3.setText("Đầu tư");
                iconIncome3.setImageResource(R.drawable.icon_coin);
                iconIncome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 5:
                txtIncome3.setText("Khác");
                iconIncome3.setImageResource(R.drawable.icon_diff);
                iconIncome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
        }

    }
    private void loadTopOutcome()
    {
        Hashtable<Integer, Float> outcomes = new Hashtable<Integer, Float>();
        for(int i =0; i < listOutcome.size(); i++)
        {
            if(!outcomes.containsKey(listOutcome.get(i).getType()))
            {
                outcomes.put(listOutcome.get(i).getType(), listOutcome.get(i).getAmount());
            }
            else
            {
                outcomes.put(listOutcome.get(i).getType(), outcomes.get(listOutcome.get(i).getType()) + listIncome.get(i).getAmount());
            }
        }
        Pair<Integer, Float> topOutcome1 = new Pair<>(0, 0f);
        Pair<Integer, Float> topOutcome2 = new Pair<>(0, 0f);
        Pair<Integer, Float> topOutcome3= new Pair<>(0, 0f);
        for(int key : outcomes.keySet())
        {
            float val = outcomes.get(key);
            if(val >= topOutcome1.second)
            {
                topOutcome3 = topOutcome2;
                topOutcome2 = topOutcome1;
                topOutcome1 = new Pair<>(key, val);
            }
        }

        numberOutcome1.setText(Math.round(topOutcome1.second) + "$");
        numberOutcome2.setText(Math.round(topOutcome2.second) + "$");
        numberOutcome3.setText(Math.round(topOutcome3.second) + "$");

        switch (topOutcome1.first)
        {
            case 0:
                txtOutcome1.setText("Thức ăn");
                iconOutcome1.setImageResource(R.drawable.icon_food);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 1:
                txtOutcome1.setText("Grab");
                iconOutcome1.setImageResource(R.drawable.icon_car);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 2:
                txtOutcome1.setText("Mua sắm");
                iconOutcome1.setImageResource(R.drawable.icon_shopbag);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 3:
                txtOutcome1.setText("Quà");
                iconOutcome1.setImageResource(R.drawable.icon_gift);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 4:
                txtOutcome1.setText("Giáo dục");
                iconOutcome1.setImageResource(R.drawable.icon_book);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 5:
                txtOutcome1.setText("Y tế");
                iconOutcome1.setImageResource(R.drawable.icon_heart);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 6:
                txtOutcome1.setText("Hóa đơn");
                iconOutcome1.setImageResource(R.drawable.icon_bill);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 7:
                txtOutcome1.setText("Khác");
                iconOutcome1.setImageResource(R.drawable.icon_diff);
                iconOutcome1.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;

        }
        switch (topOutcome2.first)
        {
            case 0:
                txtOutcome2.setText("Thức ăn");
                iconOutcome2.setImageResource(R.drawable.icon_food);
                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 1:
                txtOutcome2.setText("Grab");
                iconOutcome2.setImageResource(R.drawable.icon_car);
                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 2:
                txtOutcome2.setText("Mua sắm");
                iconOutcome2.setImageResource(R.drawable.icon_shopbag);

                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 3:
                txtOutcome2.setText("Quà");
                iconOutcome2.setImageResource(R.drawable.icon_gift);

                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 4:
                txtOutcome2.setText("Giáo dục");
                iconOutcome2.setImageResource(R.drawable.icon_book);

                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 5:
                txtOutcome2.setText("Y tế");
                iconOutcome2.setImageResource(R.drawable.icon_heart);

                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 6:
                txtOutcome2.setText("Hóa đơn");
                iconOutcome2.setImageResource(R.drawable.icon_bill);

                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 7:
                txtOutcome2.setText("Khác");
                iconOutcome2.setImageResource(R.drawable.icon_diff);
                iconOutcome2.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
        }
        switch (topOutcome3.first)
        {
            case 0:
                txtOutcome3.setText("Thức ăn");
                iconOutcome3.setImageResource(R.drawable.icon_food);

                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 1:
                txtOutcome3.setText("Grab");
                iconOutcome3.setImageResource(R.drawable.icon_car);
                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 2:
                txtOutcome3.setText("Mua sắm");
                iconOutcome3.setImageResource(R.drawable.icon_shopbag);
                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 3:
                txtOutcome3.setText("Quà");
                iconOutcome3.setImageResource(R.drawable.icon_gift);

                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 4:
                txtOutcome3.setText("Giáo dục");
                iconOutcome3.setImageResource(R.drawable.icon_book);
                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 5:
                txtOutcome3.setText("Y tế");
                iconOutcome3.setImageResource(R.drawable.icon_heart);
                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 6:
                txtOutcome3.setText("Hóa đơn");
                iconOutcome3.setImageResource(R.drawable.icon_bill);
                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
            case 7:
                txtOutcome3.setText("Khác");
                iconOutcome3.setImageResource(R.drawable.icon_diff);
                iconOutcome3.setBackgroundColor(Color.parseColor("#1C8CC9"));

                break;
        }

    }
}