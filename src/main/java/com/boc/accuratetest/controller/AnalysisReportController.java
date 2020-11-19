package com.boc.accuratetest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boc.accuratetest.acl.CoverageReportRank;
import com.boc.accuratetest.annotation.SecurityManagement;
import com.boc.accuratetest.biz.AllMethodsBiz;
import com.boc.accuratetest.constant.NotSelectProductionTaskException;
import com.boc.accuratetest.constant.ProductionTaskSession;
import com.boc.accuratetest.pojo.AllMethods;
import com.boc.accuratetest.pojo.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("analysisReport")
public class AnalysisReportController {
	@Autowired
	private AllMethodsBiz allMethodsBiz;
	@Autowired
	private ChangeCodeController changeCodeController;
	/**
	 * 	分析报告页面
	 * @return
	 */
	@SecurityManagement(CoverageReportRank.class)
	@RequestMapping("index")
	public String index(HttpSession session) {
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}
		return "analysisReport/index";
	}
	/**
	 * 	分析报告数据
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @return
	 */
	@SecurityManagement(CoverageReportRank.class)
	@RequestMapping("getAll")
	@ResponseBody
	public JSONObject getAll(Integer pageNumber,Integer pageSize,String search,HttpSession session) {
		JSONObject json = new JSONObject();
		User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		// 统计每个包的各自类数量、方法数量、增加方法数量，及其已测试数量
		List<AllMethods> statisticClassNumber = allMethodsBiz.statisticClassNumber(productionTaskNumber);
		/*统计当前生产任务的数据，总和数据*/
		int classNumber = 0; // 类的数量
		int testedclassNumber = 0; // 已测试的类的数量
		int methodNumber = 0; // 方法数量
		int methodTestedNumber = 0; // 已测试方法数量
		int addFunNumber = 0; // 新增方法数量
		int addFunTestedNumber = 0; // 新增方法已测试数量
		int modifyFunNumber = 0; // 修改方法数量
		int modifyFunTestedNumber = 0; // 修改方法已测试数量
		for (AllMethods m : statisticClassNumber) {
			classNumber += m.getClassNumber();
			testedclassNumber += m.getTestedclassNumber();
			methodNumber += m.getMethodNumber();
			methodTestedNumber += m.getMethodTestedNumber();
			addFunNumber += m.getAddFunNumber();
			addFunTestedNumber += m.getAddFunTestedNumber();
			modifyFunNumber += m.getModifyFunNumber();
			modifyFunTestedNumber += m.getModifyFunTestedNumber();
		}
		System.out.println("类的数量："+classNumber);
		System.out.println("已测试类的数量："+testedclassNumber);
		System.out.println("方法数量："+methodNumber);
		System.out.println("已测试方法数量："+methodTestedNumber);
		System.out.println("新增方法数量："+addFunNumber);
		System.out.println("已测试新增方法数量："+addFunTestedNumber);
		System.out.println("修改方法数量："+modifyFunNumber);
		System.out.println("已测试修改方法数量："+modifyFunTestedNumber);
		
		/*json.put("classNumber", classNumber);
		json.put("testedclassNumber", testedclassNumber);
		json.put("methodNumber", methodNumber);
		json.put("methodTestedNumber", methodTestedNumber);
		json.put("addFunNumber", addFunNumber);
		json.put("addFunTestedNumber", addFunTestedNumber);
		json.put("modifyFunNumber", modifyFunNumber);
		json.put("modifyFunTestedNumber", modifyFunTestedNumber);*/
		// 相关案例数、推荐案例数、已执行案例数， 都要有相关比例
		
		JSONObject recommendTestExample = changeCodeController.recommendTestExample(1, 65565, null, session);
		
		int testExampleNumber = 0; // 关联案例数量
		int recommendTestExampleNumber = 0; // 推荐案例数量
		Object object = recommendTestExample.get("rows");
		JSONArray ja = JSONArray.fromObject(object);
		for (Object object2 : ja) {
			JSONObject fromObject = JSONObject.fromObject(object2);
			int teType = (Integer)fromObject.get("teType");
			testExampleNumber++;
			if(teType == 2)recommendTestExampleNumber++;
		}
		
		List<AllMethods> statisticClassNumber2 = new ArrayList<>();
		AllMethods m = new AllMethods();
		m.setClassNumber(classNumber);
		m.setTestedclassNumber(testedclassNumber);
		m.setMethodNumber(methodNumber);
		m.setMethodTestedNumber(methodTestedNumber);
		m.setAddFunNumber(addFunNumber);
		m.setAddFunTestedNumber(addFunTestedNumber);
		m.setModifyFunNumber(modifyFunNumber);
		m.setModifyFunTestedNumber(modifyFunTestedNumber);
		
		m.setTestExampleNumber(testExampleNumber);
		m.setRecommendTestExampleNumber(recommendTestExampleNumber);
		m.setExecutedTestExampleNumber(1); // 假数据
		statisticClassNumber2.add(m);
		json.put("rows", statisticClassNumber2);
		json.put("total", statisticClassNumber2.size());
		return json;
	}
	@SecurityManagement(CoverageReportRank.class)
	@RequestMapping("getRecommendTestExampleData")
	@ResponseBody
	public JSONObject getRecommendTestExampleData(HttpSession session) {
		JSONObject json = new JSONObject();
		JSONObject recommendTestExample = changeCodeController.recommendTestExample(1, 65565, null, session);
		
		int testExampleNumber = 0; // 关联案例数量
		int recommendTestExampleNumber = 0; // 推荐案例数量
		Object object = recommendTestExample.get("rows");
		JSONArray ja = JSONArray.fromObject(object);
		for (Object object2 : ja) {
			JSONObject fromObject = JSONObject.fromObject(object2);
			int teType = (Integer)fromObject.get("teType");
			testExampleNumber++;
			if(teType == 2)recommendTestExampleNumber++;
		}
		
		System.out.println(testExampleNumber);
		json.put("testExampleNumber", testExampleNumber);
		json.put("recommendTestExampleNumber", recommendTestExampleNumber);
		return json;
	}
}

