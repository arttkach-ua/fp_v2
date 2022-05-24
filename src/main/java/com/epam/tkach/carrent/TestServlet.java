package com.epam.tkach.carrent;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    final Logger logger = LogManager.getLogger(TestServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("operation");
        if (op.equals("car_model")){
            int id = RequestReader.readIntFromRequest(request, PageParameters.ID);
            CarModelRepoI modelRepo = new CarModelRepoMySql();
            List<CarModel> modelList = null;
            try{
                modelList = modelRepo.getModelsByBrand(id);
                Gson json = new Gson();
                String JsonModelList = json.toJson(modelList);
                response.setContentType("text/html");
                response.getWriter().write(JsonModelList);

            } catch (CarModelRepoException e) {
                e.printStackTrace();
            }
        }
    }
}
