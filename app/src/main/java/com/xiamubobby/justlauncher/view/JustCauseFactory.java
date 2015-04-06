package com.xiamubobby.justlauncher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by MuSTERMIND on 2015/4/6.
 */
public class JustCauseFactory {
    private Context context;
    private JustCauseFactory(Context context) {
        this.context = context;
    }
    public static JustCauseFactory create(Context context) {
        return new JustCauseFactory(context);
    }
    public JustCauseFactory eat(JustCauseBean justCauseBean) {

        return this;
    }
    public JustCauseBase makeFrom(final JustCauseBean bean) {
        String name = bean.name;
        Integer vWidth = bean.viewport.width;
        Integer vHeight = bean.viewport.height;
        JustCauseBase ret = new JustCauseBase(context) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Paint paint = new Paint();
                for (JustCauseBean.Draw temp : bean.draws) {
                    switch (temp.type) {
                        case "argb":
                            assert (temp.values.size() >= 4);
                            canvas.drawARGB((Integer) temp.values.get(0),
                                    temp.values.get(1),
                                    temp.values.get(2),
                                    temp.values.get(3));
                            break;
                        case "rect" :
                            assert (temp.values.size() >= 4);
                            paint.setARGB(0, 255, 0, 0);
                            canvas.drawRect(temp.values.get(0), temp.values.get(1), temp.values.get(2), temp.values.get(3), paint);
                            break;
                    }
                }
            }
        };
        return ret;
    }
}
