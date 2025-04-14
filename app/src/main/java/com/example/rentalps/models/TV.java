package com.example.rentalps.models;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.example.rentalps.database.BillingDatabaseHelper;

public class TV implements Parcelable {
    private int nomor;
    private boolean sedangDigunakan;
    private long waktuSisaMillis;
    private String konsol;
    private boolean paused;

    private int harga;
    private int durasiAwal;
    private long waktuMulaiMillis;
    private long waktuBerakhirMillis;

    private CountDownTimer countDownTimer;
    private boolean isPaused = false;

    public TV(int nomor) {
        this.nomor = nomor;
        this.sedangDigunakan = false;
        this.waktuSisaMillis = 0;
        this.durasiAwal = 0;
        this.konsol = "";
        this.harga = 0;
        this.waktuMulaiMillis = 0;
        this.waktuBerakhirMillis = 0;
    }
    public boolean isPaused() {
        return paused;
    }


    protected TV(Parcel in) {
        nomor = in.readInt();
        sedangDigunakan = in.readByte() != 0;
        waktuSisaMillis = in.readLong();
        konsol = in.readString();
        harga = in.readInt();
        durasiAwal = in.readInt();
        isPaused = in.readByte() != 0;
        waktuMulaiMillis = in.readLong();
        waktuBerakhirMillis = in.readLong();
    }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel in) {
            return new TV(in);
        }

        @Override
        public TV[] newArray(int size) {
            return new TV[size];
        }
    };

    public int getNomor() {
        return nomor;
    }

    public boolean isSedangDigunakan() {
        return sedangDigunakan;
    }

    public void setSedangDigunakan(boolean status) {
        this.sedangDigunakan = status;
    }

    public long getWaktuSisaMillis() {
        return waktuSisaMillis;
    }

    public void setWaktuSisaMillis(long millis) {
        this.waktuSisaMillis = millis;
    }

    public String getKonsol() {
        return konsol;
    }

    public void setKonsol(String konsol) {
        this.konsol = konsol;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getDurasiAwal() {
        return durasiAwal;
    }

    public void setDurasiAwal(int durasiAwal) {
        this.durasiAwal = durasiAwal;
        this.waktuSisaMillis = (long) durasiAwal * 60 * 1000;
    }

    public long getWaktuMulaiMillis() {
        return waktuMulaiMillis;
    }

    public void setWaktuMulaiMillis(long waktuMulaiMillis) {
        this.waktuMulaiMillis = waktuMulaiMillis;
    }

    public long getWaktuBerakhirMillis() {
        return waktuBerakhirMillis;
    }

    public void setWaktuBerakhirMillis(long waktuBerakhirMillis) {
        this.waktuBerakhirMillis = waktuBerakhirMillis;
    }
    public interface OnTimerTickListener {
        void onTick(long millisUntilFinished);
    }
    private OnTimerTickListener onTimerTickListener;

    public void setOnTimerTickListener(OnTimerTickListener listener) {
        this.onTimerTickListener = listener;
    }

    public void startTimer(Context context, Runnable onTimerFinish) {
        if (countDownTimer != null) countDownTimer.cancel();

        waktuMulaiMillis = System.currentTimeMillis();
        waktuBerakhirMillis = waktuMulaiMillis + waktuSisaMillis;

        countDownTimer = new CountDownTimer(waktuSisaMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                waktuSisaMillis = millisUntilFinished;
                if (onTimerTickListener != null) {
                    onTimerTickListener.onTick(millisUntilFinished); // callback ke adapter
                }
            }
            @Override
            public void onFinish() {
                waktuSisaMillis = 0;
                sedangDigunakan = false;

                // Tambahkan jeda 5 detik sebelum memutar suara
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Log.d("Timer", "Memutar suara untuk TV " + nomor);
                    int resId = context.getResources().getIdentifier("tv" + nomor, "raw", context.getPackageName());
                    if (resId != 0) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(context, resId);
                        mediaPlayer.setOnCompletionListener(mp -> {
                            Log.d("MediaPlayer", "Suara selesai untuk TV " + nomor);
                            mp.release();
                        });
                        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                            Log.e("MediaPlayer", "Error memutar suara untuk TV " + nomor);
                            mp.release();
                            return true;
                        });
                        mediaPlayer.start(); // Mainkan suara sekali
                    } else {
                        Log.e("Timer", "Resource suara tidak ditemukan untuk TV " + nomor);
                    }
                }, 5000); // 5000 ms = 5 detik

                // Masukkan data billing ke database
                BillingDatabaseHelper dbHelper = new BillingDatabaseHelper(context);
                String namaTV = "TV " + nomor;
                long waktuTransaksi = System.currentTimeMillis();
                dbHelper.insertBillingData(namaTV, konsol, durasiAwal, harga, waktuTransaksi);

                // Panggil callback jika tersedia
                if (onTimerFinish != null) onTimerFinish.run();
            }




        }.start();

        isPaused = false;
        sedangDigunakan = true;
    }

    public void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isPaused = true;
        }
    }

    public void resumeTimer(Context context, Runnable onTimerFinish) {
        if (isPaused && waktuSisaMillis > 0) {
            startTimer(context, onTimerFinish);
        }
    }

    public void resetTimer(Runnable onResetFinish) {
        if (countDownTimer != null) countDownTimer.cancel();
        waktuSisaMillis = 0;
        sedangDigunakan = false;
        isPaused = false;
        konsol = "";
        harga = 0;
        durasiAwal = 0;
        waktuMulaiMillis = 0;
        waktuBerakhirMillis = 0;

        if (onResetFinish != null) onResetFinish.run();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nomor);
        dest.writeByte((byte) (sedangDigunakan ? 1 : 0));
        dest.writeLong(waktuSisaMillis);
        dest.writeString(konsol);
        dest.writeInt(harga);
        dest.writeInt(durasiAwal);
        dest.writeByte((byte) (isPaused ? 1 : 0));
        dest.writeLong(waktuMulaiMillis);
        dest.writeLong(waktuBerakhirMillis);
    }
}
