package com.jfsiot.touchselect.touchselecttest.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jfsiot.touchselect.touchselecttest.R;
import com.jfsiot.touchselect.touchselecttest.customview.SelectableEditText;
import com.jfsiot.touchselect.touchselecttest.helper.TextOffsetHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SSS on 2015-12-27.
 */
public class WordFragment extends Fragment implements View.OnTouchListener {
    @Bind(R.id.main_edit) protected SelectableEditText editText;
    @Bind(R.id.main_container) protected RelativeLayout container;

    private List<Integer> indexList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this, view);

        String text = "";
        for(String str : getResources().getStringArray(R.array.word_text)){
            text += str;
            indexList.add(text.length());
        }
        this.editText.setText(text);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.editText.setOnTouchListener(this);
        free();
    }

    public void free(){
        posDownX = -1;
        posDownY = -1;
        this.editText.setTextIsSelectable(false);
        this.editText.clearFocus();
    }

    private int posDownX, posDownY;
    private float initScrollY;
    private long downTime, tabTime = 0;
    private int MAX_SCROLLABLE_Y_POSITION = -1;
    private float posDownRawY, posDownRawX;
    private int defaultTop = -1;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int location[] = new int[2];
        if(defaultTop < 0){
            defaultTop = ((int) editText.getY());
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            this.downTime = event.getEventTime();
            this.posDownX = ((int) event.getX());
            this.posDownY = ((int) event.getY()+editText.getScrollY());
            this.posDownRawX = event.getRawX();
            this.posDownRawY = event.getRawY();
            this.initScrollY = editText.getScrollY();
            MAX_SCROLLABLE_Y_POSITION =  TextOffsetHelper.getViewTotalHeight(this.editText) - editText.getHeight();
        }
        float diff = Math.abs(posDownX - event.getX() + posDownY - event.getY() - editText.getScrollY());
        float diffRaw = (float) Math.sqrt( Math.abs( Math.pow(posDownRawX - event.getRawX(), 2) + Math.pow(posDownRawY - event.getRawY(), 2) ) );
        float diffRawY = posDownRawY - event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_MOVE && Math.abs(diffRawY) > 10) {
            if(((int) (diffRawY + initScrollY)) > MAX_SCROLLABLE_Y_POSITION) {
                this.editText.scrollTo(0, Math.max(MAX_SCROLLABLE_Y_POSITION, 0));
            }else if(((int) (diffRawY + initScrollY)) < 0){
                this.editText.scrollTo(0, 0);
            }else{
                this.editText.scrollTo(0, ((int) (diffRawY + initScrollY)));
            }
        }


        if (editText.hasSelection()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
            }else if (event.getAction() == MotionEvent.ACTION_UP && diffRaw < 20) {
                if(event.getEventTime() - downTime < 150){
                    TextOffsetHelper.getPositionLineOffset(location, this.editText, posDownX, posDownY);
                    int offset, fixOffset;
                    if(tabTime != 0 && event.getEventTime() - tabTime < 200){
                        tabTime = 0;
                        this.free();
                    }else {
                        tabTime = event.getEventTime();

                        /**
                         * Change selection range
                         */
                        float centerOfSelection =  editText.getSelectionStart() + (editText.getSelectionEnd() - editText.getSelectionStart()) / 2;
                        if (location[1] <= centerOfSelection) {
                            offset = TextOffsetHelper.getOffsetTextList(indexList, location[1], true, editText.getText().length());
                            fixOffset = editText.getSelectionEnd();
                            if(Math.abs(editText.getSelectionStart() - offset) > 1) editText.setSelection(offset, fixOffset);
                        } else if (location[1] > centerOfSelection) {
                            offset = TextOffsetHelper.getOffsetTextList(indexList, location[1], false, editText.getText().length());
                            fixOffset = editText.getSelectionStart();
                            if(Math.abs(editText.getSelectionEnd() - offset) > 1)  editText.setSelection(fixOffset, offset);
                        }
                    }
                    return true;
                }
            }
            return true;


        }else{
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                editText.setFocusable(true);
            }
            if (event.getEventTime() - downTime > 150 || diffRaw > 20) {
                free();
            }
            if(event.getAction() == MotionEvent.ACTION_MOVE) {
                if(diffRaw< 20) {
                    editText.setTextIsSelectable(true);
                }else{
                    free();
                }
            }else if (event.getAction() == MotionEvent.ACTION_UP) {
                if(diffRaw < 20) {
                    TextOffsetHelper.getPositionLineOffset(location, this.editText, ((int) event.getX()), ((int) event.getY() + editText.getScrollY()));
                    int start = TextOffsetHelper.getOffsetTextList(indexList, location[1], true, editText.getText().length() )
                            , end = TextOffsetHelper.getOffsetTextList(indexList, location[1], false, editText.getText().length());
                    editText.setSelection(start, end);
                    return true;
                }else{
                    return true;
                }
            }
        }
        return false;
    }
}
