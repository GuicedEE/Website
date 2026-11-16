package review;
public class Index implements com.guicedee.client.services.IGuiceDiscovery<Index> {
 public java.util.Collection<Class<?>> classes() {return java.util.List.of(Config.class);}
 public java.util.Collection<Package> packages() {return java.util.List.of(review.pkg.Marker.class.getPackage());}
}
