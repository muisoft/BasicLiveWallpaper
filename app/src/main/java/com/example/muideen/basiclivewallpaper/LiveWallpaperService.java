package com.example.muideen.basiclivewallpaper;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Handler;
import android.os.IBinder;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

public class LiveWallpaperService extends WallpaperService {
    int x,y;
    public LiveWallpaperService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new LiveWallpaperEgine();
    }

    private class LiveWallpaperEgine extends Engine{
        private boolean mVisible;
        public Bitmap mBackground,mImage;

        private final int frameDuration=20;
        private Movie movie;
       // private SurfaceHolder surfaceHolder;
        private final Handler mHandler=new Handler();
        private final Runnable mRunnable=new Runnable() {
            @Override
            public void run() {
                 draw();
            }
        };
        public LiveWallpaperEgine() {
            Resources resources=getApplicationContext().getResources();
           mBackground= BitmapFactory.decodeResource(resources,R.drawable.autumnleaves);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
           this.mVisible=visible;
            if(mVisible){
                mHandler.post(mRunnable);
            }else {
                mHandler.removeCallbacks(mRunnable);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(mRunnable);
        }



        private void draw(){
            final SurfaceHolder surfaceHolder=getSurfaceHolder();
            Canvas canvas=null;
            try {
                canvas=surfaceHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                if(canvas!=null){
                    canvas.drawBitmap(mBackground,0,0,null);
                    int width=canvas.getWidth();
                }
            }finally {
                if(canvas!=null){
                   surfaceHolder.unlockCanvasAndPost(canvas);
                }
                mHandler.removeCallbacks(mRunnable);
                if(mVisible){
                    mHandler.postDelayed(mRunnable,10);
                }
            }

        }
        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }
    }
}
