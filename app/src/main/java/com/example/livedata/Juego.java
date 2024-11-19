package com.example.livedata;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

public class Juego {

    interface JuegoListener {
        void cuandoDeLaOrden(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> jugando;

    void iniciarPartida(JuegoListener juegoListener) {
        if (jugando == null || jugando.isCancelled()) {
            jugando = scheduler.scheduleAtFixedRate(new Runnable() {
                int gesto;
                int cuentas = -1;

                @Override
                public void run() {
                    if (cuentas < 0) {
                        cuentas = random.nextInt(3) + 3;
                        gesto = random.nextInt(5)+1;
                    }
                    juegoListener.cuandoDeLaOrden("GESTO" + gesto + ":" + (cuentas == 0 ? "CAMBIO" : cuentas));
                    cuentas--;
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararPartida() {
        if (jugando != null) {
            jugando.cancel(true);
        }
    }

    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarPartida(new JuegoListener() {
                @Override
                public void cuandoDeLaOrden(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararPartida();
        }
    };
}