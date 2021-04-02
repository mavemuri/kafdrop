package kafdrop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaQuotaVO {
    private String name;
    private String cn;
    private String clientId;
    private long producerRate;
    private long consumerRate;
    private static final Logger LOG = LoggerFactory.getLogger(KafkaQuotaVO.class);


    public KafkaQuotaVO(String name, String cn, String clientId, long producerRate, long consumerRate) {
        LOG.info("KafkaQuota Constructor Create cn = {}, clientID = {} ,consumer rate = {}, producer rate={}",
                cn,clientId, String.valueOf(consumerRate), String.valueOf(producerRate));
        this.name = name;
        this.cn = cn;
        this.clientId = clientId;
        this.producerRate = producerRate;
        this.consumerRate = consumerRate;
    }

    public KafkaQuotaVO() {}

    public void setClientId(String id) {
        this.clientId = id;
    }


    public String getClientId() {
        return clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }

    public void setProducerRate(long prodRate) {
        this.producerRate = prodRate;
    }

    public long getProducerRate() {
        return producerRate;
    }

    public void setConsumerRate(long consumerRate) {
        this.consumerRate = consumerRate;
    }

    public long getConsumerRate() {
        return consumerRate;
    }
}
