package com.jfsiot.touchselect.touchselecttest.customview;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/**
 * Created by SSS on 2015-12-29.
 */
public class SelectionContextualActionBar extends ActionMode {

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public void setTitle(int resId) {

    }

    @Override
    public void setSubtitle(CharSequence subtitle) {

    }

    @Override
    public void setSubtitle(int resId) {

    }

    @Override
    public void setCustomView(View view) {

    }

    @Override
    public void invalidate() {

    }

    @Override
    public void finish() {

    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }

    @Override
    public CharSequence getSubtitle() {
        return null;
    }

    @Override
    public View getCustomView() {
        return null;
    }

    @Override
    public MenuInflater getMenuInflater() {
        return new MenuInflater(getCustomView().getContext());
    }
}
