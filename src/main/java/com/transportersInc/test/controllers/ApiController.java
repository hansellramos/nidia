package com.transportersInc.test.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.transportersInc.test.business.ControllerBusiness;
import com.transportersInc.test.dto.RequestDTO;
import com.transportersInc.test.dto.ResponseDTO;

import java.sql.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({ "test" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Accion exitosa"), @ApiResponse(code = 500, message = "Internal Server Error") })
@Api(value = "ApiController", description = "Despacho de contenedores y reporte", tags = { "", "" })
public class ApiController {

    @Autowired
    private ControllerBusiness controllerBusiness;


    /**
     * Consumo REST para despachar container con respecto a reglas de negocios por presupuesto
     * @param req request Body
     * @return Response Entity
     */
    @ApiOperation(value = "Despacho de contenedores")
    @RequestMapping(value = { "/containers" }, method = { RequestMethod.POST } ,produces = "application/json")
    public ResponseEntity<?> containers(@RequestBody RequestDTO req) {
        return this.controllerBusiness.containers(req);
    }

    /**
     * Consumo REST para sacar el reporte de las estadisticas
     * @param startDate fecha inicial para filtar la busqueda del reporte, campo no obligatorio
     * @param endDate fecha fin para filtar la busqueda del report, campo no obligatorio
     * @return Response Entity
     */
    @ApiOperation(value = "Reporte de estadísticas")
    @RequestMapping(value = { "/stats" }, method = { RequestMethod.GET } ,produces = "application/json")
    public ResponseEntity<ResponseDTO> stats(
            @ApiParam(value = "Fecha inicio de busqueda formato YYYY-MM-DD, no obligatorio")
            @RequestParam(name = "startDate", required = false) Date startDate,
            @ApiParam(value = "Fecha fin de busqueda formato YYYY-MM-DD, no obligatorio")
            @RequestParam(name = "endDate", required = false) Date endDate
    ) {
        return this.controllerBusiness.stats(startDate,endDate);
    }

}
