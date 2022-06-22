package com.google.samples.apps.sunflower.utilites;

import android.content.Context;

import com.google.samples.apps.sunflower.data.AppDatabase;
import com.google.samples.apps.sunflower.data.GardenPlantingDao;
import com.google.samples.apps.sunflower.data.GardenPlantingRepository;
import com.google.samples.apps.sunflower.data.PlantRepository;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModelFactory;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModelFactory;
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModelFactory;

/**
 * TODO 数据获取源
 *
 * Static methods used to inject classes needed for various Activities and Fragments.
 * 用于注入各种活动和片段所需的类的静态方法
 */
public class InjectorUtils {

    // 获取 我的花园 仓库
    private static GardenPlantingRepository getGardenPlantingRepository(Context context) {
        // 获取 我的花园 增删改查操作-DAO
        GardenPlantingDao dao = AppDatabase.getInstance(context.getApplicationContext()).getGardenPlantingDao();

        // 把 我的花园dao加入进去 并 创建 我的花园 仓库层
        return GardenPlantingRepository.getInstance(dao);
    }


    // 暴漏 我的花园 列表 ViewModel 工厂
    public static GardenPlantingListViewModelFactory provideGardenPlantingListViewModelFactory(Context context) {

        // 获取 我的花园 仓库
        GardenPlantingRepository gardenPlantingRepository = getGardenPlantingRepository(context);

        // 把 我的花园 仓库 传入 并 创建 --> 我的花园 List 列表的 ViewModel】 的工厂
        return new GardenPlantingListViewModelFactory(gardenPlantingRepository);
    }

    // 获取 植物 仓库
    private static PlantRepository getPlantRepository(Context context) {
        // AppDatabase.getInstance == (数据初始化的起点) 开始执行了，往 plant表里面插入数据
        return PlantRepository.getInstance(AppDatabase.getInstance(context.getApplicationContext()).getPlantDao());
    }

    // 暴漏 植物列表ViewModel  的  工厂
    public static PlantListViewModelFactory providePlantListViewModelFactory(Context context) {

        // 仓库
        PlantRepository plantRepository = getPlantRepository(context);

        return new PlantListViewModelFactory(plantRepository);
    }

    // 暴漏 植物介绍详情 ViewModel 工厂
    public static PlantDetailViewModelFactory providerPlantDetailViewModelFactory(Context context, String plantId) {
        PlantRepository plantRepository = getPlantRepository(context);
        GardenPlantingRepository gardenPlantingRepository = getGardenPlantingRepository(context);
        return new PlantDetailViewModelFactory(plantRepository, gardenPlantingRepository, plantId);
    }
}
