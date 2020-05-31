package com.example.fitcode_fitterthanyesterday;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReportFragment extends Fragment {

    static TextView currentWeight;
    static TextView currentHeight;
    static TextView bmiText;

    static TextView maxWeight;
    static TextView minWeight;
    static TextView current_weight_analysis;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports,container,false);

        SharedPreferences sp = getActivity().getSharedPreferences("com.example.fitcode_fitterthanyesterday", Context.MODE_PRIVATE);

        currentWeight = view.findViewById(R.id.currentweight);
        currentHeight = view.findViewById(R.id.currentheight);
        bmiText = view.findViewById(R.id.bmitext);

        maxWeight = view.findViewById(R.id.maxweight);
        minWeight = view.findViewById(R.id.minweight);
        current_weight_analysis = view.findViewById(R.id.weightanalysiscurrentweight);

        ReportFragment.bmiText.invalidate();
        ReportFragment.current_weight_analysis.invalidate();
        ReportFragment.currentHeight.invalidate();
        ReportFragment.currentHeight.invalidate();
        ReportFragment.maxWeight.invalidate();
        ReportFragment.minWeight.invalidate();

        float max_weight_float = sp.getFloat("maxweight",0);
        float min_weight_float = sp.getFloat("minweight",0);
        float current_weight_float = sp.getFloat("weight",0);
        float current_height_float = sp.getFloat("height",0);

        String maxWeightTextSetter = "Max Weight : " + max_weight_float + " kg";
        maxWeight.setText(maxWeightTextSetter);

        String minWeightTextSetter = "Min Weight : " + min_weight_float + " kg";
        minWeight.setText(minWeightTextSetter);

        String currentWeightTextSetter = "Current Weight : " + current_weight_float + " kg";
        current_weight_analysis.setText(currentWeightTextSetter);
        currentWeight.setText(currentWeightTextSetter);

        String currentHeightTextSetter = "Current Height - " + current_height_float + " cm";
        currentHeight.setText(currentHeightTextSetter);

        float bmi = current_weight_float /((current_height_float / 100.0F) * (current_height_float / 100.0F));

        String bmiTextSetter = "BMI : " + bmi;
        if (bmi < 18.5F)
            bmiTextSetter+=" (Underweight)";
        else if (bmi > 30.0F)
            bmiTextSetter+=" (Obese)";
        else if (bmi > 24F )
            bmiTextSetter+=" (Overweight)";
        else
            bmiTextSetter+= " (Healthy weight)";

        bmiText.setText(bmiTextSetter);

        setHasOptionsMenu(true);

        return view;
    }
}
