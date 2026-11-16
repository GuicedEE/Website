package review;
import com.guicedee.client.Environment;
import com.guicedee.client.utils.Discovery;
import com.guicedee.vertx.spi.*;
import com.guicedee.vertx.redis.*;
public class Probe {
 public static void main(String[] args) throws Exception {
  System.setProperty("GUICEDEE_DISCOVERY_MODE","spi");
  System.setProperty("VERTX_EVENTBUS_PORT","19000");
  var method=VertXPreStartup.class.getDeclaredMethod("configureVertxOptions"); method.setAccessible(true);
  var options=(io.vertx.core.VertxOptions)method.invoke(new VertXPreStartup());
  System.out.println("REVIEW daemon annotation="+Config.class.getAnnotation(VertX.class).useDaemonThreads()+" actual="+options.getUseDaemonThread());
  System.out.println("REVIEW warning annotation=5000 MILLISECONDS actual="+options.getWarningExceptionTime()+" "+options.getWarningExceptionTimeUnit());
  System.out.println("REVIEW DNS refresh documented=30000 milliseconds actual="+options.getAddressResolverOptions().getHostsRefreshPeriod()+" "+options.getAddressResolverOptions().getHostsRefreshPeriodUnit());
  System.out.println("REVIEW package filesystem annotations="+Discovery.annotations(FileSystemOptions.class).size()+" annotatedClasses="+Discovery.annotatedClasses(FileSystemOptions.class).size()+" requestedCaching=false actual="+options.getFileSystemOptions().isFileCachingEnabled());
  System.out.println("REVIEW eventbus envPort=19000 actual="+options.getEventBusOptions().getPort());
  var deployment=VerticleBuilder.class.getDeclaredMethod("toDeploymentOptions",Verticle.class); deployment.setAccessible(true);
  var d=(io.vertx.core.DeploymentOptions)deployment.invoke(new VerticleBuilder(),Config.class.getAnnotation(Verticle.class));
  System.out.println("REVIEW worker requested=3 MINUTES actual="+d.getMaxWorkerExecuteTime()+" "+d.getMaxWorkerExecuteTimeUnit());
  var redis=RedisPreStartup.buildFromAnnotation(Config.class.getAnnotation(RedisOptions.class));
  System.out.println("REVIEW redis tls annotation=true actual="+redis.isTlsEnabled());
  System.out.println("REVIEW redis placeholder :-fallback actual="+RedisPreStartup.resolveEnvPlaceholders("${REVIEW_UNSET_629:-fallback}"));
  var oauth=Class.forName("com.guicedee.vertx.auth.oauth2.OAuth2AuthenticationProvider").getDeclaredMethod("resolveEnvPlaceholders",String.class); oauth.setAccessible(true);
  System.out.println("REVIEW oauth placeholder :fallback actual=["+oauth.invoke(null,"${REVIEW_UNSET_629:fallback}")+"]");
  var resolve=VertxEventRegistry.class.getDeclaredMethod("resolveOption",String.class,String.class,String.class);resolve.setAccessible(true);
  System.out.println("REVIEW event per-address .env worker=true actual="+resolve.invoke(null,"review.event","VERTX_EVENT_WORKER","false"));
 }
}
