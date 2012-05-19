package ee.mobi.proov;

import java.util.ArrayList;
import java.util.List;

import ee.mobi.proov.async.TimelineAsync;
import ee.mobi.proov.async.TweetAsync;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * User home activity
 * 
 * @author Indrek Kõue
 * 
 */

public class FrontPageActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		new TimelineAsync(this).execute();

		bindTweetButton();
		bindSearchButton();

	}

	/**
	 * Redirects user to search page
	 */
	private void bindSearchButton() {
		((Button) findViewById(R.id.button2))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						startActivity(new Intent(FrontPageActivity.this,
								SearchActivity.class));
					}
				});
	}

	/**
	 * Functionality to tweet button
	 */
	private void bindTweetButton() {

		((Button) findViewById(R.id.button1))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						// get tweet msg
						String msg = ((EditText) findViewById(R.id.editText1))
								.getText().toString();

						if (!msg.equals("")) {
							new TweetAsync(FrontPageActivity.this).execute(msg);
						} else {
							Toast.makeText(getBaseContext(),
									"Empty tweet not allowed",
									Toast.LENGTH_LONG).show();

						}
					}
				});

	}

}
