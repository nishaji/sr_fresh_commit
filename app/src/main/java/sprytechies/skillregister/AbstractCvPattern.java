package sprytechies.skillregister;

import com.itextpdf.text.BaseColor;

/**
 * Created by sprydev5 on 29/7/16.
 */
public abstract class AbstractCvPattern {
    private String headerSrc;
    private String lineSrc;
    private BaseColor subTitleColor;
    private BaseColor textColor;

    public String getHeaderSrc() {
        return this.headerSrc;
    }

    public void setHeaderSrc(String headerSrc) {
        this.headerSrc = headerSrc;
    }

    public String getLineSrc() {
        return this.lineSrc;
    }

    public void setLineSrc(String lineSrc) {
        this.lineSrc = lineSrc;
    }

    public BaseColor getSubTitleColor() {
        return this.subTitleColor;
    }

    public void setSubTitleColor(BaseColor subTitleColor) {
        this.subTitleColor = subTitleColor;
    }

    public BaseColor getTextColor() {
        return this.textColor;
    }

    public void setTextColor(BaseColor textColor) {
        this.textColor = textColor;
    }
}

