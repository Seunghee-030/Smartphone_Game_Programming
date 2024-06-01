package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;
import android.graphics.RectF;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
public class Item extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float GRAVITY = 2.0f; // 중력 가속도
    private static final float SPEED = 2.0f;
    private static final float RADIUS = 0.6f;
    private static final int[] resIds = {
            R.mipmap.candy };

    public static final int MAX_LEVEL = resIds.length - 1;
    public static final float ANIM_FPS = 10.0f;
    protected RectF collisionRect = new RectF();
    private int level;
    private int life, maxLife;

    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    private Item(int level, int index) {
        super(0, 0);
        init(level, index);
        dy = SPEED;
    }

    private void init(int level, int index) {
        this.level = level;
        this.life = this.maxLife = (level + 1) * 10;
        setAnimationResource(resIds[level], ANIM_FPS);
        setPosition(Metrics.width / 2, -RADIUS, RADIUS); // 화면 가운데 맨 위에서 생성

        dy = SPEED; // 초기 속도 설정
    }

    public static Item get(int level, int index) {
        Item item = (Item) RecycleBin.get(Item.class);
        if (item != null) {
            item.init(level, index);
            return item;
        }
        return new Item(level, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        // 중력에 따른 속도 변경
        dy += GRAVITY * elapsedSeconds;

        // 공의 위치 변경
        //ballY += dy * elapsedSeconds;

        // X축 경계에 닿을 때 속도 반전
     if (dx > 0 && dstRect.right > Metrics.width) {
            // 오른쪽 벽에 닿았을 때
            posX = Metrics.width - dstRect.width(); // 화면 안으로 이동
            dx = -dx; // X축 속도 반전
        } else if (dx < 0 && dstRect.left < 0) {
            // 왼쪽 벽에 닿았을 때
            posX = 0; // 화면 안으로 이동
            dx = -dx; // X축 속도 반전
        }

        // Y축 경계에 닿을 때 속도 반전
        if (dy > 0 && dstRect.bottom > Metrics.height) {
            // 아래쪽 벽에 닿았을 때
            posY = Metrics.height - dstRect.height(); // 화면 안으로 이동
            dy = -dy; // Y축 속도 반전
        } else if (dy < 0 && dstRect.top < 0) {
            // 위쪽 벽에 닿았을 때
            posY = 0; // 화면 안으로 이동
            dy = -dy; // Y축 속도 반전
        }


        // 아이템과 몬스터의 충돌 감지
        MainScene scene = (MainScene) Scene.top();
        if (scene != null) {
            Monster monster = scene.getMonster(); // 몬스터 인스턴스 가져오기 (이를 위해 MainScene 클래스에 getMonster() 메소드가 필요)
            if (monster != null && isCollidingWith(monster)) {
                //System.out.println("충돌! - 아이템, 몬스터");
                //scene.remove(item, this);
                //scene.addScore(100000);
                // 아이템 물리 구현을 위해 잠시 주석 처리
            }

            Bouncer bouncer = scene.getBouncer();
            if (bouncer != null && isCollidingWith(bouncer)) {
                System.out.println("충돌! - Item, Bouncer");
                float bouncerLeft = bouncer.getCollisionRect().right;
                float bouncerRight = bouncer.getCollisionRect().left;
                float bouncerTop = bouncer.getCollisionRect().top;
                float bouncerBottom = bouncer.getCollisionRect().bottom;
                //BounceBall(0,100,300,101);

                // 가로
                // 왼쪽 측면에 닿았을 때
                if (dx > 0 && dstRect.right > bouncerLeft) {
                    posX = bouncerLeft - dstRect.width(); // 화면 안으로 이동
                    dx = -dx; // X축 속도 반전
                }
                // 오른쪽 측면에 닿았을 때
                else if (dx < 0 && dstRect.left < bouncerRight) {

                    posX = bouncerRight; // 화면 안으로 이동
                    dx = -dx; // X축 속도 반전
                }

                // 세로
                // 위쪽에 닿았을 때
                if (dy > 0 && dstRect.bottom > bouncerTop) {
                    posY = bouncerTop - dstRect.height(); // 화면 안으로 이동
                    dy = -dy; // Y축 속도 반전
                }
                // 아래쪽에 닿았을 때
                else if (dy < 0 && dstRect.top < bouncerBottom) {
                    posY = bouncerBottom; // 화면 안으로 이동
                    dy = -dy; // Y축 속도 반전
                }

            }
        }
    }

    public void BounceBall(float x, float y, float x1, float y1) {
        // Ball position

        // Ball direction

        // Block position and size
        int blockX = 100;
        int blockY = 100;
        int blockSize = 50;

            while (true) {
               /* if (ballX + dx < 0 || ballX + dx > dstRect.width() - 30) dx = -dx;
                if (ballY + dy < 0 || ballY + dy > dstRect.height() - 50) dy = -dy;
                // Check if the ball hits the block
                if (ballX + dx >= blockX && ballX + dx <= blockX + blockSize &&
                        ballY + dy >= blockY && ballY + dy <= blockY + blockSize) {
                    dx = -dx;
                    dy = -dy;
                }
                ballX += dx;
                ballY += dy;*/

                // X축 경계에 닿을 때 속도 반전
                if (dx > 0 && dstRect.right > Metrics.width) {
                    // 오른쪽 벽에 닿았을 때
                    posX = Metrics.width - dstRect.width(); // 화면 안으로 이동
                    dx = -dx; // X축 속도 반전
                } else if (dx < 0 && dstRect.left < 0) {
                    // 왼쪽 벽에 닿았을 때
                    posX = 0; // 화면 안으로 이동
                    dx = -dx; // X축 속도 반전
                }

                // Y축 경계에 닿을 때 속도 반전
                if (dy > 0 && dstRect.bottom > Metrics.height) {
                    // 아래쪽 벽에 닿았을 때
                    posY = Metrics.height - dstRect.height(); // 화면 안으로 이동
                    dy = -dy; // Y축 속도 반전
                } else if (dy < 0 && dstRect.top < 0) {
                    // 위쪽 벽에 닿았을 때
                    posY = 0; // 화면 안으로 이동
                    dy = -dy; // Y축 속도 반전
                }


            }


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();

        float width = dstRect.width() * 0.7f;
        canvas.translate(posX - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        //gauge.draw(canvas, (float)life / maxLife);
        canvas.restore();
    }

    public boolean isCollidingWith(Monster monster) {
        RectF itemRect = getCollisionRect();
        RectF monsterRect = monster.getCollisionRect();
        return RectF.intersects(itemRect, monsterRect);
    }
    public boolean isCollidingWith(Bouncer bouncer) {
        RectF itemRect = getCollisionRect();
        RectF bouncerRect = bouncer.getCollisionRect();
        return RectF.intersects(itemRect, bouncerRect);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }

    public int getScore() {
        return (level + 1) * 100;
    }

    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }
}