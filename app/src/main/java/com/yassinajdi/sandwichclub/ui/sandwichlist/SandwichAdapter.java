package com.yassinajdi.sandwichclub.ui.sandwichlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yassinajdi.sandwichclub.databinding.ItemSandwichBinding;
import com.yassinajdi.sandwichclub.model.Sandwich;
import com.yassinajdi.sandwichclub.utils.GlideApp;

import java.util.List;

import timber.log.Timber;

/**
 * @author Yassin Ajdi
 */
public class SandwichAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;

    private List<Sandwich> mSandwichList;

    private SandwichListViewModel mViewModel;

    public SandwichAdapter(Context context, List<Sandwich> sandwiches, SandwichListViewModel viewModel) {
        mContext = context;
        mViewModel = viewModel;
        replaceData(sandwiches);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        ItemSandwichBinding binding =
                ItemSandwichBinding.inflate(layoutInflater, parent, false);
        return new SandwichViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Sandwich sandwich = mSandwichList.get(position);
        SandwichViewHolder sandwichViewHolder = (SandwichViewHolder) holder;

        // sandwich image
        GlideApp.with(mContext)
                .load(sandwich.getImage())
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(16)))
                .into(sandwichViewHolder.binding.imageSandwich);

        // sandwich name
        sandwichViewHolder.binding.textName.setText(sandwich.getMainName());

        // sandwich description
        sandwichViewHolder.binding.textDescription.setText(sandwich.getDescription());

        // click event
        sandwichViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Timber.d("Position clicked: %s", position);
                mViewModel.getOpenSandwichEvent().setValue(position);
            }
        });

        sandwichViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mSandwichList != null ? mSandwichList.size() : 0;
    }

    public void replaceData(List<Sandwich> sandwiches) {
        mSandwichList = sandwiches;
        notifyDataSetChanged();
    }

    /**
     * The {@link SandwichViewHolder} class.
     * Provides a binding reference to each view in sandwich cardView.
     */
    public class SandwichViewHolder extends RecyclerView.ViewHolder {

        private final ItemSandwichBinding binding;

        public SandwichViewHolder(final ItemSandwichBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

}
