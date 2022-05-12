package com.example.quanlychitieu;

import android.content.Intent;
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
import com.google.firestore.*;

import java.time.*;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;

import android.util.Log;

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
        // Required empty public constructor
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

    EditText inputAmount, inputNote;
    Button btnConfirm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        inputAmount = getView().findViewById(R.id.inputAmount);
        inputAmount = getView().findViewById(R.id.inputNote);
        btnConfirm = getView().findViewById(R.id.btnConfirm);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int amount = Integer.parseInt(inputAmount.getText().toString());
                    String note = inputAmount.getText().toString();
                    //TODO: SAVE DATA TO FIREBASE
                    Map<String, Object> newOutcome = new HashMap<>();
                    newOutcome.put("amount", amount);
                    newOutcome.put("type", 3);
                    newOutcome.put("date", null);
                    newOutcome.put("note", note);
                    // Add a new document with a generated ID
                    db.collection("outcome")
                            .add(newOutcome)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("CHECK_ADD_OUTCOME", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("CHECK_ADD_OUTCOME", "Error adding document", e);
                                }
                            });
                }
                catch(Exception e)
                {

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }
//    private void setChildrenOnClickListener(AppCompatRadioButton child) {
//        GridLayout parent = (GridLayout) child.getParent();
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View v = parent.getChildAt(i);
//            if (v instanceof AppCompatRadioButton) {
//                if (((RadioButton) v).isChecked()) {
//                    activeRadioButton = (AppCompatRadioButton) v;
//                }
//                v.setOnClickListener(this);
//            }
//        }
//    }
}