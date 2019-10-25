package com.example.cataloguemovie.view;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cataloguemovie.R;
import com.example.cataloguemovie.reminder.DailyReminder;
import com.example.cataloguemovie.reminder.UpcomingReminder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {


    ConstraintLayout settingLang;
    Button btnreminder, btnTurnoff, btnDailyOff, btnDailyOn;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingLang = view.findViewById(R.id.settingLanguage);
        btnreminder = view.findViewById(R.id.mvupcoming);
        btnTurnoff = view.findViewById(R.id.turn_off);
        btnDailyOn = view.findViewById(R.id.dailyreminder);
        btnDailyOff = view.findViewById(R.id.turn_off_daily);

        btnDailyOn.setOnClickListener(this);
        btnDailyOff.setOnClickListener(this);
        btnTurnoff.setOnClickListener(this);
        btnreminder.setOnClickListener(this);
        settingLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mvupcoming:
                final UpcomingReminder upcomingReminder = new UpcomingReminder();
                upcomingReminder.setRepeatingAlarm(getContext());
                Toast.makeText(getContext(), "Reminder Aktif", Toast.LENGTH_SHORT).show();
                break;
            case R.id.turn_off:
                final UpcomingReminder cancelReminder = new UpcomingReminder();
                cancelReminder.cancelReminder(getContext());
                Toast.makeText(getContext(), "Reminder Mati", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dailyreminder:
                final DailyReminder turnOnDaily = new DailyReminder();
                turnOnDaily.setRepeatingAlarm(getContext());
                Toast.makeText(getContext(), "Daily Aktif", Toast.LENGTH_SHORT).show();
                break;
            case R.id.turn_off_daily:
                final DailyReminder turnOffDaily = new DailyReminder();
                turnOffDaily.cancelAlarm(getContext());
                Toast.makeText(getContext(), "Daily Mati", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
