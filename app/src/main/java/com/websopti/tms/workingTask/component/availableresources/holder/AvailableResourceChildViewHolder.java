package com.websopti.tms.workingTask.component.availableresources.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.websopti.tms.R;
import com.websopti.tms.workingTask.component.availableresources.model.AvailableResourcesChildViewPojo;

public class AvailableResourceChildViewHolder extends ChildViewHolder {

    /**
     * TextView
     */
    private TextView tv_child_row_title, tv_child_row_project_name, tv_child_row_created_date,
           tv_child_row_title_list,tv_task_project_name_list,tv_child_row_created_date_list;

    private LinearLayout ll_available_res_child_row_header;

    public AvailableResourceChildViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_child_row_title = (TextView) itemView.findViewById(R.id.tv_child_row_title);
        tv_child_row_project_name = (TextView) itemView.findViewById(R.id.tv_child_row_project_name);
        tv_child_row_created_date = (TextView) itemView.findViewById(R.id.tv_child_row_created_date);
        tv_child_row_title_list = (TextView)  itemView.findViewById(R.id.tv_child_row_title_list);
        tv_task_project_name_list = (TextView) itemView.findViewById(R.id.tv_task_project_name_list);
        tv_child_row_created_date_list = (TextView) itemView.findViewById(R.id.tv_child_row_created_date_list);
        ll_available_res_child_row_header = (LinearLayout) itemView.findViewById(R.id.ll_available_res_child_row_header);
    }

    public void bind(@NonNull AvailableResourcesChildViewPojo availableResourcesChildViewPojo, int position) {

        if(position == 0) {
           /* tv_child_row_title.setVisibility(View.VISIBLE);
            tv_child_row_project_name.setVisibility(View.VISIBLE);
            tv_child_row_created_date.setVisibility(View.VISIBLE);*/
            ll_available_res_child_row_header.setVisibility(View.VISIBLE);

        } else {
          /*tv_child_row_title.setVisibility(View.GONE);
            tv_child_row_project_name.setVisibility(View.GONE);
            tv_child_row_created_date.setVisibility(View.GONE);*/
            ll_available_res_child_row_header.setVisibility(View.GONE);

        }
        //Set Child View
        tv_child_row_title_list.setText(availableResourcesChildViewPojo.getTitle());
        tv_task_project_name_list.setText(availableResourcesChildViewPojo.getProjectName());
        tv_child_row_created_date_list.setText(availableResourcesChildViewPojo.getCreatedDate());
    }
}
