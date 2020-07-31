package com.boc.accuratetest.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boc.accuratetest.acl.KnowledgeRank;
import com.boc.accuratetest.annotation.SecurityIgnoreHandler;
import com.boc.accuratetest.annotation.SecurityManagement;
import com.boc.accuratetest.biz.MethodChainOriginalBiz;
import com.boc.accuratetest.biz.TestingExampleBiz;
import com.boc.accuratetest.constant.NotLoginInException;
import com.boc.accuratetest.constant.NotSelectProductionTaskException;
import com.boc.accuratetest.constant.ProductionTaskSession;
import com.boc.accuratetest.pojo.ExampleRefMethodChain;
import com.boc.accuratetest.pojo.MethodChainOriginal;
import com.boc.accuratetest.pojo.TestingExample;
import com.boc.accuratetest.pojo.User;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.boc.accuratetest.biz.ExampleRefMethodChainBiz;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("testingExample")
public class TestingExampleController {
	@Autowired
	private TestingExampleBiz testingExampleBiz;
	@Autowired
	private MethodChainOriginalBiz methodChainOriginalBiz;
	@Autowired
	private ExampleRefMethodChainBiz exampleRefMethodChainBiz;
	// < 测试用例主键  ,  最后一行数据@测试用例所在的服务器ip >
		Map<Integer,String> testExampleIdRef = new ConcurrentHashMap<Integer, String>();
	/**
	 * 	跳转到知识库创建页面
	 * @return
	 */
	@SecurityManagement(KnowledgeRank.class)
	@RequestMapping("buildKnowleage")
	public String buildKnowleage(@ModelAttribute("success") String success, Model model,HttpSession session) {
		Object u = session.getAttribute(ProductionTaskSession.loginUser);
		if(null == u) {
			throw new NotLoginInException("您尚未登陆");
		}
		System.out.println("拿到重定向得到的参数success:" + success);
		if(success == null || success.equals("")) success = "-1";
		model.addAttribute("success", success);
		return "build_knowleage";
	}
	/**
	 * 	获取测试用例列表
	 * @param pageNumber
	 * @param pageSize
	 * @param search
	 * @return
	 */
	@SecurityManagement(KnowledgeRank.class)
	@RequestMapping("getAll")
	@ResponseBody
	public JSONObject getAll(Integer pageNumber,Integer pageSize,String search,HttpSession session) {
		JSONObject json = new JSONObject();
		List<TestingExample> page = testingExampleBiz.page(pageNumber, pageSize, search,null);
		Integer total = testingExampleBiz.findTotal(search,null);
		json.put("rows", page);
		json.put("total", total);
		return json;
	}
	/**
	 * 	开始执行测试用例。获取最后一行数据，记录下来
	 * @param testExampleId
	 * @return
	 */
	@SecurityManagement(KnowledgeRank.class)
	@RequestMapping("testExampleStart")
	@ResponseBody
	public JSONObject testExampleStart(String ipOnTestExample, Integer testExampleId,HttpSession session) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		/*User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}*/
		if(StringUtils.isEmpty(ipOnTestExample) || StringUtils.isEmpty(testExampleId) ) {
			return json;
		}
		ipOnTestExample = ipOnTestExample.trim();
		Socket client = null;
    	OutputStream os = null;        
    	PrintWriter pw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
		try {
        	client = new Socket(ipOnTestExample, 8765);
        	os = client.getOutputStream();
        	pw = new PrintWriter(os);
            pw.write("chazhuang.txt");
            pw.flush();
            client.shutdownOutput();// 关闭输出流
            is = client.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String lastLine = null;
            String info = null;
            while ((info = br.readLine()) != null) {
            	lastLine=info;// 记录最后一行
            }
            if(null == lastLine)lastLine = "";
            testExampleIdRef.put(testExampleId, lastLine+"@"+ipOnTestExample);
            System.out.println("最后一行："+lastLine);
        } catch (UnknownHostException e) {
        	e.printStackTrace();
        	return json;
        } catch (IOException e) {
        	e.printStackTrace();
        	return json;
        }finally {
            try {
            	br.close();
            	isr.close();
            	is.close();
            	pw.close();
            	os.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		json.put("success", true);
		return json;
	}
	/**
	 * 	结束测试用例。建立知识库
	 * @param ipOnTestExample
	 * @param testExampleId
	 * @return
	 */
	@SecurityManagement(KnowledgeRank.class)
	@RequestMapping("testExampleEnd")
	@ResponseBody
	public JSONObject testExampleEnd(String ipOnTestExample, Integer testExampleId,HttpSession session) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		/*User user = (User)(session.getAttribute(ProductionTaskSession.loginUser));
		String productionTaskNumber = user.getProductionTaskNumber();
		if(StringUtils.isEmpty(productionTaskNumber)) {
			throw new NotSelectProductionTaskException("请选择一个生产任务编号");
		}*/
		if(StringUtils.isEmpty(testExampleId) ) {
			return json;
		}
		Socket client = null;
    	OutputStream os = null;
    	PrintWriter pw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
		try {
			String[] split = testExampleIdRef.get(testExampleId).split("@");
        	client = new Socket(split[1], 8765);
        	os = client.getOutputStream();
        	pw = new PrintWriter(os);
            pw.write("chazhuang.txt");
            pw.flush();
            client.shutdownOutput();// 关闭输出流
            is = client.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            List<MethodChainOriginal> ms = new ArrayList<>();
            if(StringUtils.isEmpty(split[0])) { // 只可能是第一个测试用例
            	String info = null;
            	while ((info = br.readLine()) != null) {
            		// 全部读取，保存到数据库
            		MethodChainOriginal chainOriginal = insertPrepare(info);
            		chainOriginal.setProductionTaskNumber("");
            		ms.add(chainOriginal);
            	}
            }else {
            	int start = 0;
            	String info = null;
            	while ((info = br.readLine()) != null) {
            		if(start == 1) { // 读取，保存到数据库
            			MethodChainOriginal chainOriginal = insertPrepare(info);
            			chainOriginal.setProductionTaskNumber("");
                		ms.add(chainOriginal);
            		}
            		if(info.equals(split[0])) { // 从下一行开始读取
            			start = 1;
            		}
            	}
            }
            methodChainOriginalBiz.insertBatch(ms); // 批量存储方法链数据
            // 收集方法链表主键，测试用例、方法链，做关联
            // 如果该测试用例已关联方法链，先取消关联
            exampleRefMethodChainBiz.deleteByTestingExampleId(testExampleId);
            List<ExampleRefMethodChain> refs = new ArrayList<>();
            for (MethodChainOriginal m : ms) {
            	ExampleRefMethodChain ref = new ExampleRefMethodChain();
				ref.setTestingExampleId(testExampleId);
				ref.setMethodChainOriginalId(m.getId());
				refs.add(ref);
			}
            exampleRefMethodChainBiz.insertBatch(refs);
        } catch (UnknownHostException e) {
        	e.printStackTrace();
        	return json;
        } catch (IOException e) {
        	e.printStackTrace();
        	return json;
        }finally {
            try {
            	br.close();
            	isr.close();
            	is.close();
            	pw.close();
            	os.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		json.put("success", true);
		return json;
	}
	/**
	 * 解析方法链中的一行
	 * @param tempStr 1594003355480.com.boc.accuratetest.biz.impl.TestingExampleBizImpl.page(Integer,Integer,String)
	 * @return
	 */
	private MethodChainOriginal insertPrepare(String tempStr) {
		MethodChainOriginal m = new MethodChainOriginal();
    	String params = tempStr.substring(tempStr.indexOf("(")+1, tempStr.indexOf(")")); // 参数
    	String split = tempStr.split("\\(")[0];
    	String callTime = split.substring(0, split.indexOf(".")); // 调用时间
    	String[] split2 = split.split("\\.");
    	String methodName = split2[split2.length-1]; // 方法名
    	String className = split2[split2.length-2]; // 类名
    	String packageName = tempStr.substring(tempStr.indexOf(callTime)+callTime.length()+1, 
    			tempStr.indexOf(className)-1); // 包路径
    	m.setPackageName(packageName);
    	m.setJavabeanName(className);
    	m.setMethodName(methodName);
    	m.setParamType(params);
    	long l = Long.valueOf(callTime);
    	m.setCallTimeLong(l);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
    	m.setCallTime(df.format(new Date(l)));
    	return m;
	}
	/**
	 * 	上传测试用例excel
	 * @param file
	 * @param model
	 * @return
	 */
	@SecurityManagement(KnowledgeRank.class)
	@PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,RedirectAttributes model) {
		model.addFlashAttribute("success", "uploadFail");
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".xls")) {
        	return "redirect:/testingExample/buildKnowleage";
        }
        
