package com.example.myfirstapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapplication.databinding.FragmentResultBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment implements View.OnTouchListener {


    private FragmentResultBinding binding;

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonResult.setOnTouchListener(this);



    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.button_result) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    //CACUL DE LA DISTANCE DU TOUCHER AU CENTRE DU BOUTON
                    float buttonCenterX = view.getWidth() /2f;
                    float buttonCenterY = view.getHeight() /2f;
                    float touchX = motionEvent.getX();
                    float touchY = motionEvent.getY();
                    float distance = distanceFromCenter(buttonCenterX, buttonCenterY, touchX, touchY);
                    float newTextSize = calculateTextSize(distance);

                    // APPLICATION DE LA NOUVELLE TAILLE DU TEXTE AU BOUTON
                    binding.buttonResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // REINITIALISATION DE LA TAILLE DU TEXTE LORSQUE LE TOUCHER EST TERMINE
                    // TAILLE INITIALE EN SP
                    binding.buttonResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                    break;
            }
        }
        return true;
    }

    private float distanceFromCenter(float centerX, float centerY, float touchX, float touchY) {
        return (float) Math.sqrt(Math.pow(centerX - touchX, 2) + Math.pow(centerY - touchY, 2));
    }

    private float calculateTextSize(float distance) {
        //TAILLE DU TEXTE DE BASE
        float baseSize = 30;
        // AUGMENTATION DE LA TAILLE DU TEXTE PAR RAPPORT A LA DISTANCE
        float sizeVariation = distance / 2;
        //CALCUL DE LA NOUVELLE TAILLE
        float newSize = baseSize + sizeVariation;
        // TAILLE MAX DU TEXTE
        float maxSize = 240;

        //MATH.MIN POUR ETRE SUR QUE LA TAILLE DU TEXTE NE DEPASSE PAS MAXSIZE
        return Math.min(maxSize, newSize);
    }

}