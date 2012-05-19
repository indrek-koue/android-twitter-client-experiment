package ee.mobi.proov.async;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import ee.mobi.proov.FrontPageActivity;
import ee.mobi.proov.MainActivity;
import ee.mobi.proov.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Get timeline data from twitter and fills listview with it
 * @author indrek
 *
 */
public class TimelineAsync extends AsyncTask<String, String, String> {

	private Activity a;
	private ProgressDialog dialog;

	List<String> msgs;

	public TimelineAsync(Activity a) {
		this.a = a;
	}

	@Override
	protected void onPreExecute() {

		dialog = ProgressDialog.show(a, "", "Loading. Please wait...", true);
		dialog.show();

		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		try {

			// get timeline data from twitter
			List<twitter4j.Status> statuses = MainActivity.twitter
					.getHomeTimeline();

			// copy messages to string list
			msgs = new ArrayList<String>();
			for (twitter4j.Status status : statuses)
				msgs.add(status.getText());

		} catch (TwitterException e) {
			Toast.makeText(a.getBaseContext(),
					"Get timeline error: " + e.toString(), Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		// find listview and add string list to it
		ListView lv = ((ListView) a.findViewById(R.id.listView1));
		lv.setAdapter(new ArrayAdapter<String>(a,
				android.R.layout.simple_list_item_1, msgs));

		dialog.dismiss();

	}

}
