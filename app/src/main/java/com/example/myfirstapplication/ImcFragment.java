package com.example.myfirstapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapplication.databinding.FragmentImcBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImcFragment extends Fragment {

    private FragmentImcBinding binding;

    public static ImcFragment newInstance() {
        ImcFragment fragment = new ImcFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentImcBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}