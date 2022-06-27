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

import com.google.samples.apps.sunflower.adapters.MyUndoListAdapter;
import com.google.samples.apps.sunflower.roombean.CommonUndoBean;
import com.google.samples.apps.sunflower.databinding.FragmentMyUndoBinding;
import com.google.samples.apps.sunflower.utilites.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.MyUndoListViewModel;
import com.google.samples.apps.sunflower.factory.CommonUndoItemViewModelFactory;

import java.util.List;


public class MyUndoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 使用 DataBinding的布局文件
        FragmentMyUndoBinding binding = FragmentMyUndoBinding.inflate(inflater, container, false);

        // 创建适配器 用于展示（我的花园）列表数据
        MyUndoListAdapter adapter = new MyUndoListAdapter();

        // 给RecycleView设置适配器数据展示画面
        binding.undoList.setAdapter(adapter);

        // 数据的关联：极其复杂
        subScribeUi(adapter, binding);

        // 返回binding的root是View类型，为什么返回它（由于把整个布局交给了DataBinding进行管理，所以getRoot是获取DataBinding管理的根节点）
        return binding.getRoot();
    }

    private void subScribeUi(@NonNull MyUndoListAdapter adapter, @NonNull FragmentMyUndoBinding binding) {
        CommonUndoItemViewModelFactory factory =
                InjectorUtils.provideCommonUndoItemViewModelFactory(requireContext());

        MyUndoListViewModel viewModel =
                ViewModelProviders.of(this, factory).get(MyUndoListViewModel.class);

        viewModel.myUndoList.observe(getViewLifecycleOwner(), gardenPlantings ->
                binding.setHasPlantings(gardenPlantings != null && !gardenPlantings.isEmpty()));

        viewModel.commonUndoList.observe(getViewLifecycleOwner(), new Observer<List<CommonUndoBean>>() {
            @Override
            public void onChanged(List<CommonUndoBean> plantAndGardenPlantings) {
                if (plantAndGardenPlantings != null && !plantAndGardenPlantings.isEmpty()) {
                    adapter.submitList(plantAndGardenPlantings);
                }
            }
        });
    }
}