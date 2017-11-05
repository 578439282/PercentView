package club.tranch.colltips;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int FLAG=0;
    private Timer timer;
    private PercentView pv;
    Button btn,btn2,btnValue;
    TextInputEditText et;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FLAG==0){
                    Toast.makeText(context,"开始",Toast.LENGTH_SHORT).show();
                    timer=new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if(pv.getValue()<=100)
                                pv.setValue(pv.getValue()+1);
                            else
                                pv.setValue(0);
                        }
                    },0,10);
                    FLAG=1;
                }else{
                    Toast.makeText(context,"停止",Toast.LENGTH_SHORT).show();
                    FLAG=0;
                    timer.cancel();
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pv.setFullType(!pv.isFullType());
            }
        });

        btnValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=Integer.parseInt(et.getText().toString());
                pv.setValue(value);
            }
        });

    }

    private void initView() {
        btn= (Button) findViewById(R.id.btn);
        pv= (PercentView) findViewById(R.id.pv);
        btn2= (Button) findViewById(R.id.btn2);
        btnValue= (Button) findViewById(R.id.btnValue);
        et= (TextInputEditText) findViewById(R.id.et);

    }
}
