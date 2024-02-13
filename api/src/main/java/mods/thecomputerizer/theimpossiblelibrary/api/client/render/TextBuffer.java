package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.joml.Vector4i;

@Getter
public class TextBuffer {

    private final String text;
    private final int left;
    private final int bottom;
    private final int width;
    private final int height;
    private final float scaleX;
    private final float scaleY;
    private final int lineSpacing;
    private final int color;

    private TextBuffer(String text, int left, int bottom, int width, int height, float scaleX, float scaleY,
                       int spacing, int color) {
        this.text = text;
        this.left = left;
        this.bottom = bottom;
        this.width = width;
        this.height = height;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.lineSpacing = spacing;
        this.color = color;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static class Builder {

        private final String text;
        private int left;
        private int bottom;
        private int width;
        private int height;
        private float scaleX;
        private float scaleY;
        private int spacing;
        private int color;

        public Builder(String text) {
            this.text = text;
            this.scaleX = 1f;
            this.scaleY = 1f;
        }

        public TextBuffer build() {
            return new TextBuffer(this.text,this.left,this.bottom,this.width,this.height,this.scaleX,this.scaleY,
                    this.spacing,this.color);
        }

        public Builder setBottom(int bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder setBottomLeft(Vector2i bottomLeft) {
            return setBottomLeft(bottomLeft.x,bottomLeft.y);
        }

        public Builder setBottomLeft(int bottom, int left) {
            this.bottom = bottom;
            this.left = left;
            return this;
        }

        /**
         * Assumes values from 0-1
         */
        public Builder setColor(Vector4f color) {
            return setColor(ColorHelper.makeRGBAInt(color));
        }

        /**
         * Assumes values from 0-1
         */
        public Builder setColor(float r, float g, float b, float a) {
            return setColor(ColorHelper.makeRGBAInt(r,g,b,a));
        }

        /**
         * Assumes values from 0-255
         */
        public Builder setColor(Vector4i color) {
            return setColor(ColorHelper.makeRGBAInt(color));
        }

        /**
         * Assumes values from 0-255
         */
        public Builder setColor(int r, int g, int b, int a) {
            return setColor(ColorHelper.makeRGBAInt(r,g,b,a));
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        /**
         * x=bottom y=left z=top w=right
         */
        public Builder setCorners(Vector4i corners) {
            return setCorners(corners.x,corners.y,corners.z,corners.w);
        }

        /**
         * bottomLeft -> x=bottom y=left
         * topRight -> x=top y=right
         */
        public Builder setCorners(Vector2i bottomLeft, Vector2i topRight) {
            return setCorners(bottomLeft.x,bottomLeft.y,topRight.x,topRight.y);
        }

        public Builder setCorners(int bottom, int left, int top, int right) {
            this.left = left;
            this.bottom = bottom;
            this.width = right-left;
            this.height = top-bottom;
            return this;
        }

        public Builder setDimensions(Vector2i dimensions) {
            return setDimensions(dimensions.x,dimensions.y);
        }

        public Builder setDimensions(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setLeft(int left) {
            this.left = left;
            return this;
        }

        public Builder setScaleX(float scale) {
            this.scaleX = scale;
            return this;
        }

        public Builder setScaleY(float scale) {
            this.scaleY = scale;
            return this;
        }

        public Builder setSpacing(int spacing) {
            this.spacing = spacing;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }
    }
}
