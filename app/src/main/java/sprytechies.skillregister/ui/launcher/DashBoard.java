package sprytechies.skillregister.ui.launcher;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import sprytechies.skillregister.R;
import sprytechies.skillregister.ui.library.FitChart;
import sprytechies.skillregister.ui.library.FitChartValue;

public class DashBoard extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.dashboard_fragment, null);
        final FitChart fitChart = (FitChart)x.findViewById(R.id.fitChart);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(100f);
        Resources resources = getResources();
        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue(30f, resources.getColor(R.color.blue_focused)));
        values.add(new FitChartValue(20f, resources.getColor(R.color.blue)));
        values.add(new FitChartValue(15f, resources.getColor(R.color.style_color_primary_dark)));
        values.add(new FitChartValue(10f, resources.getColor(R.color.yellow)));
        fitChart.setValues(values);
        return x;
    }
}
