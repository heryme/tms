package com.websopti.tms.workingTask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.websopti.tms.R;
import com.websopti.tms.workingTask.model.AvailableResourceModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class AvailableResourceAdapter extends BaseExpandableListAdapter {

    private static final String TAG = AvailableResourceAdapter.class.getSimpleName();
    private Context _context;

    private LayoutInflater inf;

    /**
     * List For Header
     */
    private List<AvailableResourceModel.ResourceDetail> _listDataHeader;

    /**
     * Hash map For
     */
    private HashMap<String, List<AvailableResourceModel.ResourceDetail.AssignedTaskListDetails>> dataChildMap;

    public AvailableResourceAdapter(
            Context context,
            List<AvailableResourceModel.ResourceDetail> listDataHeader,
            HashMap<String, List<AvailableResourceModel.ResourceDetail.AssignedTaskListDetails>> listChildData) {

        this._context = context;
        this._listDataHeader = listDataHeader;
        this.dataChildMap = listChildData;
        inf = LayoutInflater.from(context);
    }

    @Override
    public AvailableResourceModel.ResourceDetail.AssignedTaskListDetails getChild(int groupPosition, int childPosititon) {
        return this.dataChildMap.get(this._listDataHeader.get(groupPosition).getName()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {

        Log.i("Available Resource", "-->getChildView<--"+groupPosition+"-"+childPosition);
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            //LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.available_res_child_row, null);
            childViewHolder = new ChildViewHolder();

            /**
             * TextView
             */
            childViewHolder.child_row_title = (TextView) convertView.findViewById(R.id.tv_child_row_title);
            childViewHolder.child_row_project_name = (TextView) convertView.findViewById(R.id.tv_child_row_project_name);
            childViewHolder.child_row_created_date = (TextView) convertView.findViewById(R.id.tv_child_row_created_date);
            childViewHolder.child_row_title_list = (TextView) convertView.findViewById(R.id.tv_child_row_title_list);
            childViewHolder.tv_task_project_name_list = (TextView) convertView.findViewById(R.id.tv_task_project_name_list);
            childViewHolder.child_row_created_date_list = (TextView) convertView.findViewById(R.id.tv_child_row_created_date_list);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        AvailableResourceModel.ResourceDetail.AssignedTaskListDetails assignedTaskListDetails = getChild(groupPosition, childPosition);

       Log.d("AvailableResources-->"," "+getChild(groupPosition,childPosition));

        if(convertView != null) {

            //Header Show Or Hide For Child List
            if (childPosition == 0) {
                childViewHolder.child_row_title.setVisibility(View.VISIBLE);
                childViewHolder.child_row_project_name.setVisibility(View.VISIBLE);
                childViewHolder.child_row_created_date.setVisibility(View.VISIBLE);
            } else{
                childViewHolder.child_row_title.setVisibility(View.GONE);
                childViewHolder.child_row_project_name.setVisibility(View.GONE);
                childViewHolder.child_row_created_date.setVisibility(View.GONE);
            }

            //Set Title For Child view
            childViewHolder.child_row_title_list.setText(assignedTaskListDetails.getTitle());
            //Set Project Name For Child View
            childViewHolder.tv_task_project_name_list.setText(assignedTaskListDetails.getProject().getName());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm a");
            String date = dateFormat.format(Long.parseLong(assignedTaskListDetails.getCreatedDate()));
            //Set Created Date
            childViewHolder.child_row_created_date_list.setText(date);
        }

        return convertView;
    }


    class ChildViewHolder {

        TextView child_row_title;
        TextView child_row_project_name;
        TextView child_row_created_date;
        TextView child_row_title_list;
        TextView tv_task_project_name_list;
        TextView child_row_created_date_list;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(this.dataChildMap.get(this._listDataHeader.get(groupPosition).getName()) != null) {
            return this.dataChildMap.get(this._listDataHeader.get(groupPosition).getName()).size();
        }
        return 0;
    }

    @Override
    public AvailableResourceModel.ResourceDetail getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        Log.i("Available Resource", "-->getGroupView<--"+groupPosition+"-"+groupPosition);
        if (convertView == null) {
            //LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.available_res_header_row, null);
            parentViewHolder = new ParentViewHolder();
            parentViewHolder.tv_available_res_name = (TextView) convertView.findViewById(R.id.tv_available_res_name);
            parentViewHolder.tv_available_res_task_counter = (TextView) convertView.findViewById(R.id.tv_available_res_task_counter);
            convertView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }

        AvailableResourceModel.ResourceDetail resourceDetail =  getGroup(groupPosition);

        //Set Name For Header View
        parentViewHolder.tv_available_res_name.setText(resourceDetail.getName());
        //Set Task Count For Header View
        parentViewHolder.tv_available_res_task_counter.setText(resourceDetail.getTaskCount());

        return convertView;
    }


    class ParentViewHolder {
        TextView tv_available_res_name;
        TextView tv_available_res_task_counter;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
