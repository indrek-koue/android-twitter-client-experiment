package ee.mobi.proov;

import java.util.ArrayList;
import java.util.List;

import ee.mobi.proov.async.SearchAsync;

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

/**
 * Search activity
 * 
 * @author Indrek Kõue
 * 
 */
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

						new SearchAsync(SearchActivity.this).execute(msg);

					}
				});
	}

}
