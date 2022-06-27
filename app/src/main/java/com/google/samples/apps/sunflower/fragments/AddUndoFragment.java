package com.google.samples.apps.sunflower.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.net.bean.UndoBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUndoFragment extends Fragment {
    EditText undoName;
    EditText undoContent;
    EditText undoTime;
    EditText degree;
    Button add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_undo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        undoName = (EditText)getActivity().findViewById(R.id.undo_name);
        undoContent = (EditText)getActivity().findViewById(R.id.undo_content);
        undoTime = (EditText)getActivity().findViewById(R.id.undo_time);
        degree = (EditText)getActivity().findViewById(R.id.degree);
        add = (Button) getActivity().findViewById(R.id.add_undo);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createTime = new Date(System.currentTimeMillis());
                Date deadline = new Date(System.currentTimeMillis() + Integer.valueOf(undoTime.getText().toString()) * 60 * 60 * 1000 );
                String createTimeFormat = formatter.format(createTime);
                String deadTimeFormat = formatter.format(deadline);

                UndoBean undoBean = new UndoBean(0,
                        undoName.getText().toString(),
                        undoContent.getText().toString(),
                        createTimeFormat,
                        deadTimeFormat,
                        Integer.valueOf(degree.getText().toString()),
                        Integer.valueOf(undoTime.getText().toString()) < 24 ? 1 : 0,
                        1
                        ) ;

                //添加待办

                //然后跳转
                Navigation.findNavController(view).navigate(
                        AddUndoFragmentDirections.actionAddUndoFragmentToUndoListFragment()
                );
            }
        });
    }
}