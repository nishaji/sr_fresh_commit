package sprytechies.skillregister.ui.experience;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;
import sprytechies.skillregister.data.remote.JsonParse;
import sprytechies.skillregister.data.remote.remote_model.JobSugession;


public class JobSuggessionAdapter extends ArrayAdapter<String> {

    private List<String> suggestions;

    public JobSuggessionAdapter(Activity context, String nameFilter) {
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
                    List<JobSugession> new_suggestions = jp.getParseJsonJob(constraint.toString());
                    suggestions.clear();
                    for (int i = 0; i < new_suggestions.size(); i++) {
                        suggestions.add(new_suggestions.get(i).getItem());
                    }
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
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
