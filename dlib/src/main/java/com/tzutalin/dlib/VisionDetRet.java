/*
*  Copyright (C) 2015 TzuTaLin
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.tzutalin.dlib;

/**
 * Created by Tzutalin on 2015/10/20.
 */

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;
import android.util.Log;
import java.util.Arrays;
/**
 * A VisionDetRet contains all the information identifying the location and confidence value of the detected object in a bitmap.
 */
public final class VisionDetRet {

    private static final String TAG = "dlib";

    private String mLabel;
    private float mConfidence;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    private ArrayList<Point> mLandmarkPoints = new ArrayList<>();
    private ArrayList<float[]> mDescriptors = new ArrayList<>();
    private ArrayList<int[]> mAlignedImages = new ArrayList<>();

    VisionDetRet() {
    }

    /**
     * @param label      Label name
     * @param confidence A confidence factor between 0 and 1. This indicates how certain what has been found is actually the label.
     * @param l          The X coordinate of the left side of the result
     * @param t          The Y coordinate of the top of the result
     * @param r          The X coordinate of the right side of the result
     * @param b          The Y coordinate of the bottom of the result
     */
    public VisionDetRet(String label, float confidence, int l, int t, int r, int b) {
        mLabel = label;
        mLeft = l;
        mTop = t;
        mRight = r;
        mBottom = b;
        mConfidence = confidence;
    }

    /**
     * @return The X coordinate of the left side of the result
     */
    public int getLeft() {
        return mLeft;
    }

    /**
     * @return The Y coordinate of the top of the result
     */
    public int getTop() {
        return mTop;
    }

    /**
     * @return The X coordinate of the right side of the result
     */
    public int getRight() {
        return mRight;
    }

    /**
     * @return The Y coordinate of the bottom of the result
     */
    public int getBottom() {
        return mBottom;
    }

    /**
     * @return A confidence factor between 0 and 1. This indicates how certain what has been found is actually the label.
     */
    public float getConfidence() {
        return mConfidence;
    }

    /**
     * @return The label of the result
     */
    public String getLabel() {
        return mLabel;
    }

    /**
     * Add landmark to the list. Usually, call by jni
     * @param x Point x
     * @param y Point y
     * @return true if adding landmark successfully
     */
    public boolean addLandmark(int x, int y) {
        return mLandmarkPoints.add(new Point(x, y));
    }

    public boolean addDescriptor(float[] descriptor) {
        Log.d(TAG, "Descriptor added: " + Arrays.toString(descriptor));
//        mDescriptors.add(descriptor);
        return true;
    }

    public boolean addAlignedImage(int[] alignedImage) {
        Log.d(TAG, "Aligned Image added: " + alignedImage[0]);
        mAlignedImages.add(alignedImage);
        return true;
    }

    /**
     * Return the list of landmark points
     * @return ArrayList of android.graphics.Point
     */
    public ArrayList<Point> getFaceLandmarks() {
        return mLandmarkPoints;
    }

    public ArrayList<float[]> getDescriptor() {
        return mDescriptors;
    }

    public int alignedImagesSize() {
        return mAlignedImages.size();
    }

    public Bitmap getAlignedImage(int index) {
        int imageSize = 224;
        int[] mAlignedImage = mAlignedImages.get(index);
        int[] colors = new int[imageSize * imageSize];
        for (int i = 0; i < imageSize * imageSize; i++) {
            int r = i * 3;
            int g = i * 3 + 1;
            int b = i * 3 + 2;

//            int r = 255;
//            int g = 0;
//            int b = 0;
//
//            if (i % 150 == 0) {
//                g = 255;
//            }

            colors[i] = Color.rgb(mAlignedImage[r], mAlignedImage[g], mAlignedImage[b]);
        }
        Bitmap image = Bitmap.createBitmap(colors, imageSize, imageSize, Bitmap.Config.RGB_565);

        return image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Left:");
        sb.append(mLabel);
        sb.append(", Top:");
        sb.append(mTop);
        sb.append(", Right:");
        sb.append(mRight);
        sb.append(", Bottom:");
        sb.append(mBottom);
        sb.append(", Label:");
        sb.append(mLabel);
        return sb.toString();
    }
}
