package com.websopti.tms.workingTask.component.availableresources.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.websopti.tms.R;
import com.websopti.tms.workingTask.component.availableresources.model.AvailableResourcesHeaderViewPojo;

public class AvailableResourceHeaderViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    @NonNull
    private TextView tv_available_res_name;
    private TextView tv_task_count;

    public AvailableResourceHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_available_res_name = (TextView) itemView.findViewById(R.id.tv_available_res_name);
        tv_task_count = (TextView) itemView.findViewById(R.id.tv_task_count);
    }

    public void bind(@NonNull AvailableResourcesHeaderViewPojo availableResourcesHeaderViewPojo) {
        tv_available_res_name.setText(availableResourcesHeaderViewPojo.getName());
        tv_task_count.setText(availableResourcesHeaderViewPojo.getCount());
    }

  /*  @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {
              //  mArrowExpandImageView.setRotation(ROTATED_POSITION);
            } else {
               // mArrowExpandImageView.setRotation(INITIAL_POSITION);
            }
        }
    }*/

   /* @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                 rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
           // mArrowExpandImageView.startAnimation(rotateAnimation);
        }
    }*/
}
