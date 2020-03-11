package com.xts.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xts.shop.common.Constant;
import com.xts.shop.utils.LogUtils;

public class TestActivity extends AppCompatActivity {

    private ImageView mIv;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        mTv = (TextView) findViewById(R.id.tv);

        String data = getIntent().getStringExtra(Constant.DATA);
        LogUtils.print("base64:" + data);
        if (!TextUtils.isEmpty(data)) {
            byte[] decode = Base64.decode(data, Base64.DEFAULT);

            int size = decode.length;
            byte[] lg = new byte[4];
            System.arraycopy(decode, size - 4, lg, 0, 4);
            int bitmapSize = byte2Int(lg);

            byte[] bitmapBytes = new byte[bitmapSize];
            System.arraycopy(decode, 0, bitmapBytes, 0, bitmapSize);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapSize);
            mIv.setImageBitmap(bitmap);

            int strLength = decode.length - 4 - bitmapSize;
            if (strLength > 0) {
                byte[] str = new byte[strLength];
                System.arraycopy(decode,bitmapSize,str,0,strLength);
                String s = new String(str);
                mTv.setText(s);

            }
        }
    }

    private int byte2Int(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;

        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * i);
        }
        return iOutcome;
    }
}
