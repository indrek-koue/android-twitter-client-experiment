package ee.mobi.proov;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ee.mobi.proov.async.AuthenticateAsync;
import ee.mobi.proov.util.Auth;

/**
 * @author Indrek K�ue 17.05.2012
 * 
 *         Authentication activity
 * 
 *         NB! I am handling confinguration changes my self (manifest >>
 *         activity >> android:configChanges="orientation" . Reason: cleaner
 *         code but removes the feature to have different styles for different
 *         configurations (portrait/land)
 */

public class MainActivity extends Activity {

	// public static long useId;
	public static final String CONSUMER_KEY = "yq8oFFmJ9NKG1BK7n2x7g";
	public static final String CONSUMER_SECRET_KEY = "NzdWHp5b1eLF6i9m8oitYzecQrGWx0vp0SsvsDdQQ";
	// public static final String ACCESS_TOKEN =
	// "582704981-BRu4uemtTW5DXQWoM9kPM9rTsQtckWDwA8HiijCO";
	// public static final String ACCESS_TOKEN_SECRET =
	// "G7f4CjfI82ybi1Mmnq73ex7dcTf3xF6n2EQjdjcU";

	public static Twitter twitter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		genReusableTwitterObject();

		if (Auth.loadAccessToken(this, 0) != null) {
			startActivity(new Intent(this, FrontPageActivity.class));
		} else {
			openWebViewForPin();
			bindAuthPinButton();
		}

	}

	/**
	 * Generates twitter object to be used all over the app If user is
	 * authenticated, receives token from pref and continues session
	 */
	private void genReusableTwitterObject() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET_KEY);

		// if user is authenticated- continue session
		if (Auth.loadAccessToken(this, 0) != null) {
			cb.setOAuthAccessToken(Auth.loadAccessToken(this, 0).getToken());
			cb.setOAuthAccessTokenSecret(Auth.loadAccessToken(this, 0)
					.getTokenSecret());
		}

		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

	}

	/**
	 * Opens webview for user for authentication where user receives pin (needed
	 * for receiving access token)
	 */
	private void openWebViewForPin() {
		try {
			Toast.makeText(
					this,
					"Please authenticate yourself. When you have received the pin, return to the app",
					Toast.LENGTH_LONG).show();

			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitter
					.getOAuthRequestToken().getAuthorizationURL())));
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * PIN verification button logic
	 */
	private void bindAuthPinButton() {
		((Button) findViewById(R.id.button1))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						String pin = ((EditText) findViewById(R.id.editText1))
								.getText().toString();

						new AuthenticateAsync(MainActivity.this).execute(pin);
						// AccessToken accessToken = null;
						// while (accessToken == null) {
						// try {
						// accessToken = twitter.getOAuthAccessToken(pin);
						// } catch (TwitterException e) {
						// if (401 == e.getStatusCode())
						// Log.e("MY",
						// "Unable to get the access token."
						// + e.toString());
						// e.printStackTrace();
						// }
						//
						// }
						//
						// Log.d("MY", "TOKEN RECEIVED: " +
						// accessToken.getToken()
						// + ":" + accessToken.getTokenSecret());
						// twitter.setOAuthAccessToken(accessToken);
						//
						// // save token
						// Auth.storeAccessToken(MainActivity.this,
						// accessToken.getUserId(), accessToken);
						//
						// startActivity(new Intent(MainActivity.this,
						// FrontPageActivity.class));

					}
				});
	}

}