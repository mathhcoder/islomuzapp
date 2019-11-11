package uz.islom.ui.custom;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

public class TrimmedTextView extends BaseTextView {

    public enum EllipsizeRange {
        ELLIPSIS_AT_START, ELLIPSIS_AT_END;
    }

    private CharSequence originalText;
    private SpannableStringBuilder builder = new SpannableStringBuilder();

    public TrimmedTextView(Context context) {
        this(context, null, 0);
    }

    public TrimmedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrimmedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TextWatcher textCacheInvalidator = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                originalText = null;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        addTextChangedListener(textCacheInvalidator);
    }

    public CharSequence getText() {
        if (originalText == null) {
            return super.getText();
        }
        return originalText;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        CharSequence text = layout.getText();
        if (text instanceof Spanned) {
            Spanned spanned = (Spanned) text;
            int ellipsisStart;
            int ellipsisEnd;
            TextUtils.TruncateAt where = null;
            ellipsisStart = spanned.getSpanStart(EllipsizeRange.ELLIPSIS_AT_START);
            if (ellipsisStart >= 0) {
                where = TextUtils.TruncateAt.START;
                ellipsisEnd = spanned.getSpanEnd(EllipsizeRange.ELLIPSIS_AT_START);
            } else {
                ellipsisStart = spanned.getSpanStart(EllipsizeRange.ELLIPSIS_AT_END);
                if (ellipsisStart >= 0) {
                    where = TextUtils.TruncateAt.END;
                    ellipsisEnd = spanned.getSpanEnd(EllipsizeRange.ELLIPSIS_AT_END);
                } else {
                    // No EllipsisRange spans in this text
                    return;
                }
            }

            builder.clear();
            builder.append(text, 0, ellipsisStart).append(text, ellipsisEnd, text.length());
            float consumed = Layout.getDesiredWidth(builder, layout.getPaint());
            CharSequence ellipsisText = text.subSequence(ellipsisStart, ellipsisEnd);
            CharSequence ellipsizedText = TextUtils.ellipsize(ellipsisText, layout.getPaint(),
                    layout.getWidth() - consumed, where);
            if (ellipsizedText.length() < ellipsisText.length()) {
                builder.clear();
                builder.append(text, 0, ellipsisStart).append(ellipsizedText)
                        .append(text, ellipsisEnd, text.length());
                setText(builder);
                originalText = text;
                requestLayout();
                invalidate();
            }
        }
    }
}