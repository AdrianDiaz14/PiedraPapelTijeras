package com.example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import kotlin.jvm.functions.Function1;


public class JuegoViewModel extends AndroidViewModel {
    Juego juego;

    LiveData<Integer> gestoLiveData;
    LiveData<String> cuentaAtrasLiveData;

    public JuegoViewModel(@NonNull Application application) {
        super(application);

        juego = new Juego();

        gestoLiveData = Transformations.switchMap(juego.ordenLiveData, new Function1<String, LiveData<Integer>>() {

            String partidaAnterior;

            @Override
            public LiveData<Integer> invoke(String orden) {

                String gesto = orden.split(":")[0];

                if(!gesto.equals(partidaAnterior)){
                    partidaAnterior = gesto;
                    int imagen;
                    switch (gesto) {
                        case "GESTO1":
                        default:
                            imagen = R.drawable.papel;
                            break;
                        case "GESTO2":
                            imagen = R.drawable.piedra;
                            break;
                        case "GESTO3":
                            imagen = R.drawable.tijeras;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });

        cuentaAtrasLiveData = Transformations.switchMap(juego.ordenLiveData, new Function1<String, LiveData<String>>() {
            @Override
            public LiveData<String> invoke(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }
        });
    }

    LiveData<Integer> obtenerGesto(){
        return gestoLiveData;
    }

    LiveData<String> obtenerCuentaAtras(){
        return cuentaAtrasLiveData;
    }
}