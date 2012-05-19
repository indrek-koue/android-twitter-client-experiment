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
import ee.mobi.proov.async.Authenticate;
import ee.mobi.proov.util.Auth;

/**
 * @author Indrek Kõue 17.05.2012
 * 
 *         NB! I am handling confinguration changes my self (manifest >>
 *         activity >> android:configChanges="orientation" . Reason: cleaner
 *         code but removes the feature to have different styles for different
 *         configurations (portrait/land)
 */

public class LoginActivity extends Activity {

	public static long useId;
	public static final String CONSUMER_KEY = "yq8oFFmJ9NKG1BK7n2x7g";
	public static final String CONSUMER_SECRET_KEY = "NzdWHp5b1eLF6i9m8oitYzecQrGWx0vp0SsvsDdQQ";
	public static final String ACCESS_TOKEN = "582704981-BRu4uemtTW5DXQWoM9kPM9rTsQtckWDwA8HiijCO";
	public static final String ACCESS_TOKEN_SECRET = "G7f4CjfI82ybi1Mmnq73ex7dcTf3xF6n2EQjdjcU";

	public static Twitter twitter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET_KEY)
		// .setOAuthAccessToken(ACCESS_TOKEN)
		// .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET)
		;
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		//
		// startActivity(new Intent(this, MainActivity.class));
		//
		//
		try {
			// Twitter twitter = new TwitterFactory().getInstance();

			// twitter.setOAuthConsumer(LoginActivity.CONSUMER_KEY,
			// LoginActivity.CONSUMER_SECRET_KEY);

			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitter
					.getOAuthRequestToken().getAuthorizationURL())));

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		((Button) findViewById(R.id.button1))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						String pin = ((EditText) findViewById(R.id.editText1))
								.getText().toString();

						Log.i("MY", "pin: " + pin
								+ " START ASYNC for accesstoken");

						// new Authenticate(LoginActivity.this)
						// .execute(((EditText) findViewById(R.id.editText1))
						// .getText().toString());
						AccessToken accessToken = null;
						while (accessToken == null) {
							try {
								// accessToken = twitter.getOAuthAccessToken(
								// twitter.getOAuthRequestToken(), pin);
								//
								accessToken = twitter.getOAuthAccessToken(pin);
							} catch (TwitterException e) {
								if (401 == e.getStatusCode())
									Log.e("MY",
											"Unable to get the access token."
													+ e.toString());
								e.printStackTrace();
							}

//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
						}

						Log.d("MY", "TOKEN RECEIVED: " + accessToken.getToken()
								+ ":" + accessToken.getTokenSecret());

						Auth.storeAccessToken(LoginActivity.this,
								accessToken.getUserId(), accessToken);

						twitter.setOAuthAccessToken(accessToken);

						startActivity(new Intent(LoginActivity.this,
								MainActivity.class));

					}
				});

	}

}