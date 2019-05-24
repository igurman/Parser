package com.gurman;

class Modify {

    private String pattern;

    Modify(String pattern) {
        this.pattern = pattern;
    }

    synchronized String getText(String res) {
        if (!pattern.isEmpty()) {
            String[] replaces = pattern.split("#");
            String b = (replaces.length == 1) ? "" : replaces[1];
            return res.replaceAll(replaces[0], b);
        }
        return res;
    }
}
