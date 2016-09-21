package com.example.sprydev5.skillsregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * This activity displays some help information on a topic.
 * It displays some text and provides a way to get back to the home activity.
 * The text to be displayed is determined by the text id argument passed to the activity.
 *
 */

public class TopicActivity extends Activity
{

    int mTextResourceId = 0;

    /**
     * onCreate
     *
     * @param savedInstanceState Bundle
     */

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.topic);

        // Read the arguments from the Intent object.
        Intent in = getIntent ();
        mTextResourceId = in.getIntExtra (HelpFragment.ARG_TEXT_ID, 0);
        if (mTextResourceId <= 0) mTextResourceId = R.string.no_help_available;

        TextView textView = (TextView) findViewById (R.id.topic_text);
        textView.setMovementMethod (LinkMovementMethod.getInstance());
        textView.setText (Html.fromHtml (getString (mTextResourceId)));
    }

} // end class

