package com.xiamubobby.justlauncher.view;

import java.util.List;

/**
 * Created by MuSTERMIND on 2015/4/5.
 */
public class JustCauseBean {
    String name = "";
    ViewPort viewport = null;
    List<String> variables = null;
    List<Draw> draws = null;

    public class ViewPort {
        Integer width;
        Integer height;
    }

    public class Draw {
        String type;
        List<Object> values;
        Paint paint;
        public class Paint {
            List<Object> argb;
            Float strokewidth;
            String paintstyle;
            Integer paintcap;
            Integer paintjoin;
        }
    }


}
