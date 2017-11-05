package club.tranch.colltips;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;

public class PercentView extends View {

    private final static String TAG = PercentView.class.getSimpleName();
    private Paint circlePaint;//进度条背景画笔
    private Paint arcPaint;//进度条活动画笔
    private int backgroudColor;//背景颜色
    private int progressColor;//进度颜色
    private int borderSize;//进度条宽度
    private int value;//进度
    private float lastValue;
    private boolean isFullType = false;
    private RectF oval;//矩形
    private Timer timer;
    Thread thread;

    public boolean isFullType() {
        return isFullType;
    }

    public void setFullType(boolean fullType) {
        isFullType = fullType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public PercentView(Context context) {
        this(context, null, 0);
    }

    public PercentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initParams(context, attrs);
    }

    public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        oval = new RectF();
    }

    private void initParams(Context context, AttributeSet attrs) {
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentView);
        if (typedArray != null) {
            backgroudColor = typedArray.getColor(R.styleable.PercentView_background_color, Color.GRAY);
            progressColor = typedArray.getColor(R.styleable.PercentView_progress_color, Color.BLUE);
            value = typedArray.getInt(R.styleable.PercentView_value, 0);
            isFullType = typedArray.getBoolean(R.styleable.PercentView_isFullType, true);
            borderSize=typedArray.getInt(R.styleable.PercentView_borderSize,5);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint.setColor(backgroudColor);
        circlePaint.setShadowLayer(2, 5, 5, Color.argb(180, 180, 180, 180));
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
        circlePaint.setStyle(Paint.Style.FILL);
        int width = getWidth();
        int height = getHeight();
        int min;
        min = width < height ? width : height;
        Log.e(TAG, "onDraw---->" + width + "*" + height + ",value=" + value);
        float radius = width / 2 - 20;
        canvas.drawCircle(min / 2 - 10, min / 2 - 10, radius, circlePaint);
        arcPaint.setColor(progressColor);
        arcPaint.setStrokeWidth(borderSize);
        Paint.Style s = isFullType ? Paint.Style.FILL : Paint.Style.STROKE;
        arcPaint.setStyle(s);
        oval.set(min / 2 - 10 - radius, min / 2 - 10 - radius, min / 2 - 10 + radius, min / 2 - 10 + radius);//用于定义的圆弧的形状和大小的界限
        if (value != 0) {
            final float addnum = value - lastValue;
            final float num = addnum / 1000;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(10);
                                if(addnum>=0)
                                    lastValue += num*10;
                                else
                                    lastValue=0;
                                Log.i(TAG, "run: lastView:"+lastValue);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                canvas.drawArc(oval, 270, (float) (lastValue * 3.6), isFullType, arcPaint);  //根据进度画圆弧


        }


    }

}
