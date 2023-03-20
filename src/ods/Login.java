package ods;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {

    static HashMap<String, String> hashMap = new HashMap<String, String>();

    public void readConfiguration() {
        try {
            PropertiesParameterConfig parameterConfig = PropertiesParameterConfig.getPropertiesParameterObject();
            hashMap = parameterConfig.getHashMap();
        } catch (Exception ex) {
            System.out.println("Could not read Properties file");
            ex.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String strUser = "";
        String strPass = "";
        try (PrintWriter out = response.getWriter()) {
            strUser = request.getParameter("user");
            strUser = strUser.trim();
            strPass = request.getParameter("password");
            strPass = strPass.trim();

            if (hashMap.isEmpty()) {
                readConfiguration();
            }
            String strLoginUser = (String) hashMap.get("LOGINUSER");
            String strLoginPass = (String) hashMap.get("LOGINPASSWORD");
            if (strUser.equals(strLoginUser) && strPass.equals(strLoginPass)) {
                out.println("SUCCESS");
            } else {
                out.println("FAIL");
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
 *      * Handles the HTTP <code>GET</code> method.
 *           *
 *                * @param request servlet request
 *                     * @param response servlet response
 *                          * @throws ServletException if a servlet-specific error occurs
 *                               * @throws IOException if an I/O error occurs
 *                                    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
 *      * Handles the HTTP <code>POST</code> method.
 *           *
 *                * @param request servlet request
 *                     * @param response servlet response
 *                          * @throws ServletException if a servlet-specific error occurs
 *                               * @throws IOException if an I/O error occurs
 *                                    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
 *      * Returns a short description of the servlet.
 *           *
 *                * @return a String containing servlet description
 *                     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}