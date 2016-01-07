package com.jfsiot.touchselect.touchselecttest.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jfsiot.touchselect.touchselecttest.R;
import com.jfsiot.touchselect.touchselecttest.Toolbar.OnToolbarAction;
import com.jfsiot.touchselect.touchselecttest.activity.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SSS on 2015-12-27.
 */
public class DefaultFragment extends Fragment implements OnToolbarAction {
    @Bind(R.id.paste_edit) protected EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uneditable, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setClickable(false);
        editText.setCursorVisible(false);
        ((MainActivity) getActivity()).getToolbar().setTitle(R.string.nav_drawer_default);
    }

    @Override
    public void OnToolbarAction(int action) {
        if (action == 2) init();

    }
    public void init(){
        String text = "";
        for(String str : ((MainActivity) getActivity()).getTextSourse(0)){
            text += str;
        }
        this.editText.setText(text);
        editText.setTextIsSelectable(true);

        if(((MainActivity) getActivity()).getCurrentTextStatue() == 1) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.setSpan(new UnderlineSpan(), 97, 100, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new UnderlineSpan(), 188, 196, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new UnderlineSpan(), 324, 409, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new UnderlineSpan(), 467, 531, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new UnderlineSpan(), 606, 835, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new UnderlineSpan(), 839, 918, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            this.editText.setText(builder);
        }else if(((MainActivity) getActivity()).getCurrentTextStatue() == 0){
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.setSpan(new UnderlineSpan(), 92, 116, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new StyleSpan(Typeface.BOLD | Typeface.ITALIC), 259, 284, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new UnderlineSpan(), 207, 328, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            this.editText.setText(builder);
        }
    }
}
