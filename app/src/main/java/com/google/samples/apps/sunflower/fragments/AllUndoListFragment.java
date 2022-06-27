/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ListAdapter;

import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.adapters.AllUndoListAdapter;
import com.google.samples.apps.sunflower.roombean.UndoBean;
import com.google.samples.apps.sunflower.databinding.FragmentUndoListBinding;
import com.google.samples.apps.sunflower.utilites.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.AllUndoListViewModel;
import com.google.samples.apps.sunflower.factory.AllUndoListViewModelFactory;

import java.util.List;

public class AllUndoListFragment extends Fragment {

    // 植物目录 列表 的 ViewModel
    private AllUndoListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // DataBinding绑定布局的操作
        FragmentUndoListBinding binding = FragmentUndoListBinding.inflate(inflater, container, false);

        AllUndoListViewModelFactory factory = InjectorUtils.provideAllUndoListViewModelFactory(getContext());

        viewModel = ViewModelProviders.of(this, factory).get(AllUndoListViewModel.class);

        // 适配器初始化
        ListAdapter adapter = new AllUndoListAdapter();

        binding.undoList.setAdapter(adapter);

        subscribeUi(adapter);

        // 植物目录：点击右上角 会重新随机查询展示
        setHasOptionsMenu(true);

//
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAllUserUndosFromNet();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_all_undo_list, menu); // 加载菜单布局
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_undo_menu:
                //然后跳转
                NavHostFragment.findNavController(this).navigate(
                        AllUndoListFragmentDirections.actionUndoListFragmentToAddUndoFragment()
                );
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    private void subscribeUi(ListAdapter adapter) {

        this.viewModel.allUndos.observe(getViewLifecycleOwner(), new Observer<List<UndoBean>>() {
            @Override
            public void onChanged(List<UndoBean> undoBeanList) {
                adapter.submitList(undoBeanList);
            }
        });
    }

}
