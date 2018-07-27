package com.udacity.sandwichclub.ui.sandwichlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

/**
 * @author Yassin Ajdi
 */
public class SandwichAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;

    private List<Sandwich> mSandwichList;

    public SandwichAdapter(Context context, List<Sandwich> sandwiches) {
        mContext = context;
        replaceData(sandwiches);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mSandwichList != null ? mSandwichList.size() : 0;
    }

    private void replaceData(List<Sandwich> sandwiches) {
        mSandwichList = sandwiches;
        notifyDataSetChanged();
    }

}
