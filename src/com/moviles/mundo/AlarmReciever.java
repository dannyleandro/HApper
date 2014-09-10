package com.moviles.mundo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		 Toast.makeText(context, "La alarma se activó", Toast.LENGTH_LONG).show();
		 System.out.println("Sí llegaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		 Intent i = new Intent();
		 i.setClassName("com.moviles.happer", "com.moviles.happer.AlarmasActivity");
	     i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     context.startActivity(i);
	}
}
