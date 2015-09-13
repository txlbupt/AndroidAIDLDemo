package cn.cocoder.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AidlService extends Service {
	
	private IHelloWorld.Stub mBinder = new IHelloWorld.Stub() {
		
		@Override
		public String hello() throws RemoteException {
			// TODO Auto-generated method stub
			return "Hello World!";
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

}
