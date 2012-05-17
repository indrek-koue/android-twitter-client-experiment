package ee.mobi.proov.util;

import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import ee.mobi.proov.LoginActivity;

public class Auth {

	public static void storeAccessToken(Activity a, long useId,
			AccessToken accessToken) {

		Editor e = PreferenceManager.getDefaultSharedPreferences(a).edit();

		e.putLong("useId", useId);
		e.putString("token", accessToken.getToken());
		e.putString("tokenSecret", accessToken.getTokenSecret());

		Log.i("MY", "save token to memory: " + accessToken.getToken() + " : "
				+ accessToken.getTokenSecret());
		e.commit();

		// store accessToken.getToken()
		// store accessToken.getTokenSecret()
	}

	public static AccessToken loadAccessToken(Activity a, long useId) {

		String token = PreferenceManager.getDefaultSharedPreferences(a)
				.getString("token", "EMPTY");

		String tokenSecret = PreferenceManager.getDefaultSharedPreferences(a)
				.getString("tokenSecret", "EMPTY");

		Log.i("MY", "get token from memory: " + token + " : " + tokenSecret);

		return new AccessToken(token, tokenSecret);

	}

}
