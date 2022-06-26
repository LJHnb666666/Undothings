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
import com.google.samples.apps.sunflower.bean.CommonUndoBean;
import com.google.samples.apps.sunflower.databinding.ListItemMyUndoBinding;
import com.google.samples.apps.sunflower.fragments.MyUndoFragmentDirections;
import com.google.samples.apps.sunflower.viewmodels.CommonUndoItemViewModel;

public class MyUndoListAdapter extends ListAdapter<CommonUndoBean, MyUndoListAdapter.ViewHolder> {

    public MyUndoListAdapter() {
        super(new MyUndoDiffCallback());
    }

    @NonNull
    @Override
    public MyUndoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_my_undo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyUndoListAdapter.ViewHolder holder, int position) {
        CommonUndoBean commonUndoBean = getItem(position);
        holder.itemView.setTag(commonUndoBean);
        holder.bind(createOnClickListener(commonUndoBean.getUndoBean().getUndoId()), commonUndoBean);
    }

    private View.OnClickListener createOnClickListener(String undoId) {
        // 跳转到详情 并且把植物 id 给详情
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 导航过去了 到 详情
                Navigation.findNavController(v).navigate(
                        MyUndoFragmentDirections.actionMyUndoFragmentToUndoDetailFragment(undoId)
                );
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemMyUndoBinding binding; // item 每一项

        public ViewHolder(@NonNull ListItemMyUndoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(View.OnClickListener listener, CommonUndoBean commonUndos) {
            Log.v("ljh","bind: commonUndos :" + commonUndos);
            this.binding.setViewModel(new CommonUndoItemViewModel(commonUndos));
            this.binding.setClick(listener);
            this.binding.executePendingBindings();//当变量或可观察对象发生更改时，绑定会在下一帧之前更改。 不过有的时候需要立刻执行绑定.若要强制执行，可以使用 executePendingBindings() 方法。
        }
    }

    static class MyUndoDiffCallback extends DiffUtil.ItemCallback<CommonUndoBean> {

        @Override
        public boolean areItemsTheSame(@NonNull CommonUndoBean oldItem,
                                       @NonNull CommonUndoBean newItem) {
            return oldItem.getUndoBean().getUndoId().equals(newItem.getUndoBean().getUndoId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull CommonUndoBean oldItem,
                                          @NonNull CommonUndoBean newItem) {
            return oldItem.equals(newItem);
        }
    }
}
