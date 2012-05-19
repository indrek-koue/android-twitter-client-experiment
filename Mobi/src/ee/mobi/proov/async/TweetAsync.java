package ee.mobi.proov.async;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import ee.mobi.proov.FrontPageActivity;
import ee.mobi.proov.MainActivity;
import ee.mobi.proov.util.Auth;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Insert user tweet asyncronously
 * 
 * @author indrek
 * 
 */
public class TweetAsync extends AsyncTask<String, String, String> {

	private Activity a;
	private ProgressDialog dialog;

	public TweetAsync(Activity a) {
		this.a = a;
	}

	@Override
	protected void onPreExecute() {

		dialog = ProgressDialog.show(a, "", "Loading. Please wait...", true);
		dialog.show();

		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {

		twitter4j.Status status = null;
		try {

			status = MainActivity.twitter.updateStatus(params[0]);

		} catch (TwitterException e) {
			Toast.makeText(a.getBaseContext(), "Error: " + e.toString(),
					Toast.LENGTH_LONG).show();

			Log.e("MY", e.toString());
			e.printStackTrace();
		}

		// wait for twitter server to save the tweet
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status.getText();
	}

	@Override
	protected void onPostExecute(String result) {

		dialog.dismiss();

		Toast.makeText(a.getBaseContext(), "Status updated to: " + result,
				Toast.LENGTH_LONG).show();

		// new TimelineAsync(a).execute();
		a.finish();
		a.startActivity(new Intent(a, FrontPageActivity.class));

	}

}
