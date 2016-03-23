package com.example.javerbukh.myfirstapp;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;


/**
 * Created by javerbukh on 3/21/16 Using code from EnvatoTuts coding tutorials
 */
public class DoodleView extends View {

    private Paint paintDoodle = new Paint();
    private Paint paintCanvas = new Paint();
    private Path path = new Path();
    private int paintColor = 0xFF660000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float brushSize, lastBrushSize;
    private int paintAlpha = 255;

    public DoodleView(Context context) {
        super(context);
        init(null,0);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle){
        paintDoodle.setColor(Color.RED);
        paintDoodle.setAntiAlias(true);
        paintDoodle.setStyle(Paint.Style.STROKE);
        paintDoodle.setStrokeJoin(Paint.Join.ROUND);
        paintDoodle.setStrokeCap(Paint.Cap.ROUND);
        paintDoodle.setStrokeWidth(brushSize);

        paintCanvas = new Paint(Paint.DITHER_FLAG);
        paintCanvas.setColor(Color.BLUE);
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //canvas.drawLine(0, 0, getWidth(), getHeight(), paintDoodle);
        canvas.drawBitmap(canvasBitmap, 0, 0, paintCanvas);
        canvas.drawPath(path, paintDoodle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(path, paintDoodle);
                path.reset();
                break;
            default:
                return false;

        }

        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        paintDoodle.setColor(paintColor);

    }
    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        paintDoodle.setStrokeWidth(brushSize);
    }
    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }


    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void changeBackground(int color){
        drawCanvas.drawColor(color);
        invalidate();
    }

    public int getPaintAlpha(){
        return Math.round((float)paintAlpha/255*100);
    }
    public void setPaintAlpha(int newAlpha) {
        paintAlpha=Math.round((float)newAlpha / 100 * 255);
        paintDoodle.setColor(paintColor);
        paintDoodle.setAlpha(paintAlpha);
    }
}
