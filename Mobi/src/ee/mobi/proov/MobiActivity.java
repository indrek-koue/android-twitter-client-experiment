package ee.mobi.proov;

import android.app.Activity;
import android.os.Bundle;


/**
 * 
 * @author Indrek Kõue
 * 
 *
 */

public class MobiActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}