package com.transportersInc.test.daos;

import com.google.gson.Gson;
import com.transportersInc.test.dto.ResponseDTO;
import com.transportersInc.test.entities.Stats;
import com.transportersInc.test.settings.Database;
import com.transportersInc.test.utils.ConnectionManager;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.stereotype.Repository;

@Repository("stats")
public class StatsDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private Database database;

    /**
     * Se consulta en la DB los contenedores que ya fueron despachados
     * @return Una lista de contenedores
     * @throws Exception
     */
    public List<String> getContainers() throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(database.getConsultContainers());
            return this.jdbcTemplate.query(sql.toString(), (rs, rowNum) ->
                    rs.getString("Containers"));
        } finally {
            ConnectionManager.closeJdbc(jdbcTemplate);
        }
    }

    /**
     * Se inserta la estadistica en DB del servicio despachado
     * @param stats Objeto estadistica con los campos necesarios a guardar
     * @throws Exception
     */
    public void insert(Stats stats) throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(database.getInsertStats());
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("containers_dispatched", stats.getContainersDispatched());
            params.addValue("containers_not_dispatched", stats.getContainersNotDispatched());
            params.addValue("budget_used", stats.getBudgetUsed());
            params.addValue("var_containers", new Gson().toJson(stats.getContainers()));
            jdbcTemplate.update(sql.toString(), params);
        } finally {
            ConnectionManager.closeJdbc(jdbcTemplate);
        }
    }

    /**
     * Se consulta las estadisticas en la DB
     * @param startDate fecha inicio de busqueda
     * @param endDate fecha fin de busqueda
     * @return un onbjeto con la suma de todas las estadisticas filtradas
     * @throws Exception
     */
    public ResponseDTO get(Date startDate, Date endDate) throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(database.getConsultStats());
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("start_date", startDate);
            params.addValue("end_date", endDate);
            return this.jdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(ResponseDTO.class)).get(0);
        } finally {
            ConnectionManager.closeJdbc(jdbcTemplate);
        }
    }

}
