# LollipopDesign
在demo中主要说明的是Lollipop版本，V7包中的控件实例。

其中包括
Toolbar，CardView，SwipeRefreshLayout，RecyclerView，ProgressBar，FloatingActionButton，Drawer(DrawerLayout)，
NavigationView,CoordinatorLayout

Toolbar:android.support.v7.widget.Toolbar
1.引用v7 的appcompat 包
2.使用Toolbar的Activity要继承AppCompatActivity
3.需要更改主题为NoActionbBar的主题
4.在布局文件中引用Toolbar , 需引用v7包中的Toolbar , 默认的Toolbar 仅支持 API >= 21 (android 5.0)的系统
5.在代码中调用setSupportActionBar(toobar) 方法将Toolbar绑定到当前界面


CardView:android.support.v7.widget.CardView
继承自FrameLayout并实现了圆角和阴影效果,常用于ListView或RecyclerView中Item布局的根节点

添加点击后的波纹效果:
  android:clickable="true"
  android:foreground="?android:attr/selectableItemBackground"

在此不再赘述，查看内部示例即可