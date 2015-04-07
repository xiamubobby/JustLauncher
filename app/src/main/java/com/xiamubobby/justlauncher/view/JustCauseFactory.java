package com.xiamubobby.justlauncher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;
import parsii.eval.Variable;

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
                            assert (temp.values.size() >= 4);
//                            canvas.drawARGB(temp.values.get(0).intValue(),
//                                    temp.values.get(1).intValue(),
//                                    temp.values.get(2).intValue(),
//                                    temp.values.get(3).intValue());
                            break;
                        case "rect" :
                            assert (temp.values.size() >= 4);
//                            canvas.drawRect(temp.values.get(0),
//                                    temp.values.get(1),
//                                    temp.values.get(2),
//                                    temp.values.get(3),
//                                    canvasPaint);
                            break;
                        case "circle":
                            assert (temp.values.size() >= 3);
                            Scope scope = Scope.create();
                            scope.getVariable("offset").setValue(400);
                            Expression expr = null;
                            try {
                                expr = Parser.parse(temp.values.get(0), scope);
                            } catch (parsii.tokenizer.ParseException e) {
                                e.printStackTrace();
                            }
                            canvas.drawCircle((float) expr.evaluate()/viewportWidth*viewWidth,
                                    Float.valueOf(temp.values.get(1))/viewportWidth*viewWidth,
                                    Float.valueOf(temp.values.get(2))/viewportWidth*viewWidth,
                                    canvasPaint);
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
        for (String varName : bean.variables) {
            ret.variables.put(varName, 0f);
        }
        return ret;
    }

}
