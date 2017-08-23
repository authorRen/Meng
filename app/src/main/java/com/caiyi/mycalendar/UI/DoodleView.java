package com.caiyi.mycalendar.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ren ZeQiang.
 * @since 2017/8/23.
 */

public class DoodleView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;

    private Paint mPaint;
    //默认画笔粗细为5
    private int currentSize = 5;
    //默认画笔颜色为黑色
    private int currentColor = Color.BLACK;

    private BaseAction curAction = null;

    private Bitmap bitmap;

    private List<BaseAction> mActions;

    private ActionType mActionType = ActionType.Path;

    public DoodleView(Context context) {
        super(context);
        init();
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        mSurfaceHolder = getHolder();
        //注册回调方法
        mSurfaceHolder.addCallback(this);
        //设置参数方便后面绘制
        setFocusable(true);
        setFocusableInTouchMode(true);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(currentSize);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        mActions = new ArrayList<>();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL) {
            return false;
        }
        float touchX = event.getRawX();
        float touchY = event.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setCurtAction(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                for (BaseAction mAction : mActions) {
                    mAction.draw(canvas);
                }
                curAction.move(touchX, touchY);
                curAction.draw(canvas);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                break;
            case MotionEvent.ACTION_UP:
                mActions.add(curAction);
                curAction = null;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 得到当前画笔的类型，并进行实例化
     *
     * @param x
     * @param y
     */
    private void setCurtAction(float x, float y) {
        switch (mActionType) {
            case Point:
                curAction = new MyPoint(x, y, currentColor);
                break;
            case Path:
                curAction = new MyPath(x, y, currentSize, currentColor);
                break;
            case Line:
                curAction = new MyLine(x, y, currentSize, currentColor);
                break;
            case Rect:
                curAction = new MyRect(x, y, currentSize, currentColor);
                break;
            case Circle:
                curAction = new MyCircle(x, y, currentSize, currentColor);
                break;
            case FillEcRect:
                curAction = new MyFillRect(x, y, currentSize, currentColor);
                break;
            case FilledCircle:
                curAction = new MyFillCircle(x, y, currentSize, currentColor);
                break;
            default:
                break;
        }
    }

    public enum ActionType {
        Point, Path, Line, Rect, Circle, FillEcRect, FilledCircle
    }

    /**
     * 设置画笔颜色
     * @param color
     */
    public void setColor(String color) {
        this.currentColor = Color.parseColor(color);
    }

    /**
     * 设置画笔粗细
     * @param size
     */
    public void setSize(int size) {
        this.currentSize = size;
    }

    /**
     * 设置画笔类型
     * @param type
     */
    public void setType(ActionType type) {
        this.mActionType = type;
    }

    /**
     * 将当前的画布转换为Bitmap
     * @return
     */
    public Bitmap getBitmap() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        doDraw(canvas);
        return bitmap;
    }

    /**
     * 开始进行绘画
     * @param canvas
     */
    private void doDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        for (BaseAction action : mActions) {
            action.draw(canvas);
        }
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }

    /**
     * 保存涂鸦后的图片
     *
     * @param doodleView
     * @return 保存图片的路径
     */
    public String saveBitmap(DoodleView doodleView) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/doodleView/" + System.currentTimeMillis() + ".png";
        if (!new File(path).exists()) {
            new File(path).getParentFile().mkdir();
        }
        savePicByPNG(doodleView.getBitmap(), path);
        return path;
    }

    /**
     * 将一个bitmap保存到指定的路径中
     *
     * @param bitmap
     * @param path
     */
    private void savePicByPNG(Bitmap bitmap, String path) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(path);
            if (null != fileOutputStream) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回退
     *
     * @return 是否已经回退成功
     */
    public boolean back(){
        if(mActions != null && mActions.size() > 0){
            mActions.remove(mActions.size() -1);
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BaseAction action : mActions) {
                action.draw(canvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            return true;
        }
        return false;
    }

    /**
     * 重置签名
     */
    public void reset(){
        if(mActions != null && mActions.size() > 0){
            mActions.clear();
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BaseAction action : mActions) {
                action.draw(canvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}

