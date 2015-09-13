package cn.cocoder.aidldemo;

import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private IHelloWorld mHelloWorld;

	private ServiceConnection con = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mHelloWorld = IHelloWorld.Stub.asInterface(service);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button getHelloWorld = (Button) findViewById(R.id.btn_helloworld);
		getHelloWorld.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (mHelloWorld != null) {
						String str = mHelloWorld.hello();
						Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(MainActivity.this, "please bind service first", Toast.LENGTH_LONG).show();
					}
				} catch (RemoteException e) {
				}
			}
		});
		Button bind = (Button) findViewById(R.id.btn_bindservice);
		bind.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AidlService.class);
				boolean result = bindService(intent, con, Context.BIND_AUTO_CREATE);
				if(!result) {
					mHelloWorld = null;
					Toast.makeText(MainActivity.this, "bind service fail", Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button unbind = (Button) findViewById(R.id.btn_unbindservice);
		unbind.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mHelloWorld != null) {
					unbindService(con);
					mHelloWorld = null;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
