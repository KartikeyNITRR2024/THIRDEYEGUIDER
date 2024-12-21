package com.thirdeye.guider.externalcontrollers;

import org.springframework.stereotype.Service;

@Service
public class Thirdeye_Messenger_Connection {
    
//    @Value("${thirdeyeMessengerUrl}")
//    private String thirdeyeMessengerUrl;
//
//    @Value("${thirdeyeMessengerUniqueId}")
//    private Long thirdeyeMessengerUniqueId;
//
//    @Autowired
//    private RestTemplate restTemplate;
//    
//    private static final Logger logger = LoggerFactory.getLogger(Thirdeye_Messenger_Connection.class);
//    
//    public boolean sendHoldedStockPayload(HoldedStockPayload holdedStockPayload) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Content-Type", "application/json");
//            HttpEntity<Object> request = new HttpEntity<>(holdedStockPayload, headers);
//
//            // Send POST request
//            ResponseEntity<Boolean> response = restTemplate.exchange(
//                thirdeyeMessengerUrl + "api/holdedstockmessenger/" + thirdeyeMessengerUniqueId,
//                HttpMethod.POST,
//                request,
//                Boolean.class
//            );
//
//            // Return the response status
//            return response.getBody() != null && response.getBody();
//        } catch (Exception e) {
//            logger.error("Error while sending live Stock Payload To messenger: ", e);
//            return false;
//        }
//    }
}
