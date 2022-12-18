package in.ashokit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.entity.Plan;
import in.ashokit.entity.PlanCategory;
import in.ashokit.repo.PlanCategoryRepo;
import in.ashokit.repo.PlanRepo;
@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanCategoryRepo plancategoryrepo;
	@Autowired
	private PlanRepo planrepo;

	@Override
	public Map<Integer, String> getPlanCategories() {
		Map<Integer,String> categoryMap=new HashMap<>();
		List<PlanCategory> categories=plancategoryrepo.findAll();
	categories.forEach(category -> {
		categoryMap.put(category.getCategoryId(),category.getCategoryName());
		
	});
	return categoryMap;
	}

	@Override
	public boolean savePlan(Plan plan) {
		// TODO Auto-generated method stub
		Plan saved=planrepo.save(plan);
		
		return saved.getPlanId()!=null;
		
	}

	@Override
	public List<Plan> getAllPlans() {
		// TODO Auto-generated method stub
		return planrepo.findAll();
	}

	@Override
	public Plan getPlanById(Integer planId) {
		// TODO Auto-generated method stub
		Optional<Plan> findById=planrepo.findById(planId);
		if(findById.isPresent())
		{
			return findById.get();
		}
		else
		{
			return null;
		}
				
	}

	@Override
	public boolean updateById(Plan plan) {
		// TODO Auto-generated method stub
		planrepo.save(plan);
		return plan.getPlanId()!=null;
	}

	@Override
	public boolean deleteById(Integer planId) {
		// TODO Auto-generated method stub
		boolean status=false;
		try
		{
		planrepo.deleteById(planId);
		status=true;
	}
catch(Exception e)
		{
	e.printStackTrace();
		}
		return status;
	}
	@Override
	public boolean planStatusChange(Integer planId, String status) {
		// TODO Auto-generated method stub
		Optional<Plan> findById=planrepo.findById(planId);
		if(findById.isPresent())
		{
			Plan plan=findById.get();
			plan.setActiveSw(status);
			planrepo.save(plan);
			
			return true;
		}
		return false;
	}

}
