package com.xiamubobby.justlauncher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;

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
        final Integer viewportWidth = bean.viewport.width;
        final Integer viewportHeight = bean.viewport.height;
        JustCauseBase ret = new JustCauseBase(context) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                final int viewWidth = getWidth();
                final int viewHeight = getHeight();
                Paint canvasPaint;
                for (JustCauseBean.Draw temp : bean.draws) {
                    canvasPaint = (temp.paint == null) ? (new Paint()) : makePaint(temp.paint);
                    switch (temp.type) {
                        case "argb":
                            //canvas.drawARGB(255, 128, 30, 68);
                            justCauseCanvasDrawer.drawARGB(temp.values);
                            break;
                        case "rect" :
                            justCauseCanvasDrawer.drawRect(temp.values, canvasPaint);
                            break;
                        case "circle":
                            justCauseCanvasDrawer.drawCircle( temp.values, canvasPaint);
                            break;
                    }
                }
            }

            private Paint makePaint(JustCauseBean.Draw.Paint beanPaint) {
                Paint retPaint = new Paint();
                if (beanPaint.argb != null) {
                    List<String> beanPaintARGB = beanPaint.argb;
                    retPaint.setARGB(Integer.valueOf(beanPaintARGB.get(0)),
                            Integer.valueOf(beanPaintARGB.get(0)),
                            Integer.valueOf(beanPaintARGB.get(0)),
                            Integer.valueOf(beanPaintARGB.get(0)));
                }
                if (beanPaint.paintstyle != null) {
                    retPaint.setStyle(Paint.Style.valueOf(beanPaint.paintstyle));
                }
                return retPaint;
            }
        };
        ret.setUpAniVars(bean.variables);
        return ret;
    }

}
