package br.com.juliana.loureiro.projetofinalahp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import br.com.juliana.loureiro.projetofinalahp.R;
import br.com.juliana.loureiro.projetofinalahp.Util.OnSwip;

public class SobreAHP extends AppCompatActivity {
    private ImageView img1, img2, img3;
    private CardView card;
    private TextView text;
    private int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_ahp);


        declaraObjetos();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }

    private void declaraObjetos() {
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        card = findViewById(R.id.card);
        text = findViewById(R.id.text);

        card.setOnTouchListener(new OnSwip(this) {
            public void onSwipeTop() {

            }

            public void onSwipeLeft() {
                card.startAnimation(AnimationUtils.loadAnimation(SobreAHP.this, R.anim.slide_out_left));
                if(i==1) {
                    img1.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));
                    img2.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_data));
                    img3.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));
                    text.setText("Ao reduzir decisões complexas a uma série de comparações individuais, depois sintetizar os resultados, o AHP não apenas ajuda os tomadores " +
                            "de decisão a chegar à melhor decisão, mas também fornece uma justificativa clara de que é o melhor.");
                    i++;
                } else if(i==2) {
                    img1.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));
                    img2.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));
                    img3.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_data));

                   text.setText("O AHP e o software DECISION AHP envolvem os tomadores de decisão na estruturação de uma decisão em partes menores, desde a meta até os objetivos e os sub-objetivos até os cursos alternativos de ação.");
                    i++;
                }

            }

            public void onSwipeRight() {
                card.startAnimation(AnimationUtils.loadAnimation(SobreAHP.this, R.anim.slide_out_right));
                if(i==2) {
                    img1.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_data));
                    img2.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));
                    img3.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));

                    text.setText("O Analytic Hierarchy Process (AHP) é um processo decisório poderoso e flexível para ajudar as pessoas a estabelecer prioridades e tomar a melhor decisão quando os aspectos qualitativos e quantitativos de uma decisão precisam ser considerados.");
                    i--;
                } else if(i==3) {
                    img1.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));
                    img2.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_data));
                    img3.setImageDrawable(ActivityCompat.getDrawable(SobreAHP.this, R.drawable.shape_branco));

                    text.setText("Ao reduzir decisões complexas a uma série de comparações individuais, depois sintetizar os resultados, o AHP não apenas ajuda os tomadores " +
                            "de decisão a chegar à melhor decisão, mas também fornece uma justificativa clara de que é o melhor.");
                    i--;
                }
            }

            public void onSwipeBottom() {

            }

        });
    }


}
