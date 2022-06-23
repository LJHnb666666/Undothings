package com.google.samples.apps.sunflower.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding;
import com.google.samples.apps.sunflower.fragments.GardenFragmentDirections;
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel;

public class GardenPlantingAdapter extends ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder> {

    public GardenPlantingAdapter() {
        super(new GardenPlantDiffCallback());
    }

    @NonNull
    @Override
    public GardenPlantingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_garden_planting, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GardenPlantingAdapter.ViewHolder holder, int position) {
        PlantAndGardenPlantings plantings = getItem(position);
        holder.itemView.setTag(plantings);
        holder.bind(createOnClickListener(plantings.getPlant().getPlantId()), plantings);
    }

    private View.OnClickListener createOnClickListener(String plantId) {
        // 跳转到详情 并且把植物 id 给详情
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 导航过去了 到 详情
                Navigation.findNavController(v).navigate(
                        GardenFragmentDirections.actionGardenFragmentToPlantDetailFragment(plantId)
                );
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemGardenPlantingBinding binding; // item 每一项

        public ViewHolder(@NonNull ListItemGardenPlantingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(View.OnClickListener listener, PlantAndGardenPlantings plantings) {
            Log.v("ljh","bind: plantings :" + plantings);
            this.binding.setViewModel(new PlantAndGardenPlantingsViewModel(plantings));
            this.binding.setClick(listener);
            this.binding.executePendingBindings();//当变量或可观察对象发生更改时，绑定会在下一帧之前更改。 不过有的时候需要立刻执行绑定.若要强制执行，可以使用 executePendingBindings() 方法。
        }
    }

    static class GardenPlantDiffCallback extends DiffUtil.ItemCallback<PlantAndGardenPlantings> {

        @Override
        public boolean areItemsTheSame(@NonNull PlantAndGardenPlantings oldItem,
                                       @NonNull PlantAndGardenPlantings newItem) {
            return oldItem.getPlant().getPlantId().equals(newItem.getPlant().getPlantId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull PlantAndGardenPlantings oldItem,
                                          @NonNull PlantAndGardenPlantings newItem) {
            return oldItem.equals(newItem);
        }
    }
}
