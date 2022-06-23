package com.google.samples.apps.sunflower.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.databinding.ListItemPlantBinding;
import com.google.samples.apps.sunflower.fragments.PlantListFragmentDirections;

public class PlantAdapter extends ListAdapter<Plant, PlantAdapter.ViewHolder> {

    public PlantAdapter() {
        super(new PlantDiffCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plant plant = getItem(position);
        holder.itemView.setTag(plant);
        holder.bind(createOnClickListener(plant.getPlantId()), plant);
    }

    private View.OnClickListener createOnClickListener(String plantId) {
        // 导航过去了 到 详情
        return v -> Navigation.findNavController(v).navigate(
                PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(plantId)
        );
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemPlantBinding binding;

        ViewHolder(@NonNull ListItemPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(View.OnClickListener listener, Plant item) {
            binding.setPlant(item);
            binding.setClick(listener);
            binding.executePendingBindings();
        }
    }

    static class PlantDiffCallback extends DiffUtil.ItemCallback<Plant> {

        @Override
        public boolean areItemsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem.getPlantId().equals(newItem.getPlantId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem == newItem;
        }
    }
}
