package sprytechies.skillregister.ui.library;

/**
 * Created by sprydev5 on 16/11/16.
 */
import android.graphics.RectF;

abstract class BaseRenderer {
    private final RectF drawingArea;
    private final FitChartValue value;

    FitChartValue getValue() {
        return value;
    }

    RectF getDrawingArea() {
        return drawingArea;
    }

    public BaseRenderer(RectF drawingArea, FitChartValue value) {
        this.drawingArea = drawingArea;
        this.value = value;
    }
}
