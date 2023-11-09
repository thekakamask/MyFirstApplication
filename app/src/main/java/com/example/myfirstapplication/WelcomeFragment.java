package com.example.myfirstapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapplication.databinding.FragmentWelcomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
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
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int textColorDisabled = Color.parseColor("#FFFFFF");
        binding.buttonWelcome.setEnabled(false);
        binding.buttonWelcome.setBackgroundColor(0);
        binding.buttonWelcome.setTextColor(textColorDisabled);

        binding.messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int backgroundColor = Color.parseColor("#FEE715");
                int textColorEnabled = Color.parseColor("#000000");
                //Drawable backgroundWithBorder = ContextCompat.getDrawable(getContext(),R.drawable.border_button);

                //binding.buttonWelcome.setEnabled(!s.toString().isEmpty());
                //binding.buttonWelcome.setBackgroundColor(backgroundColor);
                //binding.buttonWelcome.setBackground(backgroundWithBorder);

                if (s.toString().isEmpty()) {
                    binding.buttonWelcome.setEnabled(false);
                    binding.buttonWelcome.setBackgroundColor(0);
                    binding.buttonWelcome.setTextColor(textColorDisabled);
                } else {
                    binding.buttonWelcome.setEnabled(true);
                    //binding.buttonWelcome.setBackground(backgroundWithBorder);
                    binding.buttonWelcome.setBackgroundColor(backgroundColor);
                    binding.buttonWelcome.setTextColor(textColorEnabled);
                }
            }
        });

        binding.buttonWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ImcFragment imcFragment = ImcFragment.newInstance();
                fragmentTransaction.add(R.id.fragment_container, imcFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}