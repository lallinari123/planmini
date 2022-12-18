package in.ashokit.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.constants.AppConstants;
import in.ashokit.entity.Plan;
import in.ashokit.service.PlanService;
import in.ashokit.props.AppProperties;
@RestController
public class PlanRestController {
	
	private PlanService planservice;
	
	
	private AppProperties appprops;
	Map<String,String> messages=new HashMap<>();
	
	PlanRestController(PlanService planservice,AppProperties appprops)
	{
		this.planservice=planservice;
		this.appprops=appprops;
		this.messages=appprops.getMessages();
	}
	
	
@GetMapping("/categories")
public ResponseEntity<Map<Integer,String>> planCategories()
{
	Map<Integer,String > categories=planservice.getPlanCategories();
	return new ResponseEntity<>(categories,HttpStatus.OK);
}
@PostMapping("/plan")
public ResponseEntity<String> saveplan(@RequestBody Plan plan)
{
	//Map<String,String> messages=appprops.getMessages();
	
	String responsemsg=AppConstants.EMPTY_STR;
	boolean issued=planservice.savePlan(plan);
	if(issued)
	{
		
		
		responsemsg=messages.get(AppConstants.PLAN_SAVE_SUCC);
	}
	else
	{
		responsemsg=messages.get(AppConstants.PLAN_SAVE_FAIL);
		
	}
	return new ResponseEntity<>(responsemsg,HttpStatus.CREATED);
}
@GetMapping("/plans")
public ResponseEntity<List<Plan>> plans()
{
	List<Plan> allplans=planservice.getAllPlans();
	return new ResponseEntity<>(allplans,HttpStatus.OK);
	
}
@GetMapping("/plan/{planId}")
public ResponseEntity<Plan>editPlan(@PathVariable Integer planId)
{
	Plan plan=planservice.getPlanById(planId);
	return new ResponseEntity<>(plan,HttpStatus.OK);
}
@DeleteMapping("/plan/{planId}")
public ResponseEntity<String> deleteplan(@PathVariable Integer planId)
{
	//Map<String,String> messages=appprops.getMessages();
	String msg=AppConstants.EMPTY_STR;
	boolean isDeleted=planservice.deleteById(planId);
	if(isDeleted)
	{
		msg=messages.get(AppConstants.PLAN_DELETE_SUCC);
	}
	else
	{
		msg=messages.get(AppConstants.PLAN_DELETE_FAIL);
	}
	return new ResponseEntity<>(msg,HttpStatus.OK);
}
@PutMapping("/plan")
public ResponseEntity<String> updatePlan(Plan plan)
{
	//Map<String,String> messages=appprops.getMessages();
	String msg=AppConstants.EMPTY_STR;
	boolean isUpdated=planservice.updateById(plan);
	if(isUpdated)
	{
		msg=messages.get(AppConstants.PLAN_UPDATE_SUCC);
	}
	else
	{
		msg=messages.get(AppConstants.PLAN_UPDATE_FAIL);
	}
	return new ResponseEntity<>(msg,HttpStatus.OK);
}
@PutMapping("/status-change")
public ResponseEntity<String> statusChange(@PathVariable Integer planId,@PathVariable String status)
{
	//Map<String,String> messages=appprops.getMessages();
	String msg="";
	boolean isstatuschange=planservice.planStatusChange(planId, status);
	if(isstatuschange)
	{
	
		msg=messages.get(AppConstants.PLAN_STATUS_SUCC);
	}
	else
	{
		msg=messages.get(AppConstants.PLAN_STATUS_FAIL);
	}
	return new ResponseEntity<>(msg,HttpStatus.OK);
}
}
	

