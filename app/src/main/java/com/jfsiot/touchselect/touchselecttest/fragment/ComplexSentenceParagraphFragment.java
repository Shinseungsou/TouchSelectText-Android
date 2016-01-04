package com.jfsiot.touchselect.touchselecttest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jfsiot.touchselect.touchselecttest.R;
import com.jfsiot.touchselect.touchselecttest.activity.MainActivity;
import com.jfsiot.touchselect.touchselecttest.helper.TextOffsetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSS on 2015-12-27.
 */
public class ComplexSentenceParagraphFragment extends SelectableFragment implements View.OnTouchListener {
    private List<Integer> sentenceIndexList;


    @Override
    protected void init() {
        super.init();
        this.sentenceIndexList = new ArrayList<>();

        String text = "";
        for(String str : ((MainActivity) getActivity()).getTextSourse(1)){
            text += str;
            sentenceIndexList.add(text.length());
        }
        free();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        ((MainActivity) getActivity()).getToolbar().setTitle(R.string.nav_drawer_group_complex_sentence_paragraph);
    }

    @Override
    protected String[] getStringArrayResourse() {
        return ((MainActivity) getActivity()).getTextSourse(2);
    }

    @Override
    protected void touchInner(boolean isLeft, int letterOffset) {
        int offset, fixOffset;
        if(isLeft) {
            offset = TextOffsetHelper.getOffsetTextList(sentenceIndexList, letterOffset, true, editText.getText().toString());
            fixOffset = editText.getSelectionEnd();
            if (Math.abs(editText.getSelectionStart() - offset) > 1) editText.setSelection(offset, fixOffset);
        }else{
            offset = TextOffsetHelper.getOffsetTextList(sentenceIndexList, letterOffset, false, editText.getText().toString());
            fixOffset = editText.getSelectionStart();
            if(Math.abs(editText.getSelectionEnd() - offset) > 1)  editText.setSelection(fixOffset, offset);
        }
    }

    @Override
    protected void touchOuter(boolean isLeft, int letterOffset) {
        super.touchOuter(isLeft, letterOffset);
    }
}
