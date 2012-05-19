package ee.mobi.proov;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		initSearchButton();
		initCancleButton();

	}

	private void initCancleButton() {
		((Button) findViewById(R.id.button1))
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						finish();
					}
				});
	}

	/**
	 * Search button functionality
	 */
	private void initSearchButton() {
		((Button) findViewById(R.id.button2))
				.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						
						String msg = ((EditText) findViewById(R.id.editText1))
								.getText().toString();

						// Twitter twitter = new TwitterFactory().getInstance();
						Query query = new Query(msg);

						try {
							QueryResult result = MainActivity.twitter
									.search(query);

							// copy messages to string list
							List<String> msgs = new ArrayList<String>();
							for (Tweet tweet : result.getTweets())
								msgs.add(tweet.getFromUser() + ":"
										+ tweet.getText());

							// find listview and add string list to it
							ListView lv = ((ListView) findViewById(R.id.listView1));
							lv.setAdapter(new ArrayAdapter<String>(
									SearchActivity.this,
									android.R.layout.simple_list_item_1, msgs));

						} catch (TwitterException e) {
							Toast.makeText(getBaseContext(),
									"Error: " + e.toString(), Toast.LENGTH_LONG)
									.show();
							e.printStackTrace();
						}

					}
				});
	}

}
