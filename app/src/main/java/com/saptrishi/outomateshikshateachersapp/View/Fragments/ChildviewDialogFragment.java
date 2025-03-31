package com.saptrishi.outomateshikshateachersapp.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.saptrishi.outomateshikshateachersapp.R;

public class ChildviewDialogFragment extends DialogFragment {

    private boolean notAlertDialog;

    public ChildviewDialogFragment() {
        // Empty constructor (required)
    }

    public static ChildviewDialogFragment newInstance(boolean notAlertDialog) {
        ChildviewDialogFragment fragment = new ChildviewDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("notAlertDialog", notAlertDialog);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notAlertDialog = getArguments().getBoolean("notAlertDialog", false);
        }
        setCancelable(false); // Making the dialog non-dismissible
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate your custom layout
        return inflater.inflate(R.layout.fragment_childview_dialog, container, false);
    }
}
