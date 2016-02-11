package edu.uw.motiondemo;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ButtonActivity extends AppCompatActivity {

    private static final String TAG = "Button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        final Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Clicked!");
                ObjectAnimator xAnim = ObjectAnimator.ofFloat(button, "alpha", 0.0f);
                xAnim.setDuration(1000);
                xAnim.start();
            }
        });
    }
}
