package ee.mobi.proov;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Indrek Kõue 17.05.2012
 * 
 *         NB! I am handling confinguration changes my self (manifest >>
 *         activity >> android:configChanges="orientation" . Reason: cleaner
 *         code but removes the feature to have different styles for different
 *         configurations (portrait/land)
 */

public class LoginActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// String name = ((EditText) findViewById(R.id.editText1)).getText()
		// .toString();
		try {
			String pass = ((EditText) findViewById(R.id.editText2)).getText()
					.toString();

			// The factory instance is re-useable and thread safe.
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("yq8oFFmJ9NKG1BK7n2x7g",
					"NzdWHp5b1eLF6i9m8oitYzecQrGWx0vp0SsvsDdQQ");
			RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			while (null == accessToken) {
				// System.out
				// .println("Open the following URL and grant access to your account:");
				// System.out.println(requestToken.getAuthorizationURL());
				// System.out
				// .print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
				// String pin = br.readLine();
				try {
					if (pass.length() > 0) {
						accessToken = twitter.getOAuthAccessToken(requestToken,
								pass);
					} else {
						accessToken = twitter.getOAuthAccessToken();
					}
				} catch (TwitterException te) {
					if (401 == te.getStatusCode()) {
						Toast.makeText(getBaseContext(),
								"Unable to get the access token.",
								Toast.LENGTH_LONG).show();
					} else {
						te.printStackTrace();
					}

				}

			}

			// persist to the accessToken for future reference.
			try {
				storeAccessToken(twitter.verifyCredentials().getId(),
						accessToken);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Status status = twitter.updateStatus(args[0]);
			// System.out.println("Successfully updated the status to [" +
			// status.getText() + "].");
			// System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void storeAccessToken(long useId, AccessToken accessToken) {

		Editor e = PreferenceManager.getDefaultSharedPreferences(
				LoginActivity.this).edit();

		e.putLong("useId", useId);
		e.putString("token", accessToken.getToken());
		e.putString("tokenSecret", accessToken.getTokenSecret());

		e.commit();

		// store accessToken.getToken()
		// store accessToken.getTokenSecret()
	}

	private AccessToken loadAccessToken(int useId) {

		String token = PreferenceManager.getDefaultSharedPreferences(
				LoginActivity.this).getString("token", "EMPTY");

		String tokenSecret = PreferenceManager.getDefaultSharedPreferences(
				LoginActivity.this).getString("tokenSecret", "EMPTY");

		return new AccessToken(token, tokenSecret);

	}

}