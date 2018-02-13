package com.goose77.router2.Router2.UI;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goose77.router2.R;
import com.goose77.router2.Router2.support.ParentActivity;

/**
 * Created by goose on 2/8/2018.
 */

public class AddAdjacencyDialog extends DialogFragment {
    private EditText ipAddressEditText;
    private EditText ll2pAddressEditText;
    private Button addAdjacencyButton;
    private Button cancelButton;
    public interface AdjacencyPairListener{
        void onFinishedEditDialog(String ipAddress, String ll2pAddress);
    }

    public AddAdjacencyDialog(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_adjacency_dialog, container, false);
        ipAddressEditText = (EditText) rootView.findViewById(R.id.ipAddressEditTextView);
        ll2pAddressEditText = (EditText) rootView.findViewById(R.id.ll2pEditTextView);
        addAdjacencyButton = (Button) rootView.findViewById(R.id.addAdjacencyButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        addAdjacencyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AdjacencyPairListener activity = (AdjacencyPairListener) ParentActivity.getParentActivity();
                activity.onFinishedEditDialog(ipAddressEditText.getText().toString(), ll2pAddressEditText.getText().toString());
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }




}
