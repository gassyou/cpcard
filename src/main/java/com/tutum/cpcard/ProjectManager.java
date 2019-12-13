package com.tutum.cpcard;

import java.util.ArrayList;
import java.util.Iterator;

import com.tutum.cpcard.entities.Project;


public class ProjectManager {
	
    private static ProjectManager instance = new ProjectManager();
    private ArrayList<Project> projects = new ArrayList<Project>();

    private ProjectManager(){}

    public static ProjectManager getInstance()
    {
        return instance;
    }
    
    public ArrayList<Project> getAllProjects()
    {
        return  projects;
    }
    
    public Project addProject(String projectName)
    {
        if(isProjectExist(projectName))
        {
        	return null;
        }
        else
        {
        	Project newProject = new Project(projectName);
        	projects.add(newProject);
        	return newProject;
        }
    }
    
//   public void addProject(Project p)
//   {
//	   if(!isProjectExist(p))
//	   {
//		   projects.add(p);
//	   }
//   }
   
   public Project getProjectByName(String projectName) {
	   
       Iterator<Project> iterator = (Iterator<Project>) projects.iterator();
       while(iterator.hasNext()) {

           Project p = (Project)iterator.next();
           if(p.getName().equals(projectName)) {
               return p;
           }
       }
	   return null;
   }
   
   public void removeProject(Project p)
   {
	   if(isProjectExist(p.getName()))
	   {
		   projects.remove(p);
	   }
   }
   
   public void removeProject(String projectName)
   {
	   for (Project project : projects)
	   {
			if(project.getName().equals(projectName))
			{
				projects.remove(project);
			}
	   }
   }
   
    public boolean isProjectExist(String projectName)
    {
        Iterator<Project> iterator = (Iterator<Project>) projects.iterator();
        while(iterator.hasNext()) {

            Project p = (Project)iterator.next();
            if(p.getName().equals(projectName)) {
                return true;
            }
        }
        return false;
    }
    
//    public boolean isProjectExist(Project p)
//    {
//    	int index = projects.indexOf(p);
//    	if (index == -1)
//    	{
//    		return false;
//    	}
//    	else {
//			return true;
//		}
//    }

}
