package com.example.quanlychitieu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.time.*;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

import com.google.firebase.auth.*;
import android.widget.*;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WalletFragment() {

    }

    /**
     * Use this factory method to create a   new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText inputAmount, inputNote, inputDate;
    EditText inputAmountIncome, inputNoteIncome, inputDateIncome;
    Button btnConfirm, btnConfirmIncome;
    public Button tabIncome, tabOutcome;
    LinearLayout layoutIncome, layoutOutcome;
    GridLayout gridItemOutcome, gridItemIncome;
    int inputType, inputTypeIncome;
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
    FirebaseFirestore db;
    private void changeTab(Fragment fragment)
    {
        FragmentManager fragmentManager =  getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        findViews(view);
        addEvents(view);
        changeTab(new WalletOutcome());
    }
    private void findViews(View view)
    {
        /**
         * FIND VIEW BY ID
         */

//        inputDate = view.findViewById(R.id.inputDate);
//        inputAmount = view.findViewById(R.id.inputAmount);
//        inputNote = view.findViewById(R.id.inputNote);
//        btnConfirm = view.findViewById(R.id.btnConfirm);
//        btnConfirmIncome = view.findViewById(R.id.btnConfirmIncome);
//
//        inputDateIncome = view.findViewById(R.id.inputDateIncome);
//        inputAmountIncome = view.findViewById(R.id.inputAmountIncome);
//        inputNoteIncome = view.findViewById(R.id.inputNoteIncome);
//        btnConfirmIncome = view.findViewById(R.id.btnConfirmIncome);

        tabIncome = view.findViewById(R.id.tabIncome);
        tabOutcome = view.findViewById(R.id.tabOutcome);

//        layoutIncome = view.findViewById(R.id.layoutIncome);
//        layoutOutcome = view.findViewById(R.id.layoutOutcome);
//
//        gridItemOutcome = view.findViewById(R.id.gridItemOutcome);
//        gridItemIncome = view.findViewById(R.id.gridItemIncome);
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
                Toast.makeText(getContext(), "hahahah.", Toast.LENGTH_SHORT).show();
                tabIncome.setBackgroundColor(Color.parseColor("#1C8CC9"));
                tabOutcome.setBackgroundColor(Color.parseColor("#B0BBC1"));
                changeTab(new WalletIncome());
//                layoutOutcome.setVisibility(LinearLayout.INVISIBLE);
//                layoutIncome.setVisibility(LinearLayout.VISIBLE);
//                tabIncome.setBackgroundColor(Color.);
            }
        });
        tabOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabOutcome.setBackgroundColor(Color.parseColor("#1C8CC9"));
                tabIncome.setBackgroundColor(Color.parseColor("#B0BBC1"));
                changeTab(new WalletOutcome());
//                layoutIncome.setVisibility(LinearLayout.INVISIBLE);
//                layoutOutcome.setVisibility(LinearLayout.VISIBLE);
            }
        });
//        btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    int amount = Integer.parseInt(inputAmount.getText().toString());
//                    String note = inputNote.getText().toString();
//                    String date = inputDate.getText().toString();
//                    //TODO: SAVE DATA TO FIREBASE
//                    Map<String, Object> newOutcome = new HashMap<>();
//                    newOutcome.put("amount", amount);
//                    newOutcome.put("type", inputType);
//                    newOutcome.put("date", date);
//                    newOutcome.put("note", note);
//                    newOutcome.put("email", currentUser.getEmail());
//                    // Add a new document with a generated ID
//                    Toast.makeText(view.getContext(), "Add outcome successfully", Toast.LENGTH_SHORT).show();
//                    db.collection("outcome")
//                            .add(newOutcome)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Log.d("ADD_OUTCOME", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                    Toast.makeText(view.getContext(), "Add outcome successfully", Toast.LENGTH_SHORT).show();
//                                    inputAmount.setText("");
//                                    inputDate.setText("");
//                                    inputNote.setText("");
//                                    inputType = 0;
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w("ADD_OUTCOME", "Error adding document", e);
//                                }
//                            });
//                }
//                catch(Exception e)
//                {
//
//                }
//            }
//        });
//        btnConfirmIncome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    int amount = Integer.parseInt(inputAmountIncome.getText().toString());
//                    String note = inputNoteIncome.getText().toString();
//                    String date = inputDateIncome.getText().toString();
//                    //TODO: SAVE DATA TO FIREBASE
//                    Map<String, Object> newIncome = new HashMap<>();
//                    newIncome.put("amount", amount);
//                    newIncome.put("type", inputTypeIncome);
//                    newIncome.put("date", date);
//                    newIncome.put("note", note);
//                    newIncome.put("email", currentUser.getEmail());
//                    // Add a new document with a generated ID
//                    Toast.makeText(view.getContext(), "Add outcome successfully", Toast.LENGTH_SHORT).show();
//                    db.collection("income")
//                            .add(newIncome)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Log.d("ADD_INCOME", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                    Toast.makeText(view.getContext(), "Add outcome successfully", Toast.LENGTH_SHORT).show();
//                                    inputAmount.setText("");
//                                    inputDate.setText("");
//                                    inputNote.setText("");
//                                    inputTypeIncome = 0;
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w("ADD_INCOME", "Error adding document", e);
//                                }
//                            });
//                }
//                catch(Exception e)
//                {
//
//                }
//            }
//        });
    }

//    private void setChildrenOnClickListener() {
//        for (int i = 0; i < gridItemIncome.getChildCount(); i++) {
//            gridItemIncome.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    inputType = 4;
//                }
//            });
//        }
//        for (int i = 0; i < gridItemOutcome.getChildCount(); i++) {
//            gridItemOutcome.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    inputTypeIncome = 4;
//                }
//            });
//        }
//    }
}