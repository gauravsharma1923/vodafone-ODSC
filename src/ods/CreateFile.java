package ods;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import beans.Request;
import beans.Response;

/**
 * Servlet for create a file in given location with db details
 * 
 * @author Himanshu Sharma
 */
public class CreateFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		Gson gson = new Gson();
		String status = "";
		List<String> lines = null;

		try {
			PropertiesParameterConfig parameterConfig = PropertiesParameterConfig.getPropertiesParameterObject();
			HashMap hashMap = parameterConfig.getHashMap();
			String DB_DOWN_FILE = (String) hashMap.get("DB_DOWN_FILE");

			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}
			System.out.println("-->" + sb.toString());

			Request req = (Request) gson.fromJson(sb.toString(), Request.class);
			
			String fileName = DB_DOWN_FILE+System.currentTimeMillis()+".txt";
			File file = new File(fileName);
			file.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(file);

			if (req.getType().equals("new")) {
				lines = Arrays.asList("insert into  ODS_credential values (CURRENT_TIMESTAMP," + req.getUserName() + ","
						+ req.getPassword() + "," + req.getSid() + "," + req.getExpiry() + ")|"+req.getRacName());
			} else {
				lines = Arrays.asList("update ODS_credential set INSERT_DATE_TIME=CURRENT_TIMESTAMP,Username='" + req.getUserName()
						+ "',PASSWORD='" + req.getPassword() + "',sid='" + req.getSid() + "',EXPIRY_DATE= DATE '"
						+ req.getExpiry() + "' WHERE SID='" + req.getCursid() + "' |"+req.getRacName());
			}
			Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);

			status = "success";
		} catch (Exception e) {
			e.printStackTrace();
			status = "Failed";
		}
		Response res = new Response();
		res.setStatus(status);
		response.getOutputStream().print(gson.toJson(res));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}
