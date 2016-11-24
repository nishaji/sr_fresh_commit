package sprytechies.skillregister.ui.education;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import sprytechies.skillregister.data.remote.JsonParse;
import sprytechies.skillregister.data.remote.remote_model.CourseSugeeion;


/**
 * Created by sprydev5 on 6/11/16.
 */

public class CourseSuggessionAdapter extends ArrayAdapter<String> {

    protected static final String TAG = "CourseSugessionAdapter";
    private List<String> suggestions;

    public CourseSuggessionAdapter(Activity context, String nameFilter) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        suggestions = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int index) {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                JsonParse jp = new JsonParse();
                if (constraint != null) {
                    // A class that queries a web API, parses the data and
                    // returns an ArrayList<GoEuroGetSet>
                    List<CourseSugeeion> new_suggestions = jp.getParseJsonCourse(constraint.toString());
                    suggestions.clear();
                    for (int i = 0; i < new_suggestions.size(); i++) {
                        suggestions.add(new_suggestions.get(i).getItem());
                        System.out.println(suggestions.add(new_suggestions.get(i).getItem())+"fsdfdsfsdfdsfsdf");
                    }

                    // Now assign the values and count to the FilterResults
                    // object
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    System.out.println(suggestions.size()+"ghghghghgh");
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }

}


