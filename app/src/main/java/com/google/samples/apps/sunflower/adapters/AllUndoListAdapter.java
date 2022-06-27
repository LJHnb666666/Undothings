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

import com.google.samples.apps.sunflower.roombean.UndoBean;
import com.google.samples.apps.sunflower.databinding.ListItemAllUndoBinding;
import com.google.samples.apps.sunflower.fragments.AllUndoListFragmentDirections;

public class AllUndoListAdapter extends ListAdapter<UndoBean, AllUndoListAdapter.ViewHolder> {

    public AllUndoListAdapter() {
        super(new UndoDiffCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemAllUndoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UndoBean undoBean = getItem(position);
        holder.itemView.setTag(undoBean);
        holder.bind(createOnClickListener(undoBean.getUndoId()), undoBean);
    }

    private View.OnClickListener createOnClickListener(int undoId) {
        // 导航过去了 到 详情
        return v -> Navigation.findNavController(v).navigate(
                AllUndoListFragmentDirections.actionUndoListFragmentToUndoDetailFragment(undoId + "")
        );
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemAllUndoBinding binding;

        ViewHolder(@NonNull ListItemAllUndoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(View.OnClickListener listener, UndoBean item) {
            binding.setUndo(item);
            binding.setClick(listener);
            binding.executePendingBindings();
        }
    }

    static class UndoDiffCallback extends DiffUtil.ItemCallback<UndoBean> {

        @Override
        public boolean areItemsTheSame(@NonNull UndoBean oldItem, @NonNull UndoBean newItem) {
            return oldItem.getUndoId() == newItem.getUndoId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull UndoBean oldItem, @NonNull UndoBean newItem) {
            return oldItem == newItem;
        }
    }
}
