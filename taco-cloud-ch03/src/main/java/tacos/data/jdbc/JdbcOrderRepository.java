package tacos.data.jdbc;

import java.util.Date;
//import java.sql.Timestamp;
//import java.sql.Types;
//import java.util.Arrays;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.validation.constraints.Digits;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;

//import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.data.OrderRepository;
import tacos.domain.Order;
import tacos.domain.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	// Method 1
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacosInserter;
	private ObjectMapper objectMapper;
	
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id");
		
		this.orderTacosInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");
		
		this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderInfo(order);
		order.setId(orderId);
		
		List<Taco> tacos = order.getTacos();
		for (Taco taco : tacos) {
			saveTacosToOrder(orderId, taco.getId());
		}
		
		return order;
	}
	
	private long saveOrderInfo(Order order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());
		
		return orderInserter.executeAndReturnKey(values).longValue();
	}
	
	private void saveTacosToOrder(long orderId, long tacoId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", tacoId);
		
		orderTacosInserter.execute(values);
	}
	
	
	// Method 2
//	private JdbcTemplate jdbc;
//	
//	public JdbcOrderRepository(JdbcTemplate jdbc) {
//		this.jdbc = jdbc;
//	}
//	
//	@Override
//	public Order save(Order order) {
//		long orderId = saveOrderInfo(order);
//		
//		List<Taco> tacos = order.getTacos();
//		for (Taco taco : tacos) {
//			saveTacosToOrder(orderId, taco.getId());
//		}
//		
//		return order;
//	}
//	
//	private long saveOrderInfo(Order order) {
//		order.setPlacedAt(new Date());
//		
//		List<String> colName = Arrays.asList(
//				"deliveryName",
//				"deliveryStreet",
//				"deliveryCity",
//				"deliveryState",
//				"deliveryZip",
//				"ccNumber",
//				"ccExpiration",
//				"ccCVV",
//				"placedAt");
//		
//		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
//				String.format("insert into Taco_Order (%s) values (?,?,?,?,?,?,?,?,?)", String.join(",", colName)), 
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.TIMESTAMP);
//		pscf.setReturnGeneratedKeys(true);
//				
//		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
//				order.getDeliveryName(),
//				order.getDeliveryStreet(),
//				order.getDeliveryCity(),
//				order.getDeliveryState(),
//				order.getDeliveryZip(),
//				order.getCcNumber(),
//				order.getCcExpiration(),
//				order.getCcCVV(),
//				new Timestamp(order.getPlacedAt().getTime())));
//				
//	
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		jdbc.update(psc, keyHolder);
//		
//		return keyHolder.getKey().longValue();
//	}
//	
//	private void saveTacosToOrder(long orderId, long tacoId) {
//		jdbc.update(
//				"insert into Taco_Order_Tacos (tacoOrder, taco) values (?,?)",
//				orderId,
//				tacoId);
//	}

}
