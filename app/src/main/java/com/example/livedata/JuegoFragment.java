package com.example.livedata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.livedata.databinding.FragmentJuegoBinding;


public class JuegoFragment extends Fragment {

    private FragmentJuegoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentJuegoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JuegoViewModel juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);

        juegoViewModel.obtenerGesto().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer gesto) {
                Glide.with(JuegoFragment.this).load(gesto).into(binding.gesto);
            }
        });

        juegoViewModel.obtenerCuentaAtras().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String cuentaAtras) {
                if(cuentaAtras.equals("CAMBIO")){
                    binding.cambio.setVisibility(View.VISIBLE);
                } else {
                    binding.cambio.setVisibility(View.GONE);
                }
                binding.cuentaAtras.setText(cuentaAtras);
            }
        });
    }
}