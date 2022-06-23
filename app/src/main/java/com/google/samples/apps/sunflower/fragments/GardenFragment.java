package com.google.samples.apps.sunflower.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding;
import com.google.samples.apps.sunflower.utilites.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModelFactory;

import java.util.List;

/**
 * 【Fragment1 我的花园】 【打开APP所展示的 默认首页Fragment】
 */
public class GardenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 使用 DataBinding的布局文件
        FragmentGardenBinding binding = FragmentGardenBinding.inflate(inflater, container, false);

        // 创建适配器 用于展示（我的花园）列表数据
        GardenPlantingAdapter adapter = new GardenPlantingAdapter();

        // 给RecycleView设置适配器数据展示画面
        binding.gardenList.setAdapter(adapter);

        // 数据的关联：极其复杂
        subScribeUi(adapter, binding);

        // 返回binding的root是View类型，为什么返回它（由于把整个布局交给了DataBinding进行管理，所以getRoot是获取DataBinding管理的根节点）
        return binding.getRoot();
    }

    private void subScribeUi(@NonNull GardenPlantingAdapter adapter, @NonNull FragmentGardenBinding binding) {
        GardenPlantingListViewModelFactory factory =
                InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext());

        GardenPlantingListViewModel viewModel =
                ViewModelProviders.of(this, factory).get(GardenPlantingListViewModel.class);

        viewModel.gardenPlantings.observe(getViewLifecycleOwner(), gardenPlantings ->
                binding.setHasPlantings(gardenPlantings != null && !gardenPlantings.isEmpty()));
                //Log.v("ljh","GardenFragment里面，gardenPlantings" + gardenPlantings));

        viewModel.plantAndGardenPlantings.observe(getViewLifecycleOwner(), new Observer<List<PlantAndGardenPlantings>>() {
            @Override
            public void onChanged(List<PlantAndGardenPlantings> plantAndGardenPlantings) {
                if (plantAndGardenPlantings != null && !plantAndGardenPlantings.isEmpty()) {
                    adapter.submitList(plantAndGardenPlantings);
                }
            }
        });
    }
}