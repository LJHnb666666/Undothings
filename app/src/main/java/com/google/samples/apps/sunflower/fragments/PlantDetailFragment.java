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
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding;
import com.google.samples.apps.sunflower.utilites.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModelFactory;

/**
 * A fragment representing a single Plant detail screen.
 * 植物介绍详情 的 Fragment
 */
public class PlantDetailFragment extends Fragment {

    private String shareText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // DataBinding初始化布局绑定
        FragmentPlantDetailBinding binding = FragmentPlantDetailBinding.inflate(inflater, container, false);

        // 以前的方式  植物ID
        /*Intent intent;
        intent.getStringArrayExtra()*/

        // 新的方式获取
        // 获取参数 植物ID
        //  Navigation Safe
        PlantDetailFragmentArgs args = PlantDetailFragmentArgs.fromBundle(requireArguments());

        String plantId = args.getPlantId();
        // float age = args.getAge();

        // 必须通过 植物ID 去查询 数据库
        // 初始化【(植物详情的ViewModel) 的 创建工厂】 并把上面的参数信息赋值给此工厂
        PlantDetailViewModelFactory factory = InjectorUtils.providerPlantDetailViewModelFactory(
                requireContext(), args.getPlantId());

        // 初始化（植物详情的ViewModel）
        PlantDetailViewModel viewModel = ViewModelProviders.of(this, factory).get(PlantDetailViewModel.class);

        // 监听感应生效 LiveData       ViewModel里面的LiveData <------>  布局的使用 关联起来  感应
        binding.setLifecycleOwner(this);

        // DataBinding设置好ViewModel
        binding.setViewModel(viewModel);

        // 点击 + 号的时候 效果
        binding.fab.setOnClickListener(v -> {
            viewModel.addPlantToGarden(); // 植物数据 insert 我的花园

            // 这个效果非常好，同学们可以借鉴下
            // 以后可能会代替 Toast
            // 提示：添加了新植物
            Snackbar.make(v, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show();
        });

        // 如果等于空的话，就显示：（在安卓 Sunflower APP 上看看这 %s）
        viewModel.plant.observe(this, plant ->
                this.shareText = plant == null ? "" : getString(R.string.share_text_plant, plant.getName()));

        // 标题栏 右上角分享按钮
        setHasOptionsMenu(true);

        // 返回binding的root是View类型，为什么返回它（由于把整个布局交给了DataBinding进行管理，所以getRoot是获取DataBinding管理的根节点）
        return binding.getRoot();

    }
}
