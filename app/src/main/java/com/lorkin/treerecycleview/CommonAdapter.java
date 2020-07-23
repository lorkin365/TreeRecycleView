package com.lorkin.treerecycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> list;
    private Context context;
    protected LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    protected int mType;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommonAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((TypeViewHolder) holder).bindHolder(list.get(position), position);
        //添加点击事件
        if(onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position, list.get(position));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
    }

    public void setDatas(List<T> datas){
        this.list = datas;
        notifyDataSetChanged();
    }

    public void addData(int position, List<T> treeItems){
        this.list.addAll(position + 1, treeItems);
    }

    @Override
    public abstract int getItemViewType(int position);

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * -----------------------------------------------------------------------------------------------
     */
    public abstract class TypeViewHolder extends RecyclerView.ViewHolder {

        private View convertView;
        private SparseArray<View> views = new SparseArray<>();

        public TypeViewHolder(View itemView) {
            super(itemView);
            this.convertView = itemView;
        }

        @SuppressWarnings("unchecked")
        public <V extends View> V getView(int viewId) {
            View view = views.get(viewId);
            if(view == null) {
                view = convertView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (V) view;
        }

        public TypeViewHolder setText(int viewId, String text) {
            String str = "";
            if (!TextUtils.isEmpty(text)){
                str = text.replace("null", "");
            }
            TextView textView = getView(viewId);
            textView.setText(str);
            return this;
        }

        public TypeViewHolder setBackgroundColor(int viewId, int colorId) {
            View view = getView(viewId);
            view.setBackgroundResource(colorId);
            return this;
        }

        public TypeViewHolder setVisible(int viewId, int visible) {
            View view = getView(viewId);
            view.setVisibility(visible);
            return this;
        }

        public TypeViewHolder setImageResource(int viewId, int imgId) {
            ImageView view = getView(viewId);
            view.setImageResource(imgId);
            return this;
        }

        public abstract void bindHolder(T model, int position);
    }

    /**
     * -----------------------------------------------------------------------------------------------
     */
    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T t);
        void onItemLongClick(View view, int position);
    }

    /**
     * -------------------------------------------------------------------------s----------------------
     */
    public static class RecycleViewDivider extends RecyclerView.ItemDecoration {

        private Paint mPaint;
        private Drawable mDivider;
        private int mDividerHeight = 2;//分割线高度，默认为1px
        private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};//使用系统自带的listDivider

        /**
         * 默认分割线：高度为2px，颜色为灰色
         *
         * @param context
         * @param orientation 列表方向
         */
        public RecycleViewDivider(Context context, int orientation) {
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请输入正确的参数！");
            }
            mOrientation = orientation;

            final TypedArray a = context.obtainStyledAttributes(ATTRS);//使用TypeArray加载该系统资源
            mDivider = a.getDrawable(0);
            a.recycle();//缓存
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation 列表方向
         * @param drawableId  分割线图片
         */
        public RecycleViewDivider(Context context, int orientation, int drawableId) {
            this(context, orientation);
            mDivider = ContextCompat.getDrawable(context, drawableId);
            mDividerHeight = mDivider.getIntrinsicHeight();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation   列表方向
         * @param dividerHeight 分割线高度
         * @param dividerColor  分割线颜色
         */
        public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
            this(context, orientation);
            mDividerHeight = dividerHeight;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(dividerColor);
            mPaint.setStyle(Paint.Style.FILL);
        }


        //获取分割线尺寸
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //设置偏移的高度是mDivider.getIntrinsicHeight，该高度正是分割线的高度
            outRect.set(0, 0, 0, mDividerHeight);
        }

        //绘制分割线
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        //绘制横向 item 分割线
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();//获取分割线的左边距，即RecyclerView的padding值
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();//分割线右边距
            final int childSize = parent.getChildCount();
            //遍历所有item view，为它们的下方绘制分割线
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }

        //绘制纵向 item 分割线
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }
}