package com.example.estoque411;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.estoque411.Model.Produto;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Resultados extends AppCompatActivity {
	private float rainfall[]={98.0f,10.4f, 86.78f};
	private String mounthNames[]={"jan","fev","mar"};
	private List<Produto> produtos=new ArrayList<>();
	private Iterator<Produto> iterator=produtos.iterator();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultados);
		setupGrafico();



	}

// EM CONSTRUÇÃO

	private void setupGrafico() {
		List<PieEntry> pieEntries= new ArrayList<>();

		for(int i=0;i<rainfall.length;i++){

			pieEntries.add(new PieEntry((rainfall[i]),mounthNames[i]));
		}
		PieDataSet dataset = new PieDataSet(pieEntries,"Rainfall");
		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
		dataset.setValueTextSize(15);
		PieData data = new PieData(dataset);
		PieChart chart=findViewById(R.id.grafico);
		chart.setData(data);
		chart.invalidate();

	}

	}





