package com.example.quanlychitieu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import java.util.*;
import android.util.Log;
import android.widget.TextView;

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
        txtUsername = getView().findViewById(R.id.txtUsername);
        numberWallet = getView().findViewById(R.id.numberWallet);
        txtIncome1 = getView().findViewById(R.id.txtIncome1);
        txtIncome2 = getView().findViewById(R.id.txtIncome2);
        txtIncome3 = getView().findViewById(R.id.txtIncome3);
        txtOutcome1 = getView().findViewById(R.id.txtOutcome1);
        txtOutcome2 = getView().findViewById(R.id.txtOutcome2);
        txtOutcome3 = getView().findViewById(R.id.txtOutcome3);
        numberIncome1 = getView().findViewById(R.id.numberIncome1);
        numberIncome2 = getView().findViewById(R.id.numberIncome2);
        numberIncome3 = getView().findViewById(R.id.numberIncome3);
        numberOutcome1 = getView().findViewById(R.id.numberOutcome1);
        numberOutcome2 = getView().findViewById(R.id.numberOutcome2);
        numberOutcome3 = getView().findViewById(R.id.numberOutcome3);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void init()
    {
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            // convert document to POJO
                            User user = document.toObject(User.class);
                            if(user.getEmail() == "")
                            {
                                txtUsername.setText(user.getUsername());
                                txtUsername.setText(user.getUsername());

                            }
                        }
                    }
                    Log.d("READ_DATA", list.toString());
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
        db.collection("income").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            // convert document to POJO
                            Income income = document.toObject(Income.class);
                            listIncome.add(income);
                        }
                    }
                    Log.d("READ_DATA", list.toString());
                } else {
                    Log.d("READ_DATA", "Error getting documents: ", task.getException());
                }
            }
        });
        Float[] typeIncome = new Float[9];
        Float[] typeOutcome = new Float[9];
        for(int i =0; i < typeIncome.length;i++)
        {
            typeIncome[i]=0f;
            typeOutcome[i]=0f;
        }
        for(int i = 0; i < listIncome.size(); i++)
        {
            Income income = listIncome.get(i);
            typeIncome[income.getType()] += income.getAmount();
        }
        for(int i = 0; i < listOutcome.size(); i++)
        {
            //TODO: PARSE STRING TO INCOME
            Outcome outcome = listOutcome.get(i);
            typeOutcome[outcome.getType()] += outcome.getAmount();
        }
        int[] topIncome = new int[3];
        int[] topOutcome = new int[3];
//        Arrays.sort(typeIncome, Collections.reverseOrder());
//        Arrays.sort(typeOutcome, Collections.reverseOrder());
        //TODO: -> ADD TO TYPES ARRAY SUM OF ALL INCOME -> CHOOSE TOP 3
        for(int i = 0; i < topIncome.length; i++)
        {
            TextView tmpIn = txtIncome1;
            TextView numIn = numberIncome1;
            if(i == 1)
            {
                tmpIn = txtIncome2;
                numIn = numberIncome2;
            }
            if(i == 2)
            {
                tmpIn = txtIncome3;
                numIn = numberIncome3;
            }
            numIn.setText(typeIncome[topIncome[i]].toString());

            switch (topIncome[i])
            {
                case 0:
                    tmpIn.setText("Thu nhập");
                    break;
                case 1:
                    tmpIn.setText("Lương");
                    break;
                case 2:
                    tmpIn.setText("Làm thêm");
                    break;
                case 3:
                    tmpIn.setText("Quà");
                    break;
                case 4:
                    tmpIn.setText("Đầu");
                    break;
                case 5:
                    tmpIn.setText("Khác");
                    break;
            }
        }
        for(int i = 0; i < topOutcome.length; i++)
        {
            TextView tmpOut = txtIncome1;
            TextView numOut = numberOutcome1;
            if(i == 1)
            {
                tmpOut = txtOutcome2;
                numOut = numberOutcome2;
            }
            if(i == 2)
            {
                tmpOut = txtOutcome3;
                numOut = numberOutcome3;
            }
            numOut.setText(typeIncome[topOutcome[i]].toString());

            switch (topIncome[i])
            {
                case 0:
                    tmpOut.setText("Thức ăn");
                    break;
                case 1:
                    tmpOut.setText("Grab");
                    break;
                case 2:
                    tmpOut.setText("Mua sắm");
                    break;
                case 3:
                    tmpOut.setText("Quà");
                    break;
                case 4:
                    tmpOut.setText("Giáo dục");
                    break;
                case 5:
                    tmpOut.setText("Y tế");
                    break;
                case 6:
                    tmpOut.setText("Hóa đơn");
                    break;
                case 7:
                    tmpOut.setText("Khác");
                    break;
            }
        }
    }
}