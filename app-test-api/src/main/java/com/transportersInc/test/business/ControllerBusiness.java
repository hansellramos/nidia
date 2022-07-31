package com.transportersInc.test.business;


import com.transportersInc.test.model.Container;
import com.transportersInc.test.entities.Stats;
import com.transportersInc.test.settings.ConstantsDescription;
import com.transportersInc.test.utils.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transportersInc.test.daos.StatsDAO;
import com.transportersInc.test.dto.RequestDTO;
import com.transportersInc.test.dto.ResponseDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Component
@Slf4j
public class ControllerBusiness {

    @Autowired
    private StatsDAO statsDAO;


    @Autowired
    private ConstantsDescription constantsDescription;

    /**
     * metodo que se encarga de despachar container con respecto a reglas de negocios por presupuesto
     * @param req request Body
     * @return Response Entity
     */
    public ResponseEntity<?> containers(RequestDTO req) {
        log.info("Start: " + ControllerBusiness.class.getName() + "/" + Thread.currentThread().getStackTrace()[1].getMethodName());
        log.trace("Request: " + req);
        HttpStatus httpStatus = HttpStatus.OK;
        List<Container> container;
        String resp;
        try {
            if (Validations.sumValuesArray(req.getData(),req.getBudget())) {
                List<String> repeatedValuesInArray = Validations.repeatedValuesInArray(req.getData());
                if (repeatedValuesInArray.isEmpty()) {
                    container = Validations.compareTwoArrays(req.getData(),statsDAO.getContainers());
                    if (container.isEmpty()) {
                        container = new ArrayList<Container>();
                        Collections.sort(req.getData(), (x, y) -> new Double(y.getTransportCost()).compareTo(new Double(x.getTransportCost())));
                        double sum = 0, containersDispatched = 0, containersNotDispatched = 0;
                        for(Container c: req.getData()){
                            if(sum+c.getTransportCost()<=req.getBudget()){
                                sum += c.getTransportCost();
                                containersDispatched += c.getContainerPrice();
                                container.add(c);
                            }else{
                                containersNotDispatched += c.getContainerPrice();
                            }
                        }
                        statsDAO.insert(Stats.builder().budgetUsed(req.getBudget()).containersDispatched(Validations.formatearDecimales(containersDispatched,2)).containersNotDispatched(Validations.formatearDecimales(containersNotDispatched,2)).containers(container).build());
                        resp = constantsDescription.getSuccessful().replace("{0}", container.toString());
                    }else{
                        resp = constantsDescription.getDispatched().replace("{0}", container.toString());
                    }
                }else{
                    resp = constantsDescription.getRepeated().replace("{0}", repeatedValuesInArray.toString());
                }
            }else{
                resp = constantsDescription.getBudget();
            }
            log.trace("Response: " + resp);
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            resp = constantsDescription.getErrorExceptionService();
            log.error(constantsDescription.getErrorExceptionService() + " - " + e.getMessage());
        }
        log.info("End " + ControllerBusiness.class.getName() + "/" + Thread.currentThread().getStackTrace()[1].getMethodName());
        return new ResponseEntity(resp,httpStatus);
    }

    /**
     * metodo que se encarga de sacar el reporte de las estadisticas
     * @param startDate fecha inicial para filtar la busqueda del reporte, campo no obligatorio
     * @param endDate fecha fin para filtar la busqueda del report, campo no obligatorio
     * @return Response Entity
     */
    public ResponseEntity<ResponseDTO> stats(Date startDate, Date endDate) {
        log.info("Start: " + ControllerBusiness.class.getName() + "/" + Thread.currentThread().getStackTrace()[1].getMethodName());
        log.trace("Request: " + startDate + " - endDate");
        HttpStatus httpStatus;
        ResponseDTO resp;
        try {
            resp = statsDAO.get(startDate,endDate);
            httpStatus = HttpStatus.OK;
            log.trace("Response: " + resp);
        } catch (Exception e) {
            resp = null;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error(constantsDescription.getErrorExceptionService() + " - " + e.getMessage());
        }
        log.info("End " + ControllerBusiness.class.getName() + "/" + Thread.currentThread().getStackTrace()[1].getMethodName());
        return new ResponseEntity(resp, httpStatus);
    }

}
