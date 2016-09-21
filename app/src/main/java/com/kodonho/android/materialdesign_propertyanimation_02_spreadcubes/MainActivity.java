package com.kodonho.android.materialdesign_propertyanimation_02_spreadcubes;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

/*
    큐브를 펼치면서 회전시키는 애니메이션
 */
public class MainActivity extends AppCompatActivity {

    // 동작 버튼
    Button btnSpread;
    // 큐브를 감싸고 있는 레이아웃
    RelativeLayout ground;
    // 큐브들
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpread = (Button) findViewById(R.id.btnSpread);
        ground = (RelativeLayout) findViewById(R.id.ground);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
    }

    // 현재 스프레드 상태인지를 확인하는 플래그
    boolean SPREADED = false;

    // 각각의 큐브를 펼치면서 회전
    public void spread(View v){
        if(!SPREADED) {
            move(btn1, -btn1.getWidth() / 2, -btn1.getHeight() / 2);
            move(btn2, btn2.getWidth() / 2, -btn2.getHeight() / 2);
            move(btn3, -btn3.getWidth() / 2, btn3.getHeight() / 2);
            move(btn4, btn4.getWidth() / 2, btn4.getHeight() / 2);

            // 전체 화면을 회전할때는 외곽의 layout 개체를 회전시킨다
            //rotate(ground,36000,300000);

            // 각각의 큐브를 회전시킬때는 각각의 큐브를 회전시킨다
            rotate(btn1,3600,10000);
            rotate(btn2,3600,10000);
            rotate(btn3,3600,10000);
            rotate(btn4,3600,10000);

            btnSpread.setText("Combine");
            SPREADED = true;
        }else{
            combine();
            btnSpread.setText("Spread");
            SPREADED = false;
        }
    }

    // 큐브를 모으면서 회전시키는 함수
    public void combine(){
        move(btn1, 0,0  );
        move(btn2, 0,0  );
        move(btn3, 0,0  );
        move(btn4, 0,0  );

        rotate(btn1,0,3000);
        rotate(btn2,0,3000);
        rotate(btn3,0,3000);
        rotate(btn4,0,3000);
    }

    // 타겟 개체를 이동시키는 함수
    public void move(View player, int x, int y){
        //              ↑타겟          ↑ 나로부터 이동할 거리 (최초 나의 위치는 0)
        ObjectAnimator ani1 = ObjectAnimator.ofFloat(player, "translationX", x);
        ObjectAnimator ani2 = ObjectAnimator.ofFloat(player, "translationY", y);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(3000);
        aniSet.playTogether(ani1,ani2);
        aniSet.setInterpolator(new AccelerateDecelerateInterpolator()); // 비선형 보간
        aniSet.start();
    }

    // 타겟 개체를 회전시키는 함수                     ↓ 회전하는 총 시간
    public void rotate(View target, int angle, int duration){
        //               ↑타겟          ↑ 회전각 (최초 각은 0 도)
        ObjectAnimator ani1 = ObjectAnimator.ofFloat(target, "rotation", angle);
        ani1.setDuration(duration);
        ani1.setInterpolator(new AccelerateDecelerateInterpolator()); // <- 비선형 동작 방식 지정 (pdf 참조)
        ani1.start();
    }
}
