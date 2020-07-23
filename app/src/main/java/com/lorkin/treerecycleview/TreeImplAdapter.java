package com.lorkin.treerecycleview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

public class TreeImplAdapter extends TreeAdapter{
    public TreeImplAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0){
            view = this.inflater.inflate(R.layout.item_level0, parent, false);
            return new TypeViewHolder(view) {
                @Override
                public void bindHolder(final TreeItem model, final int position) {
                    this.setText(R.id.name_tv, model.getObj().get("name"));
                    this.getView(R.id.item_ly).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (model.isOpen()){
                                doClose(position, model);
                            }else{
                                doOpen(position, model);
                            }
                        }
                    });
                    if (model.isOpen()){
                        this.setImageResource(R.id.arrow_iv, R.mipmap.big_arrow_down);
                    }else{
                        this.setImageResource(R.id.arrow_iv, R.mipmap.big_arrow_right);
                    }
                }
            };
        }else if (viewType == 1){
            view = this.inflater.inflate(R.layout.item_level1, parent, false);
            return new TypeViewHolder(view) {
                @Override
                public void bindHolder(final TreeItem model, final int position) {
                    this.setText(R.id.name_tv, model.getObj().get("name"));
                    this.getView(R.id.item_ly).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (model.isOpen()){
                                doClose(position, model);
                            }else{
                                doOpen(position, model);
                            }
                        }
                    });
                    if (model.isOpen()){
                        this.setImageResource(R.id.arrow_iv, R.mipmap.big_arrow_down);
                    }else{
                        this.setImageResource(R.id.arrow_iv, R.mipmap.big_arrow_right);
                    }
                }
            };
        }else if (viewType == 2){
            view = this.inflater.inflate(R.layout.item_level2, parent, false);
            return new TypeViewHolder(view) {
                @Override
                public void bindHolder(final TreeItem model, final int position) {
                    this.setText(R.id.name_tv, model.getObj().get("name"));
                    this.getView(R.id.item_ly).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (model.isOpen()){
                                doClose(position, model);
                            }else{
                                doOpen(position, model);
                            }
                        }
                    });
                    if (model.isOpen()){
                        this.setImageResource(R.id.arrow_iv, R.mipmap.big_arrow_down);
                    }else{
                        this.setImageResource(R.id.arrow_iv, R.mipmap.big_arrow_right);
                    }
                }
            };
        }else{
            //普通一级别
            view = this.inflater.inflate(R.layout.item_level3, parent, false);
            return new TypeViewHolder(view) {
                @Override
                public void bindHolder(TreeItem model, int position) {
                    this.setText(R.id.name_tv, model.getObj().get("name"));
                }
            };
        }
    }

    @Override
    public int getItemViewType(int position) {
        return this.list.get(position).getLevel();
    }

}
