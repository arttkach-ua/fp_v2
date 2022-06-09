package com.epam.tkach.carrent;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.entity.CompleteSet;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.epam.tkach.carrent.model.service.CompleteSetService;
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

@WebServlet("/GetCompleteSets")
public class GetCompleteSets extends HttpServlet {
    final Logger logger = LogManager.getLogger(GetCompleteSets.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("operation");
        if (op.equals("complete_set")){
            try{
                int id = RequestReader.readIntFromRequest(request, PageParameters.ID);
                List<CompleteSet> entityList = null;
                entityList = CompleteSetService.findByCarModelId(id);

                Gson json = new Gson();
                String JsonEntityList = json.toJson(entityList);
                response.setContentType("text/html");
                response.getWriter().write(JsonEntityList);

            } catch (CompleteSetsRepoException e) {
                logger.error(e);
            }
        }
    }
}
