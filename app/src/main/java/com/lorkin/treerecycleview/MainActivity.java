package com.lorkin.treerecycleview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private RecyclerView ry;
    private TreeAdapter treeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ry.setLayoutManager(linearLayoutManager);
        ry.addItemDecoration(new CommonAdapter.RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.gray)));
        ry.setAdapter(treeAdapter = new TreeImplAdapter(this));

        // 第一层数据初始化
        ArrayList<TreeItem> list = new ArrayList<>();
        TreeItem treeItem0 = getTreeItem(0, "第一层0");
        TreeItem treeItem1 = getTreeItem(0, "第一层1");
        TreeItem treeItem2 = getTreeItem(0, "第一层2");
        list.add(treeItem0);
        list.add(treeItem1);
        list.add(treeItem2);

        // 第二层数据初始化
        ArrayList<TreeItem> list0_0 = new ArrayList<>();
        TreeItem treeItem0_0 = getTreeItem(1, "第二层0");
        TreeItem treeItem0_1 = getTreeItem(1, "第二层1");
        TreeItem treeItem0_2 = getTreeItem(1, "第二层2");
        list0_0.add(treeItem0_0);
        list0_0.add(treeItem0_1);
        list0_0.add(treeItem0_2);
        treeItem0.addChilds(list0_0);

        // 第三层数据初始化
        ArrayList<TreeItem> list0_0_0 = new ArrayList<>();
        TreeItem treeItem0_0_0 = getTreeItem(2, "第三层0");
        TreeItem treeItem0_0_1 = getTreeItem(2, "第三层1");
        TreeItem treeItem0_0_2 = getTreeItem(2, "第三层2");
        list0_0_0.add(treeItem0_0_0);
        list0_0_0.add(treeItem0_0_1);
        list0_0_0.add(treeItem0_0_2);
        treeItem0_0.addChilds(list0_0_0);

        // 第四层数据初始化
        ArrayList<TreeItem> list0_0_0_0 = new ArrayList<>();
        TreeItem treeItem0_0_0_0 = getTreeItem(3, "第四层0");
        TreeItem treeItem0_0_0_1 = getTreeItem(3, "第四层1");
        TreeItem treeItem0_0_0_2 = getTreeItem(3, "第四层2");
        list0_0_0_0.add(treeItem0_0_0_0);
        list0_0_0_0.add(treeItem0_0_0_1);
        list0_0_0_0.add(treeItem0_0_0_2);
        treeItem0_0_0.addChilds(list0_0_0_0);

        treeAdapter.setDatas(list);
    }

    private void initView() {
        ry = (RecyclerView) findViewById(R.id.ry);
    }

    private TreeItem getTreeItem(int level, String name){
        HashMap<String, String> data =  new HashMap<String, String>();
        data.put("name", name);
        return new TreeItem(level, false, data);
    }
}