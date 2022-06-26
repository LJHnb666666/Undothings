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

import com.google.samples.apps.sunflower.databinding.ActivityUndoBinding;


/**
 * 首页的默认 Activity
 */
public class UndoActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout; // androidx 的 抽屉布局
    private AppBarConfiguration appBarConfiguration; // androidx. Navigation 查询(AppBarConfiguration)：https://zhuanlan.zhihu.com/p/136479775
    private NavController navController; // androidx. Navigation 控制器

    // Nav 使用篇
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUndoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_undo);

        this.drawerLayout = binding.drawerLayout; // 拿到布局的drawer_layout 赋值 给成员drawerLayout

        this.navController = Navigation.findNavController(this, R.id.undo_nav_fragment);

        this.appBarConfiguration = new
                AppBarConfiguration.Builder(new int[]{R.id.my_undo_fragment, R.id.undo_list_fragment}) // （无箭头）

                .setDrawerLayout(drawerLayout) // 抽屉布局
                .build();

        setSupportActionBar(binding.toolbar);

        NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);

        NavigationUI.setupWithNavController(binding.navigationView, this.navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
