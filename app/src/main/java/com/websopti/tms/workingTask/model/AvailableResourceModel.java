package com.websopti.tms.workingTask.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Padaliya on 10/1/2016.
 */
public class AvailableResourceModel {

    @SerializedName("availableResources")
    private List<AvailableResourceModel.ResourceDetail> availableResources;

    public List<AvailableResourceModel.ResourceDetail> getAvailableResources() {
        return availableResources;
    }

    public void setAvailableResources(ArrayList<AvailableResourceModel.ResourceDetail> availableResources) {
        this.availableResources = availableResources;
    }

    public class ResourceDetail {

        @SerializedName("id")
        private String id;
        @SerializedName("omsId")
        private String omsId;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("role")
        private String role;
        @SerializedName("taskCount")
        private String taskCount;
        @SerializedName("USER")
        private String USER;

        @SerializedName("assignedTaskListAPI")
        private List<AvailableResourceModel.ResourceDetail.AssignedTaskListDetails> assignedTaskListDetailsList;

        public List<AvailableResourceModel.ResourceDetail.AssignedTaskListDetails> getAssignedTaskListDetailsList() {
            return assignedTaskListDetailsList;
        }

        public void setAssignedTaskListDetailsList(List<AvailableResourceModel.ResourceDetail.AssignedTaskListDetails> assignedTaskListDetailsList) {
            this.assignedTaskListDetailsList = assignedTaskListDetailsList;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOmsId() {
            return omsId;
        }

        public void setOmsId(String omsId) {
            this.omsId = omsId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getTaskCount() {
            return taskCount;
        }

        public void setTaskCount(String taskCount) {
            this.taskCount = taskCount;
        }


        public String getUSER() {
            return USER;
        }

        public void setUSER(String USER) {
            this.USER = USER;
        }

        public class AssignedTaskListDetails {

            @SerializedName("createdDate")
            private String createdDate;
            @SerializedName("modifiedDate")
            private String modifiedDate;
            @SerializedName("id")
            private String id;
            @SerializedName("project")
            private Project project;
            @SerializedName("title")
            private String title;
            @SerializedName("description")
            private String description;
            @SerializedName("assignedTo")
            private String assignedTo;
            @SerializedName("assignedBy")
            private String assignedBy;
            @SerializedName("status")
            private String status;
            @SerializedName("taskType")
            private String taskType;
            @SerializedName("priority")
            private String priority;
            @SerializedName("totalHours")
            private String totalHours;
            @SerializedName("workingSince")
            private String workingSince;
            @SerializedName("expectedTimeInHours")
            private String expectedTimeInHours;
            @SerializedName("hoursInTime")
            private String hoursInTime;
            @SerializedName("actualTimeInHours")
            private String actualTimeInHours;
            @SerializedName("completionPercentage")
            private String completionPercentage;
            @SerializedName("notificationIds")
            private String notificationIds;
            @SerializedName("notifyTo")
            private String notifyTo;
            @SerializedName("taskEditedDate")
            private String taskEditedDate;
            @SerializedName("deleted")
            private String deleted;

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getModifiedDate() {
                return modifiedDate;
            }

            public void setModifiedDate(String modifiedDate) {
                this.modifiedDate = modifiedDate;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Project getProject() {
                return project;
            }

            public void setProject(Project project) {
                this.project = project;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAssignedTo() {
                return assignedTo;
            }

            public void setAssignedTo(String assignedTo) {
                this.assignedTo = assignedTo;
            }

            public String getAssignedBy() {
                return assignedBy;
            }

            public void setAssignedBy(String assignedBy) {
                this.assignedBy = assignedBy;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTaskType() {
                return taskType;
            }

            public void setTaskType(String taskType) {
                this.taskType = taskType;
            }

            public String getPriority() {
                return priority;
            }

            public void setPriority(String priority) {
                this.priority = priority;
            }

            public String getTotalHours() {
                return totalHours;
            }

            public void setTotalHours(String totalHours) {
                this.totalHours = totalHours;
            }

            public String getWorkingSince() {
                return workingSince;
            }

            public void setWorkingSince(String workingSince) {
                this.workingSince = workingSince;
            }

            public String getExpectedTimeInHours() {
                return expectedTimeInHours;
            }

            public void setExpectedTimeInHours(String expectedTimeInHours) {
                this.expectedTimeInHours = expectedTimeInHours;
            }

            public String getHoursInTime() {
                return hoursInTime;
            }

            public void setHoursInTime(String hoursInTime) {
                this.hoursInTime = hoursInTime;
            }

            public String getActualTimeInHours() {
                return actualTimeInHours;
            }

            public void setActualTimeInHours(String actualTimeInHours) {
                this.actualTimeInHours = actualTimeInHours;
            }

            public String getCompletionPercentage() {
                return completionPercentage;
            }

            public void setCompletionPercentage(String completionPercentage) {
                this.completionPercentage = completionPercentage;
            }

            public String getNotificationIds() {
                return notificationIds;
            }

            public void setNotificationIds(String notificationIds) {
                this.notificationIds = notificationIds;
            }

            public String getNotifyTo() {
                return notifyTo;
            }

            public void setNotifyTo(String notifyTo) {
                this.notifyTo = notifyTo;
            }

            public String getTaskEditedDate() {
                return taskEditedDate;
            }

            public void setTaskEditedDate(String taskEditedDate) {
                this.taskEditedDate = taskEditedDate;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public class Project {

                @SerializedName("createdDate")
                private String createdDate;
                @SerializedName("modifiedDate")
                private String modifiedDate;
                @SerializedName("id")
                private String id;
                @SerializedName("omsId")
                private String omsId;
                @SerializedName("name")
                private String name;
                @SerializedName("description")
                private String description;
                @SerializedName("status")
                private String status;
                @SerializedName("repositoryName")
                private String repositoryName;
                @SerializedName("repositoryUrl")
                private String repositoryUrl;
                @SerializedName("repositoryUsername")
                private String repositoryUsername;
                @SerializedName("repositoryPassword")
                private String repositoryPassword;
                @SerializedName("selectedRoleIndexes")
                private String selectedRoleIndexes;
                @SerializedName("team")
                private String team;

                public String getCreatedDate() {
                    return createdDate;
                }

                public void setCreatedDate(String createdDate) {
                    this.createdDate = createdDate;
                }

                public String getModifiedDate() {
                    return modifiedDate;
                }

                public void setModifiedDate(String modifiedDate) {
                    this.modifiedDate = modifiedDate;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getOmsId() {
                    return omsId;
                }

                public void setOmsId(String omsId) {
                    this.omsId = omsId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getRepositoryName() {
                    return repositoryName;
                }

                public void setRepositoryName(String repositoryName) {
                    this.repositoryName = repositoryName;
                }

                public String getRepositoryUrl() {
                    return repositoryUrl;
                }

                public void setRepositoryUrl(String repositoryUrl) {
                    this.repositoryUrl = repositoryUrl;
                }

                public String getRepositoryUsername() {
                    return repositoryUsername;
                }

                public void setRepositoryUsername(String repositoryUsername) {
                    this.repositoryUsername = repositoryUsername;
                }

                public String getRepositoryPassword() {
                    return repositoryPassword;
                }

                public void setRepositoryPassword(String repositoryPassword) {
                    this.repositoryPassword = repositoryPassword;
                }

                public String getSelectedRoleIndexes() {
                    return selectedRoleIndexes;
                }

                public void setSelectedRoleIndexes(String selectedRoleIndexes) {
                    this.selectedRoleIndexes = selectedRoleIndexes;
                }

                public String getTeam() {
                    return team;
                }

                public void setTeam(String team) {
                    this.team = team;
                }
            }
        }
    }

}
