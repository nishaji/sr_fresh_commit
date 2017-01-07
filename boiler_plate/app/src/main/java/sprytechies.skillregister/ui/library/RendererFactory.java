package sprytechies.skillregister.ui.library;

/**
 * Created by sprydev5 on 16/11/16.
 */

import android.graphics.RectF;

class RendererFactory {
    public static Renderer getRenderer(AnimationMode mode, FitChartValue value, RectF drawingArea) {
        if (mode == AnimationMode.LINEAR) {
            return new LinearValueRenderer(drawingArea, value);
        } else {
            return new OverdrawValueRenderer(drawingArea, value);
        }
    }
}
