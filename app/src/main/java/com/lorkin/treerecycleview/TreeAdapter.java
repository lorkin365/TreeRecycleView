package com.lorkin.treerecycleview;

import android.content.Context;
import java.util.List;

public abstract class TreeAdapter extends CommonAdapter<TreeItem>{
    public TreeAdapter(Context context) {
        super(context);
    }

    public void addData(int position, List<TreeItem> treeItems){
        if (treeItems == null || position >=  this.list.size() || position < 0){
            return;
        }
        this.list.get(position).setOpen(true);
        this.list.get(position).addChilds(treeItems);
        super.addData(position + 1, treeItems);
    }

    /**
     * 展开特定行的数据
     * @param position
     * @param treeItem
     */
    public void doOpen(int position, TreeItem treeItem) {
        if (treeItem.getChild() != null && treeItem.getChild().size() > 0) {
            this.list.addAll(position + 1, treeItem.getChild());
            treeItem.setOpen(true);
            notifyItemRangeInserted(position + 1, treeItem.getChild().size());
            notifyItemChanged(position);
        }
    }

    /**
     * 关闭特定行的数据
     * @param position
     * @param treeItem
     */
    public void doClose(int position, TreeItem treeItem){
        if (treeItem.getChild() != null && position < list.size() - 1) {
            //扫描到下一个同级的列表
            treeItem.setOpen(false);
            int currentLevel = treeItem.getLevel();
            //初始化下一个同级的索引
            int nextPosition = list.size();
            for (int i = position + 1; i < list.size(); i++) {
                // 如果当前和需要关闭的层级相同
                if(currentLevel == list.get(i).getLevel()){
                    //赋值
                    nextPosition = i;
                    break;
                }
            }
            //判断当前层级
            if (nextPosition > position){
                // 设置子数据的状态为关闭
                for (int i = nextPosition -1 ; i > position; i--){
                    list.get(i).setOpen(false);
                    // 移除数据
                    list.remove(i);
                }
                // 移除本级到
                notifyItemRangeRemoved(position + 1, nextPosition - position - 1);
                notifyItemChanged(position);
            }
        }
    }
}
