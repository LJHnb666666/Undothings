package com.google.samples.apps.sunflower.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.databinding.FragmentUndoDetailBinding;
import com.google.samples.apps.sunflower.utilites.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.UndoDetailViewModel;
import com.google.samples.apps.sunflower.factory.UndoDetailViewModelFactory;


public class UndoDetailFragment extends Fragment {
    private UndoDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // DataBinding初始化布局绑定
        FragmentUndoDetailBinding binding = FragmentUndoDetailBinding.inflate(inflater, container, false);
        // todo 根据传进来的id 创建相应的viewmodel
        // 获取传递来的参数 植物ID
        UndoDetailFragmentArgs args = UndoDetailFragmentArgs.fromBundle(requireArguments());

        // 必须通过 植物ID 去查询 数据库
        // 初始化【(植物详情的ViewModel) 的 创建工厂】 并把上面的参数信息赋值给此工厂
        UndoDetailViewModelFactory factory = InjectorUtils.providerUndoDetailViewModelFactory(
                requireContext(), args.getPlantId());

        // 初始化（植物详情的ViewModel）
        viewModel = ViewModelProviders.of(this, factory).get(UndoDetailViewModel.class);

        // todo 这个很重要 相当于省去了observe这一步 livedata改变 ui就可以直接改变
        // livedata和databinding关联起来
        binding.setLifecycleOwner(this);

        // DataBinding设置好ViewModel
        binding.setViewModel(viewModel);

        // 点击 + 号的时候 效果
        binding.fab.setOnClickListener(v -> {
            viewModel.addMyUndo(); // 植物数据 insert 我的花园

            // 提示：添加了新植物
            Snackbar.make(v, R.string.added_undo_to_garden, Snackbar.LENGTH_LONG).show();
        });


        // 返回binding的root是View类型，为什么返回它（由于把整个布局交给了DataBinding进行管理，所以getRoot是获取DataBinding管理的根节点）
        return binding.getRoot();
    }
}
