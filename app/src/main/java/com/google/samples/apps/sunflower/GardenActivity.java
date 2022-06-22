package com.google.samples.apps.sunflower;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding;

/**
 * 首页的默认 Activity
 */
public class GardenActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout; // androidx 的 抽屉布局
    private AppBarConfiguration appBarConfiguration; // androidx. Navigation 查询(AppBarConfiguration)：https://zhuanlan.zhihu.com/p/136479775
    private NavController navController; // androidx. Navigation 控制器

    // Nav 使用篇
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 同学们注意：由于我们是把整个布局交给了-->DataBinding去管理，那么就需要使用DataBinding初始化布局
        // DataBinding初始化
        ActivityGardenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_garden);

        this.drawerLayout = binding.drawerLayout; // 拿到布局的drawer_layout 赋值 给成员drawerLayout

        // 拿到布局的garden_nav_fragment(首页布局Fragment画面区域) 赋值 给成员navController
        this.navController = Navigation.findNavController(this, R.id.garden_nav_fragment);

        // NavigationUI使用AppBarConfiguration 对象来管理应用程序显示区域左上角的“导航”按钮的行为
        // this.appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()) // 指向给它 (有箭头)

        this.appBarConfiguration = new
                AppBarConfiguration.Builder(new int[]{R.id.garden_fragment, R.id.plant_list_fragment}) // （无箭头）

                .setDrawerLayout(drawerLayout) // 抽屉布局
                .build();

        // 标题栏 == toolbar
        // Set up ActionBar
        setSupportActionBar(binding.toolbar);

        // 查询 setupActionBarWithNavController：https://zhuanlan.zhihu.com/p/69653419?from_voters_page=true
        // 在标题 左上角菜单 1
        // 通过NavigationUI.setupActionBarWithNavController()方法，将App bar与NavController绑定
        NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);

        // 当我们点击 左侧栏 [我的花园 按钮] [植物目录 按钮] 就能触发事件 执行...
        // 左侧栏 于此 绑定  （左侧栏--> [我的花园 按钮] [植物目录 按钮]）
        // Set up navigation menu
        NavigationUI.setupWithNavController(binding.navigationView, this.navController);

        // 上面全部都是 Nav的知识 使用
        // TODO 同学们：其实很多都是Navigation的使用，前面老师已经单独专门讲过Navigation，这个对同学们来说，问题不大吧
    }

    // 在标题是 左上角菜单 2
    // 点击 标题上 左上角菜单 触发的返回动作  [点击事件]
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
