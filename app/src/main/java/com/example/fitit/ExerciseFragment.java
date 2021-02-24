package com.example.fitit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ExerciseFragment extends Fragment {
    private Button upperlimb_btn1, lowerlimb_btn1, softness_btn1, endurance_btn1, back_btn, ropeExe1_btn, ropeExe2_btn, Demo_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findObject();
        clickBtnEvent();
    }

    private void clickBtnEvent() {
        Demo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Demo_exercise.class);
                startActivity(intent);
            }
        });
    }


    private void findObject() {
        back_btn = this.getView().findViewById(R.id.back_btn);
        upperlimb_btn1 = this.getView().findViewById(R.id.upperlimb_btn1);
        lowerlimb_btn1 = this.getView().findViewById(R.id.lowerlimb_btn1);
        softness_btn1 = this.getView().findViewById(R.id.softness_btn1);
        endurance_btn1 = this.getView().findViewById(R.id.endurance_btn1);
        ropeExe1_btn = this.getView().findViewById(R.id.ropeExe1_btn);
        ropeExe2_btn = this.getView().findViewById(R.id.ropeExe2_btn);
        Demo_btn = this.getView().findViewById(R.id.Demo_btn);
    }

}