package ee.mobi.proov.async;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import ee.mobi.proov.FrontPageActivity;
import ee.mobi.proov.MainActivity;
import ee.mobi.proov.R;
import ee.mobi.proov.SearchActivity;
import ee.mobi.proov.util.Auth;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Search asyncronously
 * 
 * @author indrek
 * 
 */
public class SearchAsync extends AsyncTask<String, String, List<String>> {

	private Activity a;
	private ProgressDialog dialog;

	public SearchAsync(Activity a) {
		this.a = a;
	}

	@Override
	protected void onPreExecute() {

		dialog = ProgressDialog.show(a, "", "Loading. Please wait...", true);
		dialog.show();

		super.onPreExecute();
	}

	@Override
	protected List<String> doInBackground(String... params) {

		Query query = new Query(params[0]);
		List<String> msgs = new ArrayList<String>();
		try {

			QueryResult result = MainActivity.twitter.search(query);

			// copy messages to string list
			for (Tweet tweet : result.getTweets())
				msgs.add(tweet.getFromUser() + ":" + tweet.getText());

		} catch (TwitterException e) {
			Toast.makeText(a.getBaseContext(), "Error: " + e.toString(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		return msgs;

	}

	@Override
	protected void onPostExecute(List<String> result) {

		// find listview and add string list to it
		ListView lv = ((ListView) a.findViewById(R.id.listView1));
		lv.setAdapter(new ArrayAdapter<String>(a,
				android.R.layout.simple_list_item_1, result));

		dialog.dismiss();

	}

}
