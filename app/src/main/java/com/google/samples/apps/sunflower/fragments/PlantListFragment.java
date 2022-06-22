/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ListAdapter;

import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.adapters.PlantAdapter;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.databinding.FragmentPlantListBinding;
import com.google.samples.apps.sunflower.utilites.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel;
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModelFactory;

import java.util.List;

/**
 * 植物目录 的 Fragment
 * 【Fragment2 植物目录】植物目录的Fragment
 */
public class PlantListFragment extends Fragment {

    // 植物目录 列表 的 ViewModel
    private PlantListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 所有的布局文件都交给了 DataBinding  来初始化布局文件
        // DataBinding绑定布局的操作
        FragmentPlantListBinding binding = FragmentPlantListBinding.inflate(inflater, container, false);

        // 暴漏 植物 列表 ViewModel 工厂 (数据初始化的起点)      // 开启WM任务 insert room数据库
        PlantListViewModelFactory factory = InjectorUtils.providePlantListViewModelFactory(getContext());

        // 适配器初始化
        ListAdapter adapter = new PlantAdapter();

        // 列表控件 和  适配器 关联
        binding.plantList.setAdapter(adapter);

        this.viewModel = ViewModelProviders.of(this, factory).get(PlantListViewModel.class);

        // 到这里为止： // ViewModel 有值

        subscribeUi(adapter);

        // 菜单
        // 植物目录：点击右上角 会重新随机查询展示
        setHasOptionsMenu(true);

        // 返回binding的root是View类型，为什么返回它（由于把整个布局交给了DataBinding进行管理，所以getRoot是获取DataBinding管理的根节点）
        return binding.getRoot();
    }

    // 植物目录：点击右上角 会重新随机查询展示
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plant_list, menu); // 加载菜单布局
    }

    // 点击事件
    // 植物目录：点击右上角 会重新随机查询展示[当用户点击的时候，就会触发监听]
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_zone:
                updateData(); // 我写了查询吗，是不是没有，我只关心  修改数据
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateData() {
        if (viewModel.isFiltered()) { // 是否过滤 标记
            viewModel.cleanGrowZoneNumber(); // 清除此标记 改为默认状态 -1  （正常查询）
        } else {
            viewModel.setGrowZoneNumber(9); // 设置此标记 为9  （查询为 9的数据）
        }
    }

    /**
     * 画面真正的展示渲染 细节操作
     * @param adapter 上面丢下来的 适配器
     */
    private void subscribeUi(ListAdapter adapter) {

        // 使用LiveData的粘性 执行 把数据全部刷新到 RecyclerView 中去显示出来
        // 眼睛(第二双眼睛)
        this.viewModel.plants.observe(getViewLifecycleOwner(), new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                adapter.submitList(plants); // RecyclerView 中去显示出来
            }
        });
    }

    // 灵活多变的，没有固定一说
    /*public class Click {

        // 对话框
        public void show() {
            Toast.makeText(PlantListFragment.this.requireContext(), "Derry", Toast.LENGTH_SHORT).show();
        }

    }*/
}
