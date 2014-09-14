package com.moviles.mundo;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "La alarma se activó", Toast.LENGTH_LONG)
				.show();

		// Lanza la actividad correspondiente
		int idAlarma = intent.getIntExtra("idAlarma", -1);
		Intent i = new Intent();
		i.putExtra("idAlarma", idAlarma);
		i.setClassName("com.moviles.happer",
				"com.moviles.happer.DetalleAlarmaActivity");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);

		// Vibracion
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(10000);

		// Reproducir ringtone
		Uri ringtone = getAlarmUri();
		final Ringtone r = RingtoneManager.getRingtone(context, ringtone);
		r.play();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				r.stop();
			}
		};
		if (r.isPlaying()) {
			Timer timer = new Timer();
			timer.schedule(task, 10000);
		}
	}

	/**
	 * Devuelve el ringtone a ser reproducido
	 * 
	 * @return Uri con el ringtone
	 */
	private Uri getAlarmUri() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null)
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

		}
		return alert;
	}
}
