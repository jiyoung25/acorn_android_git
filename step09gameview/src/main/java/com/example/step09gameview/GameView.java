package com.example.step09gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {
    //필드
    Bitmap backImg; //배경 이미지
    int width, height;//즉 GameView가 차지하고 있는 화면의 폭과 높이

    //드래곤의 이미지를 저장할 배열
    Bitmap[] dragonImgs = new Bitmap[4];
    //드래곤 이미지 인덱스
    int dragonIndex=0;

    //유닛(드래곤, 적기)의 크기를 저장할 필드
    int unitSize;

    //드래곤의 좌표를 저장할 필드(가운데 기준)
    int dragonX, dragonY;

    //배경이미지의 y좌표
    int back1Y, back2Y;

    int count;

    //점수
    int point;

    //적기 이미지를 저장할 배열
    Bitmap[][] enemyImgs=new Bitmap[2][2];

    //Enemy 객체를 저장할 List
    List<Enemy> enemyList=new ArrayList<>();
    //적기의 x 좌표를 저장할 배열
    int[] enemyX=new int[5];

    //랜덤한 숫자를 얻어낼 Random 객체
    Random ran=new Random();

    //적기가 만들어진 이후 count
    int postCount;

    //미사일 객체를 저장할 list
    List<Missile> missList = new ArrayList<>();
    //미사일의 크기
    int missSize;
    //미사일 이미지를 담을 배열
    Bitmap[] missImgs = new Bitmap[3];
    //미사일의 속도
    int speedMissile;
    //효과음을 재생해주는 객체
    SoundManager soundManager;
    
    //생성자1
    public GameView(Context context) {
        super(context);
    }
    //생성자2
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //SoundManager객체를 전달받아서 필드에 저장하는 메소드
    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    //초기화 메소드
    public void init(){
        //원본 배경 이미지 읽어들이기
        Bitmap backImg = BitmapFactory.decodeResource(getResources(), R.drawable.backbg);
        //배경이미지를 view의 크기에 맞게 조절해서 필드에 저장
        this.backImg = Bitmap.createScaledBitmap(backImg, width, height, false);

        //드래곤 이미지를 로딩해서 사이즈를 조절하고 배열에 저장한다.
        Bitmap dragonImg1=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit1);
        Bitmap dragonImg2=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit2);
        Bitmap dragonImg3=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit3);
        dragonImg1=Bitmap
                .createScaledBitmap(dragonImg1, unitSize, unitSize, false);
        dragonImg2=Bitmap
                .createScaledBitmap(dragonImg2, unitSize, unitSize, false);
        dragonImg3=Bitmap
                .createScaledBitmap(dragonImg3, unitSize, unitSize, false);

        dragonImgs[0]=dragonImg1;
        dragonImgs[1]=dragonImg2;
        dragonImgs[2]=dragonImg3;
        dragonImgs[3]=dragonImg2;

        //미사일 이미지 로딩
        Bitmap missImg1=BitmapFactory.decodeResource(getResources(),
                R.drawable.mi1);
        Bitmap missImg2=BitmapFactory.decodeResource(getResources(),
                R.drawable.mi2);
        Bitmap missImg3=BitmapFactory.decodeResource(getResources(),
                R.drawable.mi3);
        //미사일 이미지 크기 조절
        missImg1=Bitmap.createScaledBitmap(missImg1,
                missSize, missSize, false);
        missImg2=Bitmap.createScaledBitmap(missImg2,
                missSize, missSize, false);
        missImg3=Bitmap.createScaledBitmap(missImg3,
                missSize, missSize, false);
        //미사일 이미지를 배열에 넣어두기
        missImgs[0]=missImg1;
        missImgs[1]=missImg2;
        missImgs[2]=missImg3;

        //적기 이미지 로딩
        Bitmap enemyImg1=BitmapFactory
                .decodeResource(getResources(), R.drawable.silver1);
        Bitmap enemyImg2=BitmapFactory
                .decodeResource(getResources(), R.drawable.silver2);
        Bitmap enemyImg3=BitmapFactory
                .decodeResource(getResources(), R.drawable.gold1);
        Bitmap enemyImg4=BitmapFactory
                .decodeResource(getResources(), R.drawable.gold2);

        //적기 이미지 사이즈 조절
        enemyImg1=Bitmap.createScaledBitmap(enemyImg1,
                unitSize, unitSize, false);
        enemyImg2=Bitmap.createScaledBitmap(enemyImg2,
                unitSize, unitSize, false);
        enemyImg3=Bitmap.createScaledBitmap(enemyImg3,
                unitSize, unitSize, false);
        enemyImg4=Bitmap.createScaledBitmap(enemyImg4,
                unitSize, unitSize, false);
        //적기 이미지 배열에 저장
        enemyImgs[0][0]=enemyImg1; //0행 0열 silver1
        enemyImgs[0][1]=enemyImg2; //0행 1열 silver2
        enemyImgs[1][0]=enemyImg3; //1행 0열 gold1
        enemyImgs[1][1]=enemyImg4; //1행 1열 gold2
        
        //적기의 x좌표를 구해서 배열을 저장한다.
        for(int i=0; i<5; i++){
            enemyX[i] = i*unitSize+unitSize/2;
        }

        //Handler 객체에 메시지를 보내서 화면이 주기적으로 갱신되는 구조로 바꾼다.
        handler.sendEmptyMessageDelayed(0,20);
    }

    //onSizeChanged method는 View가 활성화될 때 최초 한 번만 호출이 되고 View의 사이즈가 바뀌면 다시 호출된다.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //view가 차지하고 있는 폭과 높이가 px단위로 w,h에 전달이 된다.
        width=w;
        height=h;

        //unitSize는 화면 폭의 1/5로 설정한다.
        unitSize = w/5;

        //드래곤의 초기 좌표 부여
        dragonX = w/2;
        dragonY = height - unitSize/2;

        //배경이미지의 초기 좌표
        back1Y=0;
        back2Y=-height;
        
        //미사일의 크기는 드래곤 크기의 1/4로 한다.
        missSize = unitSize/4;

        //미사일의 속도는
        speedMissile = height/30;
        
        //초기화 메소드 호출
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // (0, 0)좌표에 배경 이미지 그리기(배경이미지는 2개이다.)
        canvas.drawBitmap(backImg, 0, back1Y, null);
        canvas.drawBitmap(backImg, 0, back2Y, null);

        //미사일 그리기
        for(Missile tmp:missList){
            canvas.drawBitmap(missImgs[0], tmp.x, tmp.y, null);
        }

        //적기 그리기
        for(Enemy tmp:enemyList){

            if(tmp.isFall){//추락 상태인 적기
                //canvas의 정상 상태 (변화를 가하기 전)를 임시로 저장한다.
                canvas.save();
                //적기의 위치로 평행이동
                canvas.translate(tmp.x, tmp.y);
                //적기 회전
                canvas.rotate(tmp.angle);
                //좀 더 줄어든 크기의 Bitmap image를 얻어서
                Bitmap scaled = Bitmap.createScaledBitmap(enemyImgs[tmp.type][tmp.imageIndex],
                        tmp.size, tmp.size, false);
                //적기를 원점에 그린다.
                canvas.drawBitmap(scaled, 50-unitSize/2, -unitSize/2, null);
                //저장했던 정상 상태도 되돌린다.
                canvas.restore();

            } else {//정상 상태의 적기
                //적기를 원점에 그린다.
                canvas.drawBitmap(enemyImgs[tmp.type][tmp.imageIndex], tmp.x-unitSize/2, tmp.y-unitSize/2, null);
            }

        }
        //점수 출력하기
        Paint textP = new Paint();
        textP.setColor(Color.YELLOW);
        textP.setTextSize(50);
        canvas.drawText("Score: "+point, 10, 60, textP);

        //드래곤 그리기
        canvas.drawBitmap(dragonImgs[dragonIndex/6], dragonX-unitSize/2, dragonY-unitSize/2, null);
        dragonIndex++;
        count++;
        if(dragonIndex==18){
            dragonIndex=0;
        }

        back1Y += 3;
        back2Y += 3;

        //만일 배경1의 좌표가 아래로 벗어나면
        if(back1Y >= height){
            //배경1을 상단으로 다시 보낸다.
            back1Y = -height;
            //배경2와 오차가 생기지 않게 하기 위해 복원하기
            back2Y=0;
        }
        //위와 같은 작업
        if(back2Y >= height){
            back2Y = -height;
            back1Y = 0;
        }
        missleService(); //미사일 관련 처리 메소드 호출
        enemyService();
        checkStrike();
    }

    //적기와 미사일의 충돌 검사 하기
    public void checkStrike(){
        for(int i=0; i<missList.size(); i++){
            //i번째 미사일 객체
            Missile m = missList.get(i);
            for(int j=0; j<enemyList.size(); j++){
                //j번째 적기 객체
                Enemy e = enemyList.get(j);
                //i번째 미사일이 j번째 적기의 사각형 영역 안에 있는지 여부 판별하기
                boolean isStrike = m.x > e.x - unitSize/2 &&
                        m.x < e.x + unitSize/2 &&
                        m.y > e.y - unitSize/2 &&
                        m.y < e.y +unitSize/2;

                if(isStrike && !e.isFall){//현재 추락 중인 적기는 무시하기
                    //효과음 재생
                    soundManager.playSound(GameActivity.SOUND_SHOOT);
                    //적기의 에너지를 줄이고
                    e.energy -=50;
                    //미사일을 없앤다
                    m.isDead = true;
                    //만일 적기의 에너지가 0이하가 된다면 적기를 없앤다.
                    if(e.energy<=0){
                        //e.isDead=true; 적기가 갑자기 사라지는 것이 아니라 추락된 이후에 제거되게 한다.
                        e.isFall = true; //바로 제거되는 대신 적기가 추락 상태가 되도록 한다.
                        //점수 올리기
                        point += e.type == 0 ? 100: 200;
                    }
                }
            }
        }
    }

    //미사일 관련 처리
    public void missleService(){
        //미사일 만들기
        //테스트로 미사일객체 한개를 배열에 넣어두기
        if(count%2 == 0) {
            //미사일 발사음 재생
            soundManager.playSound(GameActivity.SOUND_LAZER);
            missList.add(new Missile(dragonX-missSize/2, dragonY));
        }

        //미사일 움직이기
        for(Missile tmp:missList){
            tmp.y -= speedMissile;
            //만일 위쪽 화면을 벗어났다면
            if(tmp.y< -missSize/2){
                tmp.isDead = true; //배열에서 제거될 수 있도록 표시해 둔다.
            }
        }

        //미사일 객체를 모두 체크해서 배열에서 제거할 객체는 제거하기 (단, 반복문을 역순으로 돌아야 한다.)
        for(int i=missList.size()-1; i>=0; i--){
            //1번째 Missile 객체를 얻어와서
            Missile tmp = missList.get(i);
            // 만일 제거할 미사일 객체라면
            if(tmp.isDead){
                //리스트에서 i번 째 아이템을 제거한다.
                missList.remove(i);
            }
        }
    }

    //적기 관련 처리
    public void enemyService(){
        if(count%10==0){
            //반복문을 돌면서
            for(Enemy tmp:enemyList){
                //모든 적기의 이미지의 인덱스를 1 증가시킨다.
                tmp.imageIndex++;
                //만일 존재하지 않는 인덱스라면
                if(tmp.imageIndex==2){
                    //다시 처음으로 되돌린다.
                    tmp.imageIndex=0;
                }
            }
        }

        //임의의 시점에 적기가 5개 만들어지도록 해보세요.
        //테스트로 적기 5개 만들어보기
        postCount++;
        int ranNum = ran.nextInt(20);
        if(ranNum == 10 && postCount > 20){
            postCount=0;
            for(int i=0; i<5; i++) {
                Enemy tmp = new Enemy();
                tmp.x = enemyX[i]; //x좌표는 배열에 미리 준비된 x좌표
                tmp.y = unitSize / 2; //임시 y좌표
                tmp.type = ran.nextInt(2); //적기의 type은 0 or 1로 랜덤하게 부여
                tmp.energy = tmp.type == 0 ? 50 : 100;
                tmp.size = unitSize; //적기의 초기 크기
                //만든 적기를 적기 목록에 담기
                enemyList.add(tmp);
            }
        }

        //적 움직이기
        for(Enemy tmp:enemyList){
            if(tmp.isFall){
                //크기를 줄이고
                tmp.size -= 1;
                //회전값을 증가시킨다.
                tmp.angle +=10;
                //만일 크기가 0보다 작아진다면
                if(tmp.size <= 0){
                    //배열에서 제거될 수 있도록 표시한다.
                    tmp.isDead = true;
                }
            } else{
                //적기의 y좌표를 증가시키고
                tmp.y += speedMissile/2;
                //만일 아래쪽 화면을 벗어났다면
                if(height+unitSize/2 < tmp.y){
                    tmp.isDead = true; //배열에서 제거될 수 있도록 표시해 둔다.
                }
            }
        }
        for(int i=enemyList.size()-1; i>=0; i--){
            Enemy tmp = enemyList.get(i);
            if(tmp.isDead){
                enemyList.remove(i);
            }
        }
    }

    //view에 터치 이벤트가 발생하면 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragonX = (int)event.getX(); //event.getX()는 우리가 터치한 x좌표를 알려주고 float type으로 리턴된다.
        
        return true;
    }

    //Handler는 Android에만 있는 객체이다.
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //화면 갱신하기
            invalidate();
            //handler객체에 빈 메시지를 10/1000초 이후에 다시 보내기
            handler.sendEmptyMessageDelayed(0,20);
        }
    };
}
