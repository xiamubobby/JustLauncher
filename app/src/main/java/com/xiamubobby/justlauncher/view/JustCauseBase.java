package com.xiamubobby.justlauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;
import parsii.tokenizer.ParseException;

/**
 * Created by MuSTERLING on 2015/3/26.
 */
public class JustCauseBase extends View {
    public JustCauseBase(Context context) {
        super(context);
    }

    public JustCauseBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JustCauseBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JustCauseBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private boolean initialed = false;
    private float currentFrame = 0;
    private JustCauseAnimator animator;
    protected List<String> animationVarNames = new ArrayList<>();
    protected HashMap<String, Float> animationVars = new HashMap<>();
    protected AnimationValueCalculator animationValueCalculator;
    protected JustCauseCanvasDrawer justCauseCanvasDrawer;
    public TextView Logger;

    @Override
    protected void onDraw(Canvas canvas) {
        if (!initialed) {
            initiate(canvas);
        }
        super.onDraw(canvas);
    }

    protected void setUpAniVars(List<String> varNames) {
        animationVarNames = varNames;
        for (String vn : varNames) {
            animationVars.put(vn, 250f);
        }
    }

    private void initiate(Canvas canvas) {
        if (animator == null) {
            animator = new JustCauseAnimator();
        }
        if (animationValueCalculator == null) {
            animationValueCalculator = new AnimationValueCalculator();
        }
        if (justCauseCanvasDrawer == null) {
            justCauseCanvasDrawer = new JustCauseCanvasDrawer(canvas);
        }
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Class jcb = JustCauseAnimator.class;
                Method med = null;
                try {
                    med = jcb.getDeclaredMethod("light");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    med.invoke(JustCauseBase.this.animator);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                //startDrag(null, new DragShadowBuilder(JustCauseBase.this), null, 0);
                //invalidate();
                return false;
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //animator.light();
                        break;
                    case MotionEvent.ACTION_UP:
                        //animator.delight();
                        break;
                }
                return false;
            }
        });
        setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Logger.setText(event.getAction()+"");
                Log.v("drag e", event.getAction() + "");
                return true;
            }
        });
        initialed = true;
    }

    private class JustCauseAnimator {
        private ValueAnimator animator = null;

        protected void stopAndPurge() {
            if (animator != null) {
                if (animator.isRunning()) {
                    animator.pause();
                    animator.removeAllUpdateListeners();
                }
            }
        }
        protected void light() {
            stopAndPurge();
            animator = ValueAnimator.ofFloat(currentFrame, 0.5f);
            animator.setDuration(100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentFrame = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
        protected void delight() {
            stopAndPurge();
            animator = ValueAnimator.ofFloat(currentFrame, 0f);
            animator.setDuration(100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentFrame = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }

    protected class AnimationValueCalculator {
        Scope scope;
        Double pureNum;
        Expression expr;
        double doubleResult;
        AnimationValueCalculator() {
            this.scope = Scope.create();
            updateParams();
        }
        void updateParams() {
            for (String vn : animationVarNames) {
                scope.getVariable(vn).setValue(animationVars.get(vn));
            }
        }
        AnimationValueCalculator setExpr(String expr) {
            try {
                pureNum = Double.parseDouble(expr);
                System.out.println("pureNum");
            }
            catch(Exception e){
                try {
                    this.expr = Parser.parse(expr, scope);
                    System.out.println("complicatedExpr");
                } catch (ParseException e1) {
                    System.out.println("whatRUTrying2Do!");
                }
            }
            return this;
        }
        AnimationValueCalculator calc() {

            if (pureNum != null) {
                doubleResult = pureNum;
            }
            else {
                doubleResult = expr.evaluate();
            }
            return this;
        }
        AnimationValueCalculator calc(String expr) {
            return calc(expr, false);
        }
        AnimationValueCalculator calc(String expr, boolean updateParamsOrNot) {
            if (updateParamsOrNot) {
                updateParams();
            }
            setExpr(expr);
            calc();
            return this;
        }
        float asFloat() {
            return (float) doubleResult;
        }
        int asInt() {
            return (int) doubleResult;
        }
    }
    protected class JustCauseCanvasDrawer {
        private Canvas canvas;
        JustCauseCanvasDrawer(Canvas canvas) {
            this.canvas = canvas;
        }
        void drawARGB(List<String> components) {
            assert(components.size() >= 4);
            canvas.drawARGB(
                    animationValueCalculator.calc(components.get(0)).asInt(),
                    animationValueCalculator.calc(components.get(1)).asInt(),
                    animationValueCalculator.calc(components.get(2)).asInt(),
                    animationValueCalculator.calc(components.get(3)).asInt()
            );
        }

        void drawCircle(List<String> components, Paint paint) {
            assert(components.size() >= 3);
            canvas.drawCircle(
                    animationValueCalculator.calc(components.get(0)).asFloat(),
                    animationValueCalculator.calc(components.get(1)).asFloat(),
                    animationValueCalculator.calc(components.get(2)).asFloat(),
                    paint
            );
        }
        void drawRect(List<String> components, Paint paint) {
            assert(components.size() >= 4);
            canvas.drawRect(
                    animationValueCalculator.calc(components.get(0)).asFloat(),
                    animationValueCalculator.calc(components.get(1)).asFloat(),
                    animationValueCalculator.calc(components.get(2)).asFloat(),
                    animationValueCalculator.calc(components.get(3)).asFloat(),
                    paint
            );
        }
    }

}
