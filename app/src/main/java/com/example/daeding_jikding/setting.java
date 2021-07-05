package com.example.daeding_jikding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class setting extends Fragment {
    private Button btn_logout;
    private ListView lv_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        lv_settings = (ListView)view.findViewById(R.id.lv_settings);
        List<String> data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        lv_settings.setAdapter(adapter);

        data.add("내 정보");
        data.add("동아리 관리");
        data.add("환경 설정");
        adapter.notifyDataSetChanged();

        lv_settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), data.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //자동로그인 email, pwd preference 삭제 후 로그인 화면으로 이동
                SharedPreferences pref = getActivity().getSharedPreferences("loginPref", getActivity().MODE_PRIVATE);
                pref.edit().remove("autoLogin").commit();

                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }
}