package ee.mobi.proov;

import ee.mobi.proov.async.Authenticate;
import ee.mobi.proov.util.Auth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
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

	public static long useId;
	public static final String CONSUMER_KEY = "yq8oFFmJ9NKG1BK7n2x7g";
	public static final String CONSUMER_SECRET_KEY = "NzdWHp5b1eLF6i9m8oitYzecQrGWx0vp0SsvsDdQQ";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(LoginActivity.CONSUMER_KEY,
					LoginActivity.CONSUMER_SECRET_KEY);
			RequestToken requestToken = twitter.getOAuthRequestToken();

			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken
					.getAuthorizationURL())));

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		((Button) findViewById(R.id.button1))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						new Authenticate(LoginActivity.this)
								.execute(((EditText) findViewById(R.id.editText1))
										.getText().toString());

					}
				});
	}
}