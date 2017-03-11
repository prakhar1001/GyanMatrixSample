package prakhar.com.gyanmatrixsample.CustomView;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by lendingkart on 2/25/2017.
 */
public class FontCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }
}