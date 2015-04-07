package com.xiamubobby.justlauncher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import parsii.eval.Expression;
import parsii.eval.Scope;

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
    protected HashMap<String, Object> variables = new HashMap<>();
    public TextView Logger;

    @Override
    protected void onDraw(Canvas canvas) {
        if (!initialed) {
            initiate();
        }
        super.onDraw(canvas);
//        canvas.drawRGB((int) (255 * currentFrame),
//                (int) (255 * currentFrame),
//                (int) (255 * currentFrame));
    }

    private void initiate() {
        if (animator == null) {
            animator = new JustCauseAnimator();
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
        Expression expr;
        HashMap<String, Object> variables;
        private AnimationValueCalculator() {
            this.scope = Scope.create();
            this.expr = null;
        }
    }
}
