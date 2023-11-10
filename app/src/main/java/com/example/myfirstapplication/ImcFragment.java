package com.example.myfirstapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myfirstapplication.databinding.FragmentImcBinding;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImcFragment extends Fragment {

    private FragmentImcBinding binding;
    private String imcCalculate = "";

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



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();
        setupColors();
        initializeButtonColors();

    }

    private void setupColors() {
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked}, // checked
                        new int[]{-android.R.attr.state_checked}, // unchecked
                },
                new int[]{
                        Color.YELLOW, // the color for the checked state
                        Color.BLACK,  // the color for the unchecked state
                }
        );

        binding.radio1.setButtonTintList(colorStateList);
        binding.radio2.setButtonTintList(colorStateList);
        binding.mega.setButtonTintList(colorStateList);
    }

    private void setupListeners() {
        binding.calculImc.setOnClickListener(v -> calculateImc());
        binding.raz.setOnClickListener(v -> resetFields());
        binding.mega.setOnCheckedChangeListener(this::toggleMegaFeature);
        TextWatcher textWatcher = getTextWatcher();
        binding.poids.addTextChangedListener(textWatcher);
        binding.taille.addTextChangedListener(textWatcher);
        binding.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                convertSizeValue(i);
            }
        });
    }

    private void convertSizeValue(int i) {
        String sizeStr = binding.taille.getText().toString();
        if (!sizeStr.isEmpty()) {
            try {
                float tailleValue = Float.parseFloat(sizeStr);
                if (i == R.id.radio1 && tailleValue > 2) {

                    tailleValue = tailleValue / 100;
                    binding.taille.setText(String.format(Locale.getDefault(), "%.2f", tailleValue));
                } else if (i == R.id.radio2 && tailleValue <= 2) {

                    tailleValue = tailleValue * 100;
                    binding.taille.setText(String.format(Locale.getDefault(), "%.0f", tailleValue));
                }
            } catch (NumberFormatException e) {

            }
        }
    }
    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.result.setText(R.string.result);
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateButtonState();
            }
        };
    }

    private void resetFields() {
        binding.poids.setText("");
        binding.taille.setText("");
        binding.mega.setChecked(false);
        binding.group.clearCheck();
        imcCalculate = "";
        binding.result.setText(R.string.result);
        updateButtonState();
    }

    private void toggleMegaFeature(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (!imcCalculate.isEmpty()) {
                binding.result.setText("You are always pretty");
            } else {
                binding.result.setText(R.string.result);
            }
        } else {
            binding.result.setText(imcCalculate.isEmpty() ? getString(R.string.result) : imcCalculate);
        }
    }



    private void updateButtonState() {

        int backgroundColor = Color.parseColor("#FEE715");

        boolean isPoidsFilled = binding.poids.getText().length() > 0;
        boolean isTailleFilled = binding.taille.getText().length() > 0;
        boolean shouldEnableButtons = isPoidsFilled && isTailleFilled;


        if (shouldEnableButtons) {
            binding.calculImc.setBackgroundColor(backgroundColor);
            binding.raz.setBackgroundColor(backgroundColor);
            binding.calculImc.setEnabled(true);
            binding.raz.setEnabled(true);
        } else {
            binding.calculImc.setBackgroundColor(0);
            binding.raz.setBackgroundColor(0);
            binding.calculImc.setEnabled(false);
            binding.raz.setEnabled(false);
        }
    }

    private void calculateImc() {
        String poidsResult = binding.poids.getText().toString();
        String tailleResult = binding.taille.getText().toString();
        float imc = 0;

        if (!poidsResult.isEmpty() && !tailleResult.isEmpty()) {
            try {
                float poidsFloat = Float.parseFloat(poidsResult);
                float tailleFloat = Float.parseFloat(tailleResult);

                if (poidsFloat<= 0 && tailleFloat <= 0) {
                    Toast.makeText(getActivity(), "Weight and Size need to be superior to zero", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (binding.radio1.isChecked()) {
                    // AUCUN CHANGEMENT PUISQUE TAILLE EN METRES
                } else if (binding.radio2.isChecked()){
                    // CHANGEMENT PUISQUE TAILLE EN CENTIMETRES
                    tailleFloat=tailleFloat/100;
                } else {
                    // AUCUN RADIOBUTTON COCHE
                    binding.result.setText("Please, choose your size in meters or in centimeters");
                    return;
                }

                if (tailleFloat>0) {
                    imc = poidsFloat / (tailleFloat * tailleFloat);
                    imcCalculate= String.format("Your IMC is : %.2f", imc);
                    binding.result.setText(String.format("Your IMC is : %.2f", imc));

                } else {
                    binding.result.setText("Invalid size");
                }


            } catch (NumberFormatException e) {
                binding.result.setText("Invalid Input");
            }


        } else {
            binding.result.setText("Please choose your size and your weight");
        }


    }

    private void initializeButtonColors() {
        binding.calculImc.setBackgroundColor(0);
        binding.raz.setBackgroundColor(0);
    }
}