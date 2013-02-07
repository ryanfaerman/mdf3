package com.nwitty.android.quotebook;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AddQuote extends Activity {
	String _quote = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_quote);
		
		final View quoteField = findViewById(R.id.quote_field);
	    try {
	    	Bundle data = getIntent().getExtras();
	    	_quote = data.getString(android.content.Intent.EXTRA_TEXT);
	    	((TextView) quoteField).setText(_quote);
	    	
	    	findViewById(R.id.cancel_button).setOnClickListener(cancelQuoteClickListener);
	    	findViewById(R.id.save_button).setOnClickListener(saveQuoteClickListener);
	    	
	    } catch(Exception e) {
	        Log.e("ERROR", "SOMETHING BROKE");
	        Log.e("ERROR", e.toString());
	        finish();
	        return;
	    }
	}
	
	View.OnClickListener saveQuoteClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.i("TRACE", "saving quote");
			
			ContentValues values = new ContentValues();
			values.put(QuoteDb.KEY_QUOTE, _quote);
			getContentResolver().insert(QuoteProvider.CONTENT_URI, values);
	        
			finish();
			
		}
	};
	
	View.OnClickListener cancelQuoteClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.i("TRACE", "canceling quote");

			finish();
			
		}
	};
}
