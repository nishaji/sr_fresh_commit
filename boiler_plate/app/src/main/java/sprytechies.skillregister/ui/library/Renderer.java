package sprytechies.skillregister.ui.library;

/**
 * Created by sprydev5 on 16/11/16.
 */

import android.graphics.Path;

interface Renderer {
    Path buildPath(float animationProgress, float animationSeek);
}
