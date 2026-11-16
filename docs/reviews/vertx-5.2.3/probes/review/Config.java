package review;
@com.guicedee.vertx.spi.VertX
@com.guicedee.vertx.spi.AddressResolverOptions(hostsRefreshPeriod=30000)
@com.guicedee.vertx.redis.RedisOptions(tlsEnabled=true, database=4)
@com.guicedee.vertx.spi.Verticle(value="probe", maxWorkerExecuteTime=3)
public class Config {}
