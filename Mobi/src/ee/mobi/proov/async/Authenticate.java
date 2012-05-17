package ee.mobi.proov.async;

import ee.mobi.proov.LoginActivity;
import ee.mobi.proov.MainActivity;
import ee.mobi.proov.util.Auth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class Authenticate extends AsyncTask<String, String, AccessToken> {

	Activity a;
	Twitter twitter;

	public Authenticate(Activity a) {
		this.a = a;
	}

	@Override
	protected AccessToken doInBackground(String... params) {

		AccessToken accessToken = null;
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(LoginActivity.CONSUMER_KEY,
				LoginActivity.CONSUMER_SECRET_KEY);
		try {

			while (accessToken == null)
				accessToken = twitter.getOAuthAccessToken(
						twitter.getOAuthRequestToken(), params[0]);

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accessToken;
	}

	@Override
	protected void onPostExecute(AccessToken result) {
		try {
			Auth.storeAccessToken(a, twitter.verifyCredentials().getId(),
					result);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// a.startActivity(new Intent(a, MainActivity.class));

	}

}
