package eu.marcin.srodkitransportu1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class EkranMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ekran_main);
        TextView textViewSrodkiTransportu = (TextView) findViewById(R.id.textViewSrodkiTransportu);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/AdelleSans.ttf");
        textViewSrodkiTransportu.setTypeface(myFont);


    }



    public void przejdzDoAplikacji(View view) {
        Intent noweOkno = new Intent(getApplicationContext(), Ekran2.class);
        startActivity(noweOkno);


    }



}
