package ca.sheridancollege.prince.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.prince.beans.*;
import ca.sheridancollege.prince.database.*;
import jakarta.servlet.http.HttpSession;


@Controller

public class HomeController 
{	
	@GetMapping("/")
	public String index(Model model)
	{	
		model.addAttribute("mission",new Mission());
		return "index";
	}
	
	@Autowired
	private DatabaseAccess database;
	
	public HomeController(DatabaseAccess database)
	{
		this.database=database;
	}
	
	@GetMapping("/createMission")
    public String createMissionPage(Model model) 
	{
        
		model.addAttribute("mission",new Mission());
        return "create.html"; 
    }
	
	@PostMapping("/createNewMission")
    public String createMissionPage(Model model, HttpSession session, @ModelAttribute Mission miss) 
	{
		model.addAttribute("mission",new Mission());
		int r=database.createMission(miss);
		if(r==1)
		{
			session.setAttribute("missionList", database.getMissionList(miss));
			return "view.html";
		}
		else
		{
			System.out.println("Can't Create a Record");
			return "create.html"; 
		}
        
    }
	
	@PostMapping("/showMissions")
    public String showMissions(Model model,@ModelAttribute Mission miss,HttpSession session) 
	{    		
		session.setAttribute("missionList",database.getMissionList(miss)); //select query
		model.addAttribute("mission",new Mission());
		return "view.html";
    }
	
	@GetMapping("/deleteMissionById/{id}")
	public String deleteSt(Model model,@PathVariable Long id,HttpSession session)
	{
		Mission miss=database.deletemissionById(id);
		session.setAttribute("missionList",database.getMissionList(miss));
		return "view.html";	
	}
	
	@GetMapping("/editMissionById/{id}")
	public String editPage(Model model,@PathVariable Long id)
	{
		Mission mission1=database.getMission(id);
		if(mission1==null)
		{
			System.out.println("no result for mission id="+id);
			return "redirect:/";
		}
		model.addAttribute("mission",mission1);
		return "edit.html";
	}
	
	@PostMapping("/updateMission")
	public String updateStu(Model model,@ModelAttribute Mission mis,HttpSession session)
	
	{
		int rvalue=database.updateMission1(mis);
		System.out.println("returnvalue"+rvalue);
		session.setAttribute("missionList",database.getMissionList(mis));
		return "view.html";
	}
}