package com.zwb.next.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.view.View;

public class SpectrumView extends View {
    private int[] colors = new int[]{-65536, -33024, -256, -16711936, -16711681, -16776961, -7667457};
    private byte[] mBytes;
    private float[] mPoints;
    private Rect mRect = new Rect();
    private Paint mPaint = new Paint();
    private Visualizer visualizer;
    private int times = 3;

    public SpectrumView(Context context, int audioSessionId) {
        super(context);
        this.setupVisualizer(audioSessionId);
        this.mPaint.setStrokeWidth(1.0F);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.colors[(int)(Math.random() * 7.0D)]);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void setupVisualizer(int audioSessionId) {
        this.visualizer = new Visualizer(audioSessionId);
        this.visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        this.visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                SpectrumView.this.updateVisualizer(waveform);
            }

            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);
        this.visualizer.setEnabled(true);
    }

    public void updateVisualizer(byte[] mbyte) {
        this.mBytes = mbyte;
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(-1);
        if (this.times < 0) {
            this.mPaint.setColor(this.colors[(int)(Math.random() * 7.0D)]);
            this.times = 3;
        } else {
            --this.times;
        }

        if (this.mBytes != null) {
            if (this.mPoints == null || this.mPoints.length < this.mBytes.length * 4) {
                this.mPoints = new float[this.mBytes.length * 4];
            }

            this.mRect.set(0, 0, this.getWidth(), this.getHeight());

            for(int i = 0; i < this.mBytes.length - 1; ++i) {
                this.mPoints[i * 4] = (float)(this.mRect.width() * i / (this.mBytes.length - 1));
                this.mPoints[i * 4 + 1] = (float)(this.mRect.height() / 2 + (byte)(this.mBytes[i] + 128) * (this.mRect.height() / 2) / 128);
                this.mPoints[i * 4 + 2] = (float)(this.mRect.width() * (i + 1) / (this.mBytes.length - 1));
                this.mPoints[i * 4 + 3] = (float)(this.mRect.height() / 2 + (byte)(this.mBytes[i + 1] + 128) * (this.mRect.height() / 2) / 128);
            }

            canvas.drawLines(this.mPoints, this.mPaint);
        }
    }
}