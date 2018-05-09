package com.websopti.tms.workingTask.component.availableresources.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.websopti.tms.R;
import com.websopti.tms.workingTask.component.availableresources.holder.AvailableResourceChildViewHolder;
import com.websopti.tms.workingTask.component.availableresources.holder.AvailableResourceHeaderViewHolder;
import com.websopti.tms.workingTask.component.availableresources.model.AvailableResourcesChildViewPojo;
import com.websopti.tms.workingTask.component.availableresources.model.AvailableResourcesHeaderViewPojo;

import java.util.List;

public class AvailableResourceAdapter extends ExpandableRecyclerAdapter<AvailableResourcesHeaderViewPojo, AvailableResourcesChildViewPojo, AvailableResourceHeaderViewHolder, AvailableResourceChildViewHolder> {

    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private List<AvailableResourcesHeaderViewPojo> mAvailableResourcesHeaderViewPojoList;

    public AvailableResourceAdapter(Context context, @NonNull List<AvailableResourcesHeaderViewPojo> availableResourcesHeaderViewPojoList) {
        super(availableResourcesHeaderViewPojoList);
        mAvailableResourcesHeaderViewPojoList = availableResourcesHeaderViewPojoList;
        mInflater = LayoutInflater.from(context);
    }

    @UiThread
    @NonNull
    @Override
    public AvailableResourceHeaderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {
            default:
           /* case PARENT_NORMAL:
                recipeView = mInflater.inflate(R.layout.recipe_view, parentViewGroup, false);
                break;*/
             case PARENT_VEGETARIAN:
                recipeView = mInflater.inflate(R.layout.available_recource_header_view, parentViewGroup, false);
                break;
        }
        return new AvailableResourceHeaderViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public AvailableResourceChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        switch (viewType) {
            default:
            case CHILD_NORMAL:
                ingredientView = mInflater.inflate(R.layout.available_recource_child_view, childViewGroup, false);
                break;
         /*  case CHILD_VEGETARIAN:
                ingredientView = mInflater.inflate(R.layout.vegetarian_ingredient_view, childViewGroup, false);
                break;*/
        }
        return new AvailableResourceChildViewHolder(ingredientView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull AvailableResourceHeaderViewHolder availableResourceHeaderViewHolder, int parentPosition, @NonNull AvailableResourcesHeaderViewPojo availableResourcesHeaderViewPojo) {
        availableResourceHeaderViewHolder.bind(availableResourcesHeaderViewPojo);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull AvailableResourceChildViewHolder availableResourceChildViewHolder, int parentPosition, int childPosition, @NonNull AvailableResourcesChildViewPojo availableResourcesChildViewPojo) {
        availableResourceChildViewHolder.bind(availableResourcesChildViewPojo, childPosition);
    }

    @Override
    public int getParentViewType(int parentPosition) {
        if (mAvailableResourcesHeaderViewPojoList.get(parentPosition).isVegetarian()) {
            return PARENT_VEGETARIAN;
        } else {
            return PARENT_NORMAL;
        }
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        AvailableResourcesChildViewPojo availableResourcesChildViewPojo = mAvailableResourcesHeaderViewPojoList.get(parentPosition).getIngredient(childPosition);
        if (availableResourcesChildViewPojo.isVegetarian()) {
            return CHILD_VEGETARIAN;
        } else {
            return CHILD_NORMAL;
        }
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_VEGETARIAN || viewType == PARENT_NORMAL;
    }

}
