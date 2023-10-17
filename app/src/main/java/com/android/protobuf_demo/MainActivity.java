package com.android.protobuf_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.protobuf_demo.databinding.ActivityMainBinding;
import com.google.protobuf.InvalidProtocolBufferException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.serializeBtn.setOnClickListener(this);
        binding.deserializeBtn.setOnClickListener(this);
    }

    private int dataLength;
    private final byte[] buffer = new byte[2048];

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.serialize_btn) {
            PersonModel.Person person = PersonModel
                    .Person
                    .newBuilder()
                    .setName("liyiwei")
                    .setAge(33)
                    .addHobbies("tt")
                    .addHobbies("yy")
                    .build();
            byte[] bytes_data = person.toByteArray();
            dataLength = bytes_data.length;
            System.arraycopy(bytes_data, 0, buffer, 0, dataLength);

            Log.e(TAG, "序列化结果: " + StringUtil.byteArrayToHexStr(bytes_data));
            System.out.println();
        } else if (vId == R.id.deserialize_btn) {
            byte[] data = new byte[dataLength];
            System.arraycopy(buffer, 0, data, 0, dataLength);
            try {
                PersonModel.Person person = PersonModel.Person.parseFrom(data);
                String string = person.toString();
                Log.e(TAG, "反序列化结果：" + string);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        }
    }
}