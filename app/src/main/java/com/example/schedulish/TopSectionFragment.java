package com.example.schedulish;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class TopSectionFragment  extends Fragment {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_section_fragment, container, false);

        Button classButton = (Button)view.findViewById(R.id.classButton);
        Button termButton = (Button)view.findViewById(R.id.termButton);
        Button examButton = (Button)view.findViewById(R.id.examButton);


        classButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getActivity(), Courses.class);
            startActivity(intent);
        });

        examButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getActivity(), Assessments.class);
            startActivity(intent);
        });

       termButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getActivity(), Terms.class);
            startActivity(intent);
        });

        return view;
    }



}