        try{
            jxl.Workbook wb = Workbook.getWorkbook(file.getInputStream());
            wb.getNumberOfSheets(); // sheet数量
            Sheet sheet = wb.getSheet(0); // 读取第一个sheet
            int row_total = sheet.getRows();
            List<TestingExample> tes = new ArrayList<>();
            for (int j = 0; j < row_total; j++) {
                if(j == 0)continue; // 不读标题行
                    Cell[] cells = sheet.getRow(j);
                    TestingExample te = new TestingExample();
                    te.setBelongProduct(cells[1].getContents());// 所属产品
                    te.setFunction(cells[3].getContents());// 功能
                    te.setSubfunction(cells[4].getContents());// 子功能
                    te.setTestItem(cells[9].getContents());// 测试项 
                    te.setTestPoint(cells[10].getContents());// 测试点 
                    te.setTestCaseNumber(cells[11].getContents());// 测试案例编号
                    te.setTestOperationExplain(cells[13].getContents());// 测试操作说明
                    te.setExpectedResults(cells[14].getContents());// 预期结果
                    te.setProductionTaskNumber(cells[15].getContents().trim());// 生产任务编号
                    tes.add(te);
            }
            testingExampleBiz.batchSave(tes);
        }catch (IOException e) {
            e.printStackTrace();
            return "redirect:/testingExample/buildKnowleage";
        } catch (BiffException e){
            e.printStackTrace();
            return "redirect:/testingExample/buildKnowleage";
        }
        
        model.addFlashAttribute("success", "uploadSuccess");
        return "redirect:/testingExample/buildKnowleage";
    }
	public static boolean deleteFolder(String url) {
        File file = new File(url);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                String root = files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                //System.out.println(root);
                deleteFolder(root);
            }
            file.delete();
            return true;
        }
    }
	/**
	 * 	跳转到知识库详情页面
	 * @return
	 */
	@SecurityManagement(KnowledgeRank.class)
	@RequestMapping("knowleageDetail")
	public String knowleageDetail(HttpSession session) {
		Object u = session.getAttribute(ProductionTaskSession.loginUser);
		if(null == u) {
			throw new NotLoginInException("您尚未登陆");
		}
		return "knowleage_detail";
	}
	@SecurityManagement(KnowledgeRank.class)
	@RequestMapping("getMethodLinkByTestExampleId")
	@ResponseBody
	public JSONObject getMethodLinkByTestExampleId(Integer testExampleId) {
		JSONObject json = new JSONObject();
		List<MethodChainOriginal> mcos = methodChainOriginalBiz.getMethodLinkByTestExampleId(testExampleId);
		json.put("list", mcos);
		return json;
	}
}

