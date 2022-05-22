package com.example.quanlychitieu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;

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
import java.text.*;
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


import androidx.appcompat.app.AlertDialog;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletIncome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletIncome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Date inputDate;

    EditText inputAmount, inputNote;
    EditText inputAmountIncome, inputNoteIncome;
    Button btnConfirm, btnConfirmIncome;
    public Button tabIncome, tabOutcome;
    LinearLayout layoutIncome, layoutOutcome;
    GridLayout gridItemOutcome, gridItemIncome;
    int inputType, inputTypeIncome;
    Button gIncomeItem1,gIncomeItem2,gIncomeItem3,gIncomeItem4,gIncomeItem5,gIncomeItem6;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    public WalletIncome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletIncome.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletIncome newInstance(String param1, String param2) {
        WalletIncome fragment = new WalletIncome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
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
        View view = inflater.inflate(R.layout.fragment_wallet_income, container, false);
        init(view);
        return view;
    }
    private void init(View view)
    {

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        initDatePicker(view);

        findViews(view);
        addEvents(view);


    }
    private void findViews(View view)
    {
//        inputDateIncome = view.findViewById(R.id.inputDateIncome);
        inputAmountIncome = view.findViewById(R.id.inputAmountIncome);
        inputNoteIncome = view.findViewById(R.id.inputNoteIncome);
        btnConfirmIncome = view.findViewById(R.id.btnConfirmIncome);
        gridItemIncome = view.findViewById(R.id.gridItemIncome);

        gIncomeItem1= view.findViewById(R.id.gIncomeItem1);
        gIncomeItem2= view.findViewById(R.id.gIncomeItem2);
        gIncomeItem3= view.findViewById(R.id.gIncomeItem3);
        gIncomeItem4= view.findViewById(R.id.gIncomeItem4);
        gIncomeItem5= view.findViewById(R.id.gIncomeItem5);
        gIncomeItem6= view.findViewById(R.id.gIncomeItem6);
        dateButton = view.findViewById(R.id.inputDateIncome);
        dateButton.setText(getTodaysDate());
    }
//    private static String getTimeDate(long timestamp){
//        try{
//            DateFormat dateFormat = getDateTimeInstance();
//            Date netDate = (new Date(timestamp));
//            return dateFormat.format(netDate);
//        } catch(Exception e) {
//            return "date";
//        }
//    }
    private void addEvents(View view)
    {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });

        setChildrenOnClickListener();
        btnConfirmIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int amount = Integer.parseInt(inputAmountIncome.getText().toString());
                    String note = inputNoteIncome.getText().toString();
                    Date date = new Date();
                    //TODO: SAVE DATA TO FIREBASE
                    Map<String, Object> newIncome = new HashMap<>();
                    newIncome.put("amount", amount);
                    newIncome.put("type", inputTypeIncome);
                    newIncome.put("date", date);
                    newIncome.put("note", note);
                    newIncome.put("email", currentUser.getEmail());


                    // Add a new document with a generated ID
                    Toast.makeText(view.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    db.collection("income")
                            .add(newIncome)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("ADD_INCOME", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(view.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    inputAmount.setText("");
                                    inputNote.setText("");
                                    inputTypeIncome = 0;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("ADD_INCOME", "Error adding document", e);
                                }
                            });
                }
                catch(Exception e)
                {

                }
            }
        });
    }
    private void setChildrenOnClickListener() {
//        for (int i = 0; i < gridItemIncome.getChildCount(); i++) {
//            gridItemIncome.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    inputType = 4;
//                    inputAmount.setText("testttt");
//                }
//            });
//        }
        gIncomeItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTypeIncome = 0;
            }
        });
        gIncomeItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTypeIncome = 1;
            }
        });
        gIncomeItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTypeIncome = 2;
            }
        });
        gIncomeItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTypeIncome = 3;
            }
        });
        gIncomeItem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTypeIncome = 4;
            }
        });
        gIncomeItem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTypeIncome = 5;
            }
        });
    }



    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(View _view)
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
//                String date_string = "2";
                //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");
                //Parsing the given String to Date object
                Date d = formatter.parse(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = 3;

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, 0, 0);
        Date test = c.getTime();

        datePickerDialog = new DatePickerDialog(_view.getContext(), style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}