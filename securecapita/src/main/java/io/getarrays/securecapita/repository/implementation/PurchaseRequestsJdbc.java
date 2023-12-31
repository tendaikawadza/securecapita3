package io.getarrays.securecapita.repository.implementation;

import io.getarrays.securecapita.domain.PurchaseRequests;
import io.getarrays.securecapita.domain.User;
import io.getarrays.securecapita.exception.ApiException;
import io.getarrays.securecapita.repository.PurchaseRequestsRepository;
import io.getarrays.securecapita.rowmapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;




import static java.util.Map.of;



@Repository
@RequiredArgsConstructor
@Slf4j

public class PurchaseRequestsJdbc implements PurchaseRequestsRepository<PurchaseRequests> {



    private JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbc;
    RowMapper<PurchaseRequests>rowMapper=((rs, rowNum) -> {

        PurchaseRequests PurchaseRequests=new PurchaseRequests();
        PurchaseRequests purchaseRequests=new PurchaseRequests();
        purchaseRequests.setId(rs.getLong("id"));
        purchaseRequests.setProductName(rs.getString("productName"));
        purchaseRequests.setDate(rs.getDate(String.valueOf(rs.getDate("Date"))));
        purchaseRequests.setProductCode(rs.getString("productCode"));
        purchaseRequests.setQuantity(rs.getInt("quantity"));
        purchaseRequests.setReceiverEmail(rs.getString("receiverEmail"));
        return PurchaseRequests;

    });

    @Override
    public List<PurchaseRequests> list() {
        try {
            String query = "SELECT * FROM purchaseRequests";
            List<PurchaseRequests> purchaseRequests = jdbc.query(query,rowMapper) ;                        //query(query, new UserRowMapper());
            return purchaseRequests;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred while retrieving the list of users. Please try again.");
        }

    }


    @Override
    public PurchaseRequests create(PurchaseRequests purchaseRequests) {
        String sql="INSERT INTO productName,,Date,productCode,) VALUES(?,?,?,?,?,?)";
        int insert=  jdbcTemplate.update(sql,purchaseRequests.getReceiverEmail(),purchaseRequests.getProductName(),purchaseRequests.getDate(),purchaseRequests.getProductCode());
        return purchaseRequests;
    }



    @Override
    public PurchaseRequests get(Long id) {
        try {

           String SELECT_PURCHASEREQUESTS_BY_ID_QUERY = "SELECT * FROM PURCHASEREQUEST WHERE id = :id";
            return jdbc.queryForObject(SELECT_PURCHASEREQUESTS_BY_ID_QUERY, of("id", id),rowMapper);

        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No PURCHASE REQUESTS found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void update(PurchaseRequests purchaseRequests, Long id) {
        try {

            String UPDATE_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID = "UPDATE PURCHASEREQUEST SET productName=?,Date=?,productCode=? WHERE id = :purchaserequestsId";

            jdbcTemplate.update(UPDATE_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID, purchaseRequests.getProductName(),purchaseRequests.getDate(),purchaseRequests.getProductCode(),id);
            return;

        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }

    }



    @Override
    public boolean delete(Long id) {
        try {
            String DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID = "DELETE FROM PURCHASEREQUEST WHERE id = :purchaserequestwId";
            jdbc.update(DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID, Collections.singletonMap("purchaserequestsId", id));
            return true;
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }

    }


}
