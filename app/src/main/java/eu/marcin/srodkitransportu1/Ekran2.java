package eu.marcin.srodkitransportu1;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Random;


/**
 * Created by Marcin on 2016-07-21.
 */
public class Ekran2 extends Activity  implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final float MAX_ZOOM = 1.2f;
    private GestureDetectorCompat gDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;


    Handler handler = new Handler();
    Runnable myRunable;

    final ScaleAnimation skalujAnimacjeWGore = new ScaleAnimation(
            1, MAX_ZOOM,
            1, MAX_ZOOM,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
    );

    final ScaleAnimation skalujAnimacjeWDol = new ScaleAnimation(
            MAX_ZOOM, 1,
            MAX_ZOOM, 1,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
    );

    //resetowanie rozmiaru obrazka przy przechodzeniu NEXT - PREV do rozmiatru 1:1
    final ScaleAnimation skaluj1do1 = new ScaleAnimation(
            1,1,1,1, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);








    int licznik = 0;
    String[] nazwy;
    MediaPlayer mp3;
    boolean flaga = true;


    ImageView imageViewObrazkiZTablicy;
    TextView textSrodekTransportuNazwa;

    ImageView imageButtonMuteOnOff;

    int[] obrazy = {

            R.drawable.auto,            R.drawable.autobus,         R.drawable.balon,
            R.drawable.bryczka,         R.drawable.helikopter,      R.drawable.kajak,
            R.drawable.lodka,           R.drawable.motolotnia,      R.drawable.mtocykl,
            R.drawable.pociag,          R.drawable.rower,           R.drawable.rower_wodny,
            R.drawable.samolot,         R.drawable.skuter_wodny,    R.drawable.sterowiec,
            R.drawable.traktor,         R.drawable.zaglowka


    };
    int iloscEkranow = obrazy.length;

    int[] myMusic = {

            R.raw.auto,                 R.raw.autobus,              R.raw.balon,
            R.raw.bryczka,              R.raw.helikopter,           R.raw.kajak,
            R.raw.lodka,                R.raw.motolotnia,           R.raw.motocykl,
            R.raw.pociag,               R.raw.rower,                R.raw.rower_wodny,
            R.raw.samolot,              R.raw.skuter_wodny,         R.raw.sterowiec,
            R.raw.traktor,              R.raw.zaglowka


    };

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int[] num = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    int[] losowe = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

    boolean flaga11 = false;
    int licznikPetliWhile = 0;

    Random randomize = new Random();
    int liczbaLosowa1;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        skalujAnimacjeWGore.setFillAfter(true);
        skalujAnimacjeWGore.setDuration(2000);

        skalujAnimacjeWDol.setFillAfter(true);
        skalujAnimacjeWDol.setDuration(2000);

        skaluj1do1.setFillAfter(false);
        skaluj1do1.setDuration(0);

        skalujAnimacjeWGore.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageViewObrazkiZTablicy.startAnimation(skalujAnimacjeWDol);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.ekran2);

        imageButtonMuteOnOff = (ImageView) findViewById(R.id.imageButtonMuteOnOff);
        imageButtonMuteOnOff.setImageResource(R.drawable.btn_mute_on);





        nazwy = new String[]{
                getResources().getString(R.string.srodekTransportu_1),
                getResources().getString(R.string.srodekTransportu_2),
                getResources().getString(R.string.srodekTransportu_3),
                getResources().getString(R.string.srodekTransportu_4),
                getResources().getString(R.string.srodekTransportu_5),
                getResources().getString(R.string.srodekTransportu_6),
                getResources().getString(R.string.srodekTransportu_7),
                getResources().getString(R.string.srodekTransportu_8),
                getResources().getString(R.string.srodekTransportu_9),
                getResources().getString(R.string.srodekTransportu_10),
                getResources().getString(R.string.srodekTransportu_11),
                getResources().getString(R.string.srodekTransportu_12),
                getResources().getString(R.string.srodekTransportu_13),
                getResources().getString(R.string.srodekTransportu_14),
                getResources().getString(R.string.srodekTransportu_15),
                getResources().getString(R.string.srodekTransportu_16),
                getResources().getString(R.string.srodekTransportu_17)
        };
        textSrodekTransportuNazwa = (TextView) findViewById(R.id.textSrodekTransportuNazwa);
        randomizujTablce();

        this.gDetector = new GestureDetectorCompat(this, this);
        gDetector.setOnDoubleTapListener(this);


        //koniec onCreate
    }


    private void randomizujTablce() {
       // imageViewObrazkiZTablicy.startAnimation(skaluj1do1);

        textSrodekTransportuNazwa.setText("");
        licznikPetliWhile = 0;

        while (licznikPetliWhile<num.length) {

            flaga11 = false;

            liczbaLosowa1 = randomize.nextInt(num.length);

            for (int z=0; z<losowe.length; z++) {

                if (losowe[z] == num[liczbaLosowa1]) {
                    flaga11 = true;
                }
            }

            if (flaga11 == false) {

                losowe[licznikPetliWhile] = num[liczbaLosowa1];
                textSrodekTransportuNazwa.append(licznikPetliWhile + " - " +  losowe[licznikPetliWhile]+ "\n");
                licznikPetliWhile++;
            }


        }

        imageViewObrazkiZTablicy = (ImageView) findViewById(R.id.imageViewSrodkiTransportu);
        imageViewObrazkiZTablicy.setImageResource(obrazy[losowe[licznik]]);



        myRunable = new Runnable() {
           @Override
           public void run() {
               imageViewObrazkiZTablicy.startAnimation(skalujAnimacjeWGore);//opóźnienie animacji
           }
       };

        handler.postDelayed(myRunable, 2000);

        mp3 = MediaPlayer.create(this, myMusic[losowe[licznik]]);
        mp3.setLooping(false);
        mp3.start();

        textSrodekTransportuNazwa = (TextView) findViewById(R.id.textSrodekTransportuNazwa);
        textSrodekTransportuNazwa.setText(nazwy[losowe[licznik]]);

    }


    @Override
    protected void onStop() {
        mp3.stop();
        super.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gDetector.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
       return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
       return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
       return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
       return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {///////////////////////////
        try {
            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                pokazNastepnySrodekTransportu();

            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                pokazPoprzedniSrodekTransportu();

            }

        } catch (Exception e) {


        }
        return false;
    }



    private void pokazNastepnySrodekTransportu() {

        handler.removeCallbacks(myRunable);
        imageViewObrazkiZTablicy.startAnimation(skaluj1do1);


        if (licznik<iloscEkranow-1) {
            licznik++;
        } else {
            licznik=0;
        }


        imageViewObrazkiZTablicy.setImageResource(obrazy[losowe[licznik]]);
        myRunable = new Runnable() {
            @Override
            public void run() {
                imageViewObrazkiZTablicy.startAnimation(skalujAnimacjeWGore);//opóźnienie animacji
            }
        };

        handler.postDelayed(myRunable, 2000);
        textSrodekTransportuNazwa.setText(nazwy[losowe[licznik]]);

        if(mp3.isPlaying()) mp3.stop();
        mp3 = MediaPlayer.create(this, myMusic[losowe[licznik]]);
        mp3.setLooping(false);

        if (!flaga) {
            mp3.setVolume(0,0);

        } else if (flaga) {

            mp3.setVolume(1,1);
            mp3.start();
        }


    }

    private void pokazPoprzedniSrodekTransportu() {
        handler.removeCallbacks(myRunable);
        imageViewObrazkiZTablicy.startAnimation(skaluj1do1);

        if (licznik==0) {
            licznik = iloscEkranow-1;
        } else {
            licznik--;
        }


        imageViewObrazkiZTablicy.setImageResource(obrazy[losowe[licznik]]);
        myRunable = new Runnable() {
            @Override
            public void run() {
                imageViewObrazkiZTablicy.startAnimation(skalujAnimacjeWGore);//opóźnienie animacji
            }
        };

        handler.postDelayed(myRunable, 2000);

        textSrodekTransportuNazwa.setText(nazwy[losowe[licznik]]);

        if(mp3.isPlaying()) mp3.stop();
        mp3 = MediaPlayer.create(this, myMusic[losowe[licznik]]);
        mp3.setLooping(false);

        if (!flaga) {
            mp3.setVolume(0,0);

        } else if (flaga) {

            mp3.setVolume(1,1);
            mp3.start();
        }

    }
    public void pokazNastepnySrodekTransportu(View view) {
        handler.removeCallbacks(myRunable);
        imageViewObrazkiZTablicy.startAnimation(skaluj1do1);

        if (licznik<iloscEkranow-1) {
            licznik++;
        } else {
            licznik=0;
        }


        imageViewObrazkiZTablicy.setImageResource(obrazy[losowe[licznik]]);
        myRunable = new Runnable() {
            @Override
            public void run() {
                imageViewObrazkiZTablicy.startAnimation(skalujAnimacjeWGore);//opóźnienie animacji
            }
        };

        handler.postDelayed(myRunable, 2000);

        textSrodekTransportuNazwa.setText(nazwy[losowe[licznik]]);

        if(mp3.isPlaying()) mp3.stop();
        mp3 = MediaPlayer.create(this, myMusic[losowe[licznik]]);
        mp3.setLooping(false);

        if (!flaga) {
            mp3.setVolume(0,0);

        } else if (flaga) {

            mp3.setVolume(1,1);
            mp3.start();
        }


    }


    public void pokazPoprzedniSrodekTransportu(View view) {
        handler.removeCallbacks(myRunable);
        imageViewObrazkiZTablicy.startAnimation(skaluj1do1);


        if (licznik==0) {
            licznik = iloscEkranow-1;
        } else {
            licznik--;
        }


        imageViewObrazkiZTablicy.setImageResource(obrazy[losowe[licznik]]);
        myRunable = new Runnable() {
            @Override
            public void run() {
                imageViewObrazkiZTablicy.startAnimation(skalujAnimacjeWGore);//opóźnienie animacji
            }
        };

        handler.postDelayed(myRunable, 2000);

        textSrodekTransportuNazwa.setText(nazwy[losowe[licznik]]);

        if(mp3.isPlaying()) mp3.stop();
        mp3 = MediaPlayer.create(this, myMusic[losowe[licznik]]);
        mp3.setLooping(false);

        if (!flaga) {
            mp3.setVolume(0,0);

        } else if (flaga) {

            mp3.setVolume(1,1);
            mp3.start();
        }


    }


    public void musicOnOff(View view) {

        if (flaga) {
            //wyłącz muzykę

            imageButtonMuteOnOff.setImageResource(R.drawable.btn_mute_off);
            flaga = false;
            mp3.setVolume(0,0);

        } else if (!flaga) {
           // włącz muzykę
           flaga = true;
            imageButtonMuteOnOff.setImageResource(R.drawable.btn_mute_on);
            mp3.setVolume(1,1);
        }

    }
}
