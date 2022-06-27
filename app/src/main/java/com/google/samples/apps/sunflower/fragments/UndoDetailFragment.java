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
        UndoDetailFragmentArgs args = UndoDetailFragmentArgs.fromBundle(requireArguments());

        UndoDetailViewModelFactory factory = InjectorUtils.providerUndoDetailViewModelFactory(
                requireContext(), Integer.valueOf(args.getPlantId()));

        viewModel = ViewModelProviders.of(this, factory).get(UndoDetailViewModel.class);

        // livedata和databinding关联起来
        binding.setLifecycleOwner(this);

        // DataBinding设置好ViewModel
        binding.setViewModel(viewModel);

        // 点击 + 号的时候 效果
        binding.fab.setOnClickListener(v -> {
            if(!Boolean.TRUE.equals(viewModel.getIsAdd().getValue())){
                viewModel.addMyUndo();
                Snackbar.make(v, "添加了新的待办", Snackbar.LENGTH_LONG).show();

            }else{
                //该 待办 已经添加了
                viewModel.deleteMyUndo();
                Snackbar.make(v, "删除了待办", Snackbar.LENGTH_LONG).show();
            }
        });


        return binding.getRoot();
    }
}
