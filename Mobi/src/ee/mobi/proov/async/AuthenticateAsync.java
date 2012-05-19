package ee.mobi.proov.async;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import ee.mobi.proov.FrontPageActivity;
import ee.mobi.proov.MainActivity;
import ee.mobi.proov.util.Auth;

public class AuthenticateAsync extends AsyncTask<String, String, AccessToken> {

	private Activity a;
	private ProgressDialog dialog;

	public AuthenticateAsync(Activity a) {
		this.a = a;
	}

	@Override
	protected void onPreExecute() {

		dialog = ProgressDialog.show(a, "", "Loading. Please wait...", true);
		dialog.show();

		super.onPreExecute();
	}

	@Override
	protected AccessToken doInBackground(String... params) {

		Log.i("MY", "async auth start, pin: " + params[0]);

		AccessToken accessToken = null;
		while (accessToken == null) {

			try {
				accessToken = MainActivity.twitter
						.getOAuthAccessToken(params[0]);
			} catch (TwitterException e) {
				if (401 == e.getStatusCode())
					Log.e("MY",
							"Unable to get the access token." + e.toString());
				e.printStackTrace();
			}

		}

		return accessToken;

	}

	@Override
	protected void onPostExecute(AccessToken result) {

		Auth.storeAccessToken(a, 0, result);

		dialog.dismiss();

		Log.i("MY", "async auth end");
		Toast.makeText(a, "Authenticate success", Toast.LENGTH_LONG).show();

		a.startActivity(new Intent(a, FrontPageActivity.class));

	}

}
