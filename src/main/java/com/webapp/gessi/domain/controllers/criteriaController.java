package com.webapp.gessi.domain.controllers;

import com.webapp.gessi.config.DBConnection;
import com.webapp.gessi.data.criteria;
import com.webapp.gessi.domain.dto.ExclusionDTO;
import com.webapp.gessi.domain.dto.criteriaDTO;
import com.webapp.gessi.domain.dto.referenceDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/criteria")
public class criteriaController {
    private static Connection iniConnection() throws SQLException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DBConnection.class);
        Connection conn = ctx.getBean(Connection.class);
        conn.setAutoCommit(false);
        return conn;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public static String addCriteria(String idICEC, String text, String type) {
        System.out.println("add criteria en controller criteria");
        return criteria.insert(idICEC, text, type);
    }

    public static void updateCriteria(String oldIdICEC, criteriaDTO f) {
        System.out.println("update criteria en controller criteria");
        if (!Objects.equals(oldIdICEC, f.getIdICEC()))
            ExclusionController.deleteCriteriaFK();
        criteria.update(f.getIdICEC(), f.getText(), f.getType(), oldIdICEC);
        if (!Objects.equals(oldIdICEC, f.getIdICEC())) {
            ExclusionController.UpdateIdICEC(oldIdICEC, f.getIdICEC());
            ExclusionController.addCriteriaFK();
        }
    }

    public static void deleteCriteria(@PathVariable("id") String idICEC) throws SQLException {
        System.out.println("delete criteria en controller criteria");
        List<ExclusionDTO> exclusionDTOList = ExclusionController.getByIdICEC(idICEC);
        for (ExclusionDTO exclusionDTO : exclusionDTOList) {
            referenceDTO referenceDTO = ReferenceController.getReference(exclusionDTO.getIdRef());
            if (referenceDTO.getApplCriteria().size() <= 1)
                ReferenceController.updateState(referenceDTO.getIdRef(), null);
        }
        criteria.delete(idICEC);

    }

    public static List<criteriaDTO> getCriteriasIC() {
        return criteria.getAllCriteria("IC");
    }
    public static List<criteriaDTO> getCriteriasEC() { return criteria.getAllCriteria("EC"); }


    public static List<String> getStringListCriteriasEC() {
        List<criteriaDTO> list = criteria.getAllCriteria("EC");
        return list.stream().map(criteriaDTO::getIdICEC).collect(Collectors.toList());
    }

    public static List<String> getAllCriteria() {
        ArrayList<String> r = new ArrayList<>();
        List<criteriaDTO> list = criteria.getAllCriteria("");
        for (criteriaDTO i : list) {
            r.add(i.getIdICEC());
            System.out.println(i.getIdICEC());
        }
        return r;
    }
}