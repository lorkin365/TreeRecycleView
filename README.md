# TreeRecycleView

1.实体类数据
        //层级
        private int level;
        //状态，打开或者关闭
        private boolean isOpen;
        //当前行的数据
        private HashMap<String, String> obj;
        //当前行的子数据
        private List<TreeItem> child;
  
实体类数据包括了层级、打开或者关闭的状态、当前行的数据和当前行的子数据

2.数据初始化
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
        treeItem0_0.addChilds(list0_0_0)
        
        treeAdapter.setDatas(list);
        
数据的初始化一层套一层，初始化第一层然后再将第二层的数据挂在第一层，如果有下一层以此类推。

3.继承TreeAdapter，实现onCreateViewHolder方法和getItemViewType方法，你可以实现多布局多样式，也可以自主做各种动作或者从网络获取下一层数据，扩展性很强，可以随自己需要设定。
        if (model.isOpen()){
             doClose(position, model);
        }else{
             doOpen(position, model);
        }
上面列出的是一个重要的代码，在某项被点击的时候，通过状态然后调用doClose或者doOpen,只要数据设置没有问题，父类会自动实现展开和关闭并加上平滑的动画。


希望对大家的学习和工作能有帮助

