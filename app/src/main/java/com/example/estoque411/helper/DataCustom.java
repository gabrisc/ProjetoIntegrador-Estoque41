package com.example.estoque411.helper;

import java.text.SimpleDateFormat;

public class DataCustom {

	public static String dataAtual(){
		Long data=System.currentTimeMillis();
		SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
		String dataString = simpleDateFormat.format(data);
		return dataString;
	}

	public  static String dataHoraAtual(){
		Long dataHora=System.currentTimeMillis();
		SimpleDateFormat simpleDateAndHourFormat= new SimpleDateFormat("d/M/yyyy hh:mml:ss");
		String dataAndHour = simpleDateAndHourFormat.format(dataHora);
		return dataAndHour;
	}
	public static  String mesAnoAtual(){
		Long mesHora=System.currentTimeMillis();
		SimpleDateFormat simpleDateAndHourFormat= new SimpleDateFormat("MM/yyyy");
		String dataAndHour = simpleDateAndHourFormat.format(mesHora);
		return dataAndHour;

	}
}
